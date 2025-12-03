// En: repository/remote/dto/RankingResponseDTO.kt
package com.example.gestionresiduos.repository.remote.dto

/**
 * DTO que representa la respuesta COMPLETA del endpoint de ranking.
 * Contiene una lista de los rankings de empleados.
 */
data class RankingResponseDTO(
    // El nombre "ranking" debe coincidir con la clave en el JSON de la API.
    val ranking: List<RankingEmpleadoDTO>
)
    