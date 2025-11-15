package com.example.gestionresiduos.model

// Punto físico donde se realiza la recolección de material
data class PuntoRecoleccion(
    val id: String,              // Identificador único del punto
    val clienteId: String,       // ID del cliente propietario del punto
    val direccion: String,       // Dirección exacta del punto
    val material: String,        // Tipo de material a recoger (cartón, vidrio, etc.)
    val contenedor: String,      // Tipo de contenedor (120L, 240L, etc.)
    val observaciones: String?   // Comentarios u observaciones adicionales
)