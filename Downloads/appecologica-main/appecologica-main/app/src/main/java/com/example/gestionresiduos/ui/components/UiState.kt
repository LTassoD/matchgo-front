package com.example.gestionresiduos.ui.components

// Estado genérico para pantallas que cargan datos
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}