package com.example.gestionresiduos.model

// Representa una orden de servicio generada dentro de una ruta
data class OrdenServicio(
    val id: String,                  // Identificador único de la orden
    val rutaId: String,              // Ruta a la que pertenece
    val puntoId: String,             // Punto específico donde se ejecuta
    val estado: EstadoOS,            // Estado actual de la orden (enum)
    val fotos: List<String> = emptyList(), // URLs o paths de fotos adjuntas
    val observacion: String?         // Texto libre de observación del chofer
)