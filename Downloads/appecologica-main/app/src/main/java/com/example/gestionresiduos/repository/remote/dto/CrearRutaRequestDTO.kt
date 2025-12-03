// En: repository/remote/dto/CrearRutaRequestDTO.kt
package com.example.gestionresiduos.repository.remote.dto

data class CrearRutaRequestDTO(
    val nombre: String,
    val fecha: String, // Podrías usar un formato estándar como "YYYY-MM-DD"
    val estado: String,
    val idChoferAsignado: String?,
    val idVehiculo: String? = null
)
    
