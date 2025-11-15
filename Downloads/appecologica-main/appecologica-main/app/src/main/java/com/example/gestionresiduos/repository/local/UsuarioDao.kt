package com.example.gestionresiduos.repository.local

import com.example.gestionresiduos.model.Usuario

data class UsuarioCredential(val usuario: Usuario, val password: String)

interface UsuarioDao {
    fun findByRut(rut: String): UsuarioCredential?
    fun getAll(): List<UsuarioCredential>
}

internal object InMemoryUsuarioDao : UsuarioDao {
    override fun findByRut(rut: String): UsuarioCredential? =
        AppDatabase.usuarios.firstOrNull { it.usuario.rut.equals(rut, ignoreCase = true) }

    override fun getAll(): List<UsuarioCredential> = AppDatabase.usuarios.toList()
}
