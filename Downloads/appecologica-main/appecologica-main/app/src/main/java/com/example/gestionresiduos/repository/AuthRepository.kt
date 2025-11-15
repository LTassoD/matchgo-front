package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.LoginRequest
import com.example.gestionresiduos.model.Usuario
import com.example.gestionresiduos.repository.local.InMemoryUsuarioDao
import com.example.gestionresiduos.repository.local.UsuarioDao

interface AuthRepository {
    suspend fun login(request: LoginRequest): Usuario
}

class AuthRepositoryImpl(
    private val remote: Repository = RemoteRepository(),
    private val usuarioDao: UsuarioDao = InMemoryUsuarioDao
) : AuthRepository {
    override suspend fun login(request: LoginRequest): Usuario {
        val validation = request.validate()
        if (!validation.isValid) {
            val message = validation.rutError ?: validation.passwordError ?: "Datos inválidos"
            throw IllegalArgumentException(message)
        }

        return runCatching { remote.login(request.sanitizedRut, request.password) }
            .getOrElse { fallback ->
                val credentials = usuarioDao.findByRut(request.sanitizedRut)
                    ?: throw IllegalArgumentException("Usuario no encontrado")
                if (credentials.password != request.password) {
                    throw IllegalArgumentException("Contraseña incorrecta")
                }
                credentials.usuario
            }
    }
}
