package com.example.gestionresiduos.repository.remote.dto

// Respuesta de autenticación del backend (JWT + datos básicos del usuario)
data class AuthResponseDTO(
    val token: String,
    val id: Long,
    val nombre: String,
    val rut: String,
    val role: String
)
