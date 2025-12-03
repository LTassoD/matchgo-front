package com.example.gestionresiduos.model


data class PuntoRecoleccion(
    val id: String,
    val clienteId: String,
    val nombreCliente: String,
    val direccion: String,
    val material: String,
    val contenedor: String,
    val estado: String,
    val observaciones: String?
)