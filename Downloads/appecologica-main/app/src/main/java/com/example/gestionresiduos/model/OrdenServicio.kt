package com.example.gestionresiduos.model

data class OrdenServicio(
    val id: String,
    val rutaId: String,
    val puntoId: String,
    val estado: EstadoOS,
    val fotos: List<String> = emptyList(),
    val observacion: String?
)