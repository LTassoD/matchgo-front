// En: repository/remote/dto/CrearEmpleadoResponseDTO.kt
package com.example.gestionresiduos.repository.remote.dto

data class CrearEmpleadoResponseDTO(
    val id: Int,
    val nombre: String,
    val rut: String,
    val correo: String,
    val telefono: String,
    val rol: String,
    val fotoUri: String?
)
