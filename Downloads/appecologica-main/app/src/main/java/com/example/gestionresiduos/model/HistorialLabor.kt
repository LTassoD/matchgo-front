package com.example.gestionresiduos.model


import java.util.Date


data class HistorialLabor(

    val id: Long =0,
    val userId: Int, // Para saber a qué usuario pertenece
    val fecha: Date,
    val rutaId: String,
    val puntosCompletados: Int,
    val observacion: String? = null
)

