package com.example.gestionresiduos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.repository.RemoteRepository
import com.example.gestionresiduos.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class RutaViewModel : ViewModel() {

    private val repository: Repository = RemoteRepository()

    // =================================================================
    //         ESTADOS EXPUESTOS A LA UI
    // =================================================================
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas = _rutas.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // =================================================================
    //         SECCIÓN DE CARGA DE DATOS (READ)
    // =================================================================
    fun cargarRutasPorChofer(choferId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _rutas.value = repository.rutasParaChofer(choferId)
            } catch (e: Exception) {
                handleApiError(e, "cargar rutas del chofer")
            } finally {
                _loading.value = false
            }
        }
    }

    fun cargarTodasLasRutas() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {


            } catch (e: Exception) {
                handleApiError(e, "cargar todas las rutas")
            } finally {
                _loading.value = false
            }
        }
    }

    // =================================================================
    //         SECCIÓN DE ACCIONES (CRUD Y OPERACIONES)
    // =================================================================

    // --- CORRECCIÓN: Se implementa la lógica para las acciones del Admin ---
    fun crearRuta(nuevaRuta: Ruta, onComplete: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.crearRuta(nuevaRuta)
                cargarTodasLasRutas() // Refresca la lista para el admin
                onComplete()
            } catch (e: Exception) {
                handleApiError(e, "crear la ruta")
            } finally {
                _loading.value = false
            }
        }
    }

    fun actualizarRuta(rutaActualizada: Ruta, onComplete: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.actualizarRuta(rutaActualizada)
                cargarTodasLasRutas()
                onComplete()
            } catch (e: Exception) {
                handleApiError(e, "actualizar la ruta")
            } finally {
                _loading.value = false
            }
        }
    }

    fun eliminarRuta(rutaId: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.eliminarRuta(rutaId)
                // Actualización optimista:
                _rutas.value = _rutas.value.filterNot { it.id == rutaId }
            } catch (e: Exception) {
                handleApiError(e, "eliminar la ruta")
                cargarTodasLasRutas()
            } finally {
                _loading.value = false
            }
        }
    }

    // --- ACCIONES PARA CHOFER ---
    fun iniciarOrden(puntoId: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.iniciarOrden(puntoId)
                actualizarEstadoPuntoLocal(puntoId, "EN PROGRESO")
            } catch (e: Exception) {
                handleApiError(e, "iniciar la orden")
            } finally {
                _loading.value = false
            }
        }
    }

    fun finalizarOrden(puntoId: String, observacion: String?, onComplete: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.finalizarOrden(puntoId, observacion)
                actualizarEstadoPuntoLocal(puntoId, "COMPLETADO")
                onComplete()
            } catch (e: Exception) {
                handleApiError(e, "finalizar la orden")
            } finally {
                _loading.value = false
            }
        }
    }

    // =================================================================
    //         UTILIDADES
    // =================================================================

    private fun handleApiError(e: Exception, context: String) {
        val errorMessage = when (e) {
            is IOException -> "Error de conexión. Revisa tu Internet."
            else -> "Error al $context: ${e.message}"
        }
        _error.value = errorMessage
    }

    fun errorMostrado() {
        _error.value = null
    }

    private fun actualizarEstadoPuntoLocal(puntoId: String, nuevoEstado: String) {
        val rutasActuales = _rutas.value
        val rutasActualizadas = rutasActuales.map { ruta ->
            val puntosActualizados = ruta.puntos.map { punto ->
                if (punto.id == puntoId) {
                    punto.copy(estado = nuevoEstado)
                } else {
                    punto
                }
            }
            ruta.copy(puntos = puntosActualizados)
        }
        _rutas.value = rutasActualizadas
    }
}
