// En: repository/remote/mapper/HistorialMapper.kt
package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.HistorialLabor
import com.example.gestionresiduos.repository.remote.dto.HistorialLaborDTO

/**
 * Convierte el 'HistorialLaborDTO' de la API a un modelo de dominio 'HistorialLabor'.
 */
fun HistorialLaborDTO.toDomain(): HistorialLabor {
    return HistorialLabor(
        rutaId = this.rutaId,
        fecha = this.fecha,
        puntosCompletados = this.puntosCompletados,
        observacion = this.observacion,
        userId = this.userId
    )
}
