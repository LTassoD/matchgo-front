package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.LoginRequest
import com.example.gestionresiduos.model.Usuario
import com.example.gestionresiduos.repository.local.InMemoryUsuarioDao

class AuthRepositoryFake(
    private val usuarioDao: com.example.gestionresiduos.repository.local.UsuarioDao = InMemoryUsuarioDao
) : AuthRepository {
    override suspend fun login(request: LoginRequest): Usuario {
        val validation = request.validate()
        if (!validation.isValid) throw IllegalArgumentException("Datos inválidos")

        val credential = usuarioDao.findByRut(request.sanitizedRut)
            ?: throw IllegalArgumentException("Usuario no encontrado")
        if (credential.password != request.password) throw IllegalArgumentException("Credenciales inválidas")
        return credential.usuario
    }
}
