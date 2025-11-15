package com.example.gestionresiduos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionresiduos.model.OrdenServicio
import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.repository.RutaRepository
import com.example.gestionresiduos.repository.RutaRepositoryFake
import com.example.gestionresiduos.ui.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RutaViewModel(
    private val choferId: String,
    private val repository: RutaRepository = RutaRepositoryFake()
) : ViewModel() {

    private val _rutas = MutableStateFlow<UiState<List<Ruta>>>(UiState.Loading)
    val rutas = _rutas.asStateFlow()

    private val _rutaDetalle = MutableStateFlow<UiState<Ruta>>(UiState.Loading)
    val rutaDetalle = _rutaDetalle.asStateFlow()

    private val _orden = MutableStateFlow<UiState<OrdenServicio>>(UiState.Loading)
    val orden = _orden.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    init {
        cargarRutas()
    }

    fun cargarRutas() {
        viewModelScope.launch {
            _rutas.value = UiState.Loading
            runCatching { repository.rutasParaChofer(choferId) }
                .onSuccess { _rutas.value = UiState.Success(it) }
                .onFailure { _rutas.value = UiState.Error(it.message ?: "Error al cargar rutas") }
        }
    }

    fun cargarRuta(rutaId: String) {
        viewModelScope.launch {
            _rutaDetalle.value = UiState.Loading
            runCatching { repository.rutaPorId(rutaId) }
                .onSuccess { _rutaDetalle.value = UiState.Success(it) }
                .onFailure { _rutaDetalle.value = UiState.Error(it.message ?: "Error al cargar la ruta") }
        }
    }

    fun cargarOrden(rutaId: String, puntoId: String) {
        viewModelScope.launch {
            _orden.value = UiState.Loading
            runCatching { repository.obtenerOrden(rutaId, puntoId) }
                .onSuccess { _orden.value = UiState.Success(it) }
                .onFailure { _orden.value = UiState.Error(it.message ?: "No fue posible cargar la OS") }
        }
    }

    fun iniciarOrden(puntoId: String) {
        viewModelScope.launch {
            runCatching { repository.iniciarOrden(puntoId) }
                .onSuccess { _mensaje.value = "Orden iniciada" }
                .onFailure { _mensaje.value = it.message }
        }
    }

    fun finalizarOrden(puntoId: String, observacion: String?) {
        viewModelScope.launch {
            runCatching { repository.finalizarOrden(puntoId, observacion) }
                .onSuccess { _mensaje.value = "Orden finalizada" }
                .onFailure { _mensaje.value = it.message }
        }
    }
}
