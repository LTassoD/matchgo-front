// En: repository/remote/dto/VehiculoDTO.kt
package com.example.gestionresiduos.repository.remote.dto

data class VehiculoDTO(
    val id: Int,
    val marca: String,
    val modelo: String,
    val patente: String,
    val anio: Int,
    val capacidad: Int
)
    