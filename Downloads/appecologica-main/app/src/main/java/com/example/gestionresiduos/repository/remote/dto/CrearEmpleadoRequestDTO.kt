package com.example.gestionresiduos.repository.remote.dto

data class CrearEmpleadoRequestDTO(
    val nombre: String,
    val rut: String,
    val correo: String,
    val telefono: String,
    val rol: String, // Enviamos el rol como un String (ej. "CHOFER")
    val fotoUri: String?
)
