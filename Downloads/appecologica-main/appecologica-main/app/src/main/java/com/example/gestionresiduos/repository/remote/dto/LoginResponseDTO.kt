package com.example.gestionresiduos.repository.remote.dto

// Respuesta del login desde Gateway/Auth
data class LoginResponseDTO(
    val accessToken: String,
    val tokenType: String,
    val id: String,
    val nombre: String,
    val rut: String,
    val rol: String
)

