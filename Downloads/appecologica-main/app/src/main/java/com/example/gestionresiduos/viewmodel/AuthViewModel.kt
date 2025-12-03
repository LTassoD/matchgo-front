package com.example.gestionresiduos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.repository.RemoteRepository
import com.example.gestionresiduos.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class AuthViewModel : ViewModel() {

    private val repository: Repository = RemoteRepository()

    // =================================================================
    //         ESTADOS DE LA UI (CARGA Y ERRORES)
    // =================================================================

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // =================================================================
    //         ESTADOS DE DATOS
    // =================================================================

    private val _empleado = MutableStateFlow<Empleado?>(null)
    val empleado = _empleado.asStateFlow()

    private val _token = MutableStateFlow<String?>(null)

    // =================================================================
    //         SECCIÓN DE ACCIONES
    // =================================================================

    fun login(rut: String, password: String, onLoginSuccess: (Empleado) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {

                val (user, userToken) = repository.login(rut, password)

                _empleado.value = user
                _token.value = userToken

                onLoginSuccess(user)

            } catch (e: HttpException) {
                _error.value = "Error de servidor (${e.code()})"
            } catch (_: IOException) {
                _error.value = "Error de conexión. Revisa tu Internet."
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }

}