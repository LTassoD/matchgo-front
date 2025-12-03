package com.example.gestionresiduos.repository.remote.dto

data class PuntoDTO(
    val id: String,
    val clienteId: String,
    val nombreCliente: String? = null,
    val direccion: String,
    val material: String,
    val contenedor: String,
    val estado: String? = null,
    val observaciones: String?
)
