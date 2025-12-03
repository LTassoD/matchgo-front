// En: repository/remote/mapper/VehiculoMapper.kt
package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.Vehiculo
import com.example.gestionresiduos.repository.remote.dto.CrearVehiculoRequestDTO
import com.example.gestionresiduos.repository.remote.dto.VehiculoDTO

/**
 * Convierte el modelo de dominio 'Vehiculo' a un DTO para enviarlo a la API.
 */
fun Vehiculo.toRequestDto(): CrearVehiculoRequestDTO {
    return CrearVehiculoRequestDTO(
        marca = this.marca,
        modelo = this.modelo,
        patente = this.patente,
        anio = this.anio,
        capacidad = this.capacidad
    )
}

/**
 * Convierte el 'VehiculoDTO' de la API a un modelo de dominio 'Vehiculo'.
 */
fun VehiculoDTO.toDomain(): Vehiculo {
    return Vehiculo(
        id = this.id,
        marca = this.marca,
        modelo = this.modelo,
        patente = this.patente,
        anio = this.anio,
        capacidad = this.capacidad
    )
}
