package com.example.gestionresiduos.repository.remote.dto

data class RutaDTO(
    val id: String,
    val nombre: String,
    val fecha: String,
    val estado: String,
    val choferId: String?,
    val puntos: List<PuntoDTO>
)
