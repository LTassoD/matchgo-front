package com.example.gestionresiduos.repository.remote.dto

data class PuntoDTO(
    val id: String,
    val clienteId: String,
    val direccion: String,
    val material: String,
    val contenedor: String,
    val observaciones: String?
)