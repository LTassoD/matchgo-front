package com.example.gestionresiduos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionresiduos.model.*
import com.example.gestionresiduos.repository.RemoteRepository
import com.example.gestionresiduos.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException


class AdminViewModel(
    private val repository: Repository = RemoteRepository()
) : ViewModel() {

    // =================================================================
    //         1. ESTADOS DE LA UI Y DATOS
    // =================================================================

    // --- Estados de Carga y Errores ---
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // --- Estados de Datos ---
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes.asStateFlow()

    private val _empleados = MutableStateFlow<List<Empleado>>(emptyList())
    val empleados: StateFlow<List<Empleado>> = _empleados.asStateFlow()

    private val _vehiculos = MutableStateFlow<List<Vehiculo>>(emptyList())
    val vehiculos: StateFlow<List<Vehiculo>> = _vehiculos.asStateFlow()

    private val _materiales = MutableStateFlow<List<Material>>(emptyList())
    val materiales: StateFlow<List<Material>> = _materiales.asStateFlow()

    private val _rankingEmpleados = MutableStateFlow<List<Pair<Empleado, Float>>>(emptyList())
    val rankingEmpleados: StateFlow<List<Pair<Empleado, Float>>> = _rankingEmpleados.asStateFlow()

    private val _historialEmpleado = MutableStateFlow<List<HistorialLabor>>(emptyList())
    val historialEmpleado: StateFlow<List<HistorialLabor>> = _historialEmpleado.asStateFlow()

    init {
        cargarTodosLosDatos()
    }

    // =================================================================
    //         2. CARGA DE DATOS (READ ALL)
    // =================================================================

    fun cargarTodosLosDatos() {
        cargarClientes()
        cargarEmpleados()
        cargarVehiculos()
        cargarMateriales()
        cargarRankingEmpleados()
    }

    fun cargarClientes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _clientes.value = repository.listarClientes()
            } catch (e: Exception) {
                _error.value = "Error al cargar clientes: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cargarEmpleados() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _empleados.value = repository.listarEmpleados()
            } catch (e: Exception) {
                _error.value = "Error al cargar empleados: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cargarVehiculos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _vehiculos.value = repository.listarVehiculos()
            } catch (e: Exception) {
                _error.value = "Error al cargar vehículos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cargarMateriales() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _materiales.value = repository.listarMateriales()
            } catch (e: Exception) {
                _error.value = "Error al cargar materiales: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cargarRankingEmpleados() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (empleados.value.isNotEmpty()) {
                    _rankingEmpleados.value = listOf(
                        Pair(empleados.value.first(), 95.5f),
                        Pair(empleados.value.getOrElse(1) { empleados.value.first() }, 87.0f)
                    )
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el ranking: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadHistorialParaEmpleado(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {

                _historialEmpleado.value = emptyList() // Limpia o carga datos de ejemplo
            } catch (e: Exception) {
                _error.value = "Error al cargar historial: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // =================================================================
    //         3. CRUD - CLIENTES
    // =================================================================

    fun crearCliente(nuevoCliente: Cliente, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.crearCliente(nuevoCliente)
                cargarClientes()
                onComplete()
            } catch (e: Exception) {
                _error.value = "Error al crear el cliente: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun actualizarCliente(clienteActualizado: Cliente, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.actualizarCliente(clienteActualizado)
                cargarClientes()
                onComplete()
            } catch (e: Exception) {
                _error.value = "Error al actualizar el cliente: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun borrarCliente(clienteId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.eliminarCliente(clienteId)
                _clientes.value = _clientes.value.filterNot { it.idcliente == clienteId }
            } catch (e: Exception) {
                _error.value = "Error al eliminar el cliente: ${e.message}"
                cargarClientes()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // =================================================================
    //         4. CRUD - EMPLEADOS
    // =================================================================

    fun saveEmpleado(empleado: Empleado, onSaveFinished: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.registrarEmpleado(empleado)
                cargarEmpleados()
                onSaveFinished()
            } catch (e: Exception) {
                _error.value = "Error al guardar el empleado: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun actualizarEmpleado(empleadoActualizado: Empleado, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.actualizarEmpleado(empleadoActualizado)
                cargarEmpleados()
                onComplete()
            } catch (e: Exception) {
                _error.value = "Error al actualizar el empleado: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun borrarEmpleado(empleadoId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.eliminarEmpleado(empleadoId)
                _empleados.value = _empleados.value.filterNot { it.id == empleadoId }
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _empleados.value = _empleados.value.filterNot { it.id == empleadoId }
                } else {
                    _error.value = "Error del servidor al eliminar: ${e.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error al eliminar el empleado: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    // =================================================================
    //         3. CRUD - VEHÍCULOS
    // =================================================================

    fun crearVehiculo(nuevoVehiculo: Vehiculo, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.crearVehiculo(nuevoVehiculo)
                cargarVehiculos()
                onComplete()
            } catch (e: Exception) {
                _error.value = "Error al crear el vehículo: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    /**
     * **UPDATE**: Actualiza un vehículo existente.
     */
    fun actualizarVehiculo(vehiculoActualizado: Vehiculo, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.actualizarVehiculo(vehiculoActualizado)
                cargarVehiculos()
                onComplete()
            } catch (e: Exception) {
                _error.value = "Error al actualizar el vehículo: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    // =================================================================
    //         5. CRUD - MATERIALES
    // =================================================================

    fun crearMaterial(material: Material, onMaterialCreated: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.crearMaterial(material)
                cargarMateriales()
                onMaterialCreated()
            } catch (e: Exception) {
                _error.value = "Error al crear el material: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun editarMaterial(materialActualizado: Material, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.actualizarMaterial(materialActualizado)
                cargarMateriales()
                onComplete()
            } catch (e: Exception) {
                _error.value = "Error al actualizar el material: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun borrarMaterial(materialId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.eliminarMaterial(materialId)
                _materiales.value = _materiales.value.filterNot { it.id == materialId }
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _materiales.value = _materiales.value.filterNot { it.id == materialId }
                } else {
                    _error.value = "Error del servidor al eliminar: ${e.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error al eliminar el material: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // =================================================================
    //         6. OTRAS OPERACIONES
    // =================================================================

    fun enviarComentario(userId: Int, comentario: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                delay(1000)
                onComplete()
            } catch (e: Exception) {
                _error.value = "Error al enviar comentario: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    // =================================================================
    //         SECCIÓN DE ASIGNACIÓN DE RUTAS
    // =================================================================

    private val _clienteSeleccionadoId = MutableStateFlow<Int?>(null)
    val clienteSeleccionadoId: StateFlow<Int?> = _clienteSeleccionadoId.asStateFlow()

    private val _choferSeleccionadoId = MutableStateFlow<Int?>(null)
    val choferSeleccionadoId: StateFlow<Int?> = _choferSeleccionadoId.asStateFlow()

    private val _vehiculoSeleccionadoId = MutableStateFlow<Int?>(null)
    val vehiculoSeleccionadoId: StateFlow<Int?> = _vehiculoSeleccionadoId.asStateFlow()

    /**
     * Actualiza el ID del cliente seleccionado.
     */
    fun onClienteSeleccionado(id: Int) {
        _clienteSeleccionadoId.value = id
    }

    /**
     * Actualiza el ID del chofer seleccionado.
     */
    fun onChoferSeleccionado(id: Int) {
        _choferSeleccionadoId.value = id
    }

    /**
     * Actualiza el ID del vehículo seleccionado.
     */
    fun onVehiculoSeleccionado(id: Int) {
        _vehiculoSeleccionadoId.value = id
    }

    /**
     * Ejecuta la lógica para asignar la ruta.
     */
    fun asignarRuta(onComplete: () -> Unit) {
        // Se asegura de que todas las selecciones se hayan hecho
        val clienteId = _clienteSeleccionadoId.value
        val choferId = _choferSeleccionadoId.value
        val vehiculoId = _vehiculoSeleccionadoId.value

        if (clienteId == null || choferId == null || vehiculoId == null) {
            _error.value = "Debes seleccionar un cliente, un chofer y un vehículo."
            return
        }

        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.asignarRuta(clienteId, choferId, vehiculoId)
                cargarClientes()
                resetearSeleccion()
                onComplete()
            } catch (e: Exception) {
                _error.value = "Error al asignar la ruta: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    /**
     * Limpia los IDs seleccionados.
     */
    fun resetearSeleccion() {
        _clienteSeleccionadoId.value = null
        _choferSeleccionadoId.value = null
        _vehiculoSeleccionadoId.value = null
    }

    fun errorMostrado() {
        _error.value = null
    }


}
