// En: repository/remote/dto/HistorialLaborDTO.kt
package com.example.gestionresiduos.repository.remote.dto

import java.util.Date

data class HistorialLaborDTO(
    val rutaId: String,
    val fecha: Date,
    val puntosCompletados: Int,
    val observacion: String?,
    val userId: Int
)
    