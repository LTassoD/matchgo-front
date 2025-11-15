package com.example.gestionresiduos.model

// Vehículo asignado a un chofer o ruta
data class Vehiculo(
    val id: String,
    val patente: String,
    val modelo: String,
    val capacidadKg: Int,
    val habilitado: Boolean = true
)
