package com.example.gestionresiduos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionresiduos.model.LoginRequest
import com.example.gestionresiduos.model.LoginValidationResult
import com.example.gestionresiduos.model.Usuario
import com.example.gestionresiduos.repository.AuthRepository
import com.example.gestionresiduos.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario = _usuario.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _validation = MutableStateFlow(LoginValidationResult())
    val validation = _validation.asStateFlow()

    fun login(rut: String, password: String) {
        viewModelScope.launch {
            val request = LoginRequest(rut, password)
            val validation = request.validate()
            _validation.value = validation
            if (!validation.isValid) return@launch

            _loading.value = true
            _error.value = null
            runCatching { repository.login(request) }
                .onSuccess { _usuario.value = it }
                .onFailure { _error.value = it.message ?: "Error al iniciar sesión" }
            _loading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}
