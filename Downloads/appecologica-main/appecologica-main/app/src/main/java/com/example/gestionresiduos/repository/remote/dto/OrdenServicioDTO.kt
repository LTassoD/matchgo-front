package com.example.gestionresiduos.repository.remote.dto

// Estado como String plano; se mapeará al enum EstadoOS en los mappers
data class OrdenServicioDTO(
    val id: String,
    val rutaId: String,
    val puntoId: String,
    val estado: String,          // "PENDIENTE" | "EN_PROCESO" | "COMPLETADA"
    val fotos: List<String>?,    // puede venir null, lo normalizamos a lista vacía
    val observacion: String?
)