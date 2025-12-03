package com.example.gestionresiduos.model


data class Ruta(
    val id: String,
    val choferId: String?,
    val nombre: String,
    val fecha: String,
    val estado: String,
    val puntos: List<PuntoRecoleccion>,
    val vehiculoId: String? = null
)
