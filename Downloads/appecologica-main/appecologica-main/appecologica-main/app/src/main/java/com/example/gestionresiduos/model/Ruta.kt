package com.example.gestionresiduos.model

import java.time.LocalDate

// Representa una ruta diaria de recolección asignada a un chofer
data class Ruta(
    val id: String,                      // Identificador único de la ruta
    val fecha: LocalDate,                // Fecha programada de la ruta
    val choferId: String,                // ID del chofer asignado
    val puntos: List<PuntoRecoleccion>   // Lista de puntos incluidos en la ruta
)