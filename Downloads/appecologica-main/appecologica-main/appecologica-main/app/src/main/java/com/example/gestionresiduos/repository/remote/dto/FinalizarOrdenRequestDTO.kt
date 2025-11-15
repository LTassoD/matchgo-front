package com.example.gestionresiduos.repository.remote.dto

// Payload para POST /api/ordenes/{puntoId}/finalizar
data class FinalizarOrdenRequestDTO(
    val observacion: String?
)