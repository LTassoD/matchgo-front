package com.example.gestionresiduos.repository.remote.dto

// payload para /api/auth/login
data class LoginRequestDTO(
    val rut: String,
    val password: String
)