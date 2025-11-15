package com.example.gestionresiduos.model

import java.time.LocalDate

// Registro de una ruta completada por el chofer
data class HistorialLabor(
    val id: String,
    val choferId: String,
    val fecha: LocalDate,
    val resumen: String,
    val puntosCompletados: Int
)
