package com.example.gestionresiduos.model

// Representa un cliente que tiene puntos de recolección
data class Cliente(
    val id: String,         // Identificador único del cliente
    val nombre: String,     // Nombre o razón social
    val direccion: String   // Dirección principal del cliente
)