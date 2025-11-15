package com.example.gestionresiduos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.repository.RemoteRepository
import com.example.gestionresiduos.ui.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val repo = RemoteRepository()

    private val _state = MutableStateFlow<UiState<List<Cliente>>>(UiState.Loading)
    val state = _state.asStateFlow()

    fun cargarClientes() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val data = repo.listarClientes()
                _state.value = UiState.Success(data)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}