package com.example.gestionresiduos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.model.Material
import com.example.gestionresiduos.model.Vehiculo
import com.example.gestionresiduos.repository.AdminRepository
import com.example.gestionresiduos.repository.AdminRepositoryImpl
import com.example.gestionresiduos.ui.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: AdminRepository = AdminRepositoryImpl()
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Cliente>>>(UiState.Loading)
    val state = _state.asStateFlow()

    private val _vehiculos = MutableStateFlow(repository.obtenerVehiculos())
    val vehiculos = _vehiculos.asStateFlow()

    private val _materiales = MutableStateFlow(repository.obtenerMateriales())
    val materiales = _materiales.asStateFlow()

    private val _usuarios = MutableStateFlow(repository.obtenerUsuarios())
    val usuarios = _usuarios.asStateFlow()

    fun cargarClientes(forceRemote: Boolean = false) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val data = repository.obtenerClientes(forceRemote)
                _state.value = UiState.Success(data)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun crearCliente(nombre: String, direccion: String) {
        if (nombre.isBlank() || direccion.isBlank()) return
        viewModelScope.launch {
            repository.crearOActualizarCliente(
                Cliente(id = nombre.lowercase(), nombre = nombre, direccion = direccion)
            )
            cargarClientes()
        }
    }

    fun actualizarVehiculo(id: String, habilitado: Boolean) {
        repository.actualizarEstadoVehiculo(id, habilitado)
        _vehiculos.value = repository.obtenerVehiculos()
    }

    fun crearMaterial(nombre: String, descripcion: String, unidad: String, precio: Double) {
        viewModelScope.launch {
            repository.crearOModificarMaterial(
                Material(
                    id = nombre.lowercase(),
                    nombre = nombre,
                    descripcion = descripcion,
                    unidadMedida = unidad,
                    precioReferencia = precio
                )
            )
            _materiales.value = repository.obtenerMateriales()
        }
    }

    fun crearVehiculo(patente: String, modelo: String, capacidad: Int) {
        viewModelScope.launch {
            repository.registrarVehiculo(
                Vehiculo(
                    id = patente.uppercase(),
                    patente = patente.uppercase(),
                    modelo = modelo,
                    capacidadKg = capacidad
                )
            )
            _vehiculos.value = repository.obtenerVehiculos()
        }
    }
}
