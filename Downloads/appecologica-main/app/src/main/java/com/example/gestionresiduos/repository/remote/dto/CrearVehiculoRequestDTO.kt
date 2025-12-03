// En: repository/remote/dto/CrearVehiculoRequestDTO.kt
package com.example.gestionresiduos.repository.remote.dto

data class CrearVehiculoRequestDTO(
    val marca: String,
    val modelo: String,
    val patente: String,
    val anio: Int,
    val capacidad: Int
)
    