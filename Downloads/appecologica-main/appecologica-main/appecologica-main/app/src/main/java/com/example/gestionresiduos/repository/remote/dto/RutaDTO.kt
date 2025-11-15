package com.example.gestionresiduos.repository.remote.dto

// Nota: fecha llega como String (ej: "2025-10-12") para mapearla luego a LocalDate
data class RutaDTO(
    val id: String,
    val fecha: String,
    val choferId: String,
    val puntos: List<PuntoDTO>
)
