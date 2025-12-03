
package com.example.gestionresiduos.repository.remote.dto

/**
 * DTO que representa la respuesta de la API para un ítem del ranking.
 * Contiene los datos completos de un empleado y su porcentaje de cumplimiento.
 */
data class RankingEmpleadoDTO(
    val empleado: EmpleadoDTO, // Reutilizamos el EmpleadoDTO que ya tenemos del login
    val cumplimiento: Float    // Porcentaje de cumplimiento, ej: 0.95 para 95%
)
