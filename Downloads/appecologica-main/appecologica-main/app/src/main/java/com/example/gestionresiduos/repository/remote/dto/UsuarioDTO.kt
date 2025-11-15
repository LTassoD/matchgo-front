package com.example.gestionresiduos.repository.remote.dto

// Respuesta del login y/o endpoints de usuario
data class UsuarioDTO(
    val id: String,
    val nombre: String,
    val rut: String,
    val rol: String // "CHOFER" | "ADMIN" (string plano desde el backend)
)