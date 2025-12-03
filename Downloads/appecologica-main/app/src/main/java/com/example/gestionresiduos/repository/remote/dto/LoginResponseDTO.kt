package com.example.gestionresiduos.repository.remote.dto


data class LoginResponseDTO(
    val empleado: EmpleadoDTO?, // <-- Añade el signo de interrogación aquí
    val token: String?
)
    