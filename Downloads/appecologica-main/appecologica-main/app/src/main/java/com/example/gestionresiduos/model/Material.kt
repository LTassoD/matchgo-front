package com.example.gestionresiduos.model

// Describe un material que puede gestionar la empresa
data class Material(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val unidadMedida: String,
    val precioReferencia: Double
)
