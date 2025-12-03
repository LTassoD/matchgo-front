package com.example.gestionresiduos.repository.remote.dto

data class CrearClienteRequestDTO(
    val nombreEmpresa: String,
    val rut: String,
    val direccion: String,
    val telefono: String,
    val correo: String,
    val material: String,
    val frecuencia: String,
    val fotoUri: String?
)
