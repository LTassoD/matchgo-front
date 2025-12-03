package com.example.gestionresiduos.repository.remote.mapper


import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.repository.remote.dto.CrearRutaRequestDTO
import com.example.gestionresiduos.repository.remote.dto.RutaDTO


fun RutaDTO.toDomain(): Ruta =
    Ruta(
        id = this.id,
        nombre = this.nombre,
        fecha = this.fecha,
        estado = this.estado,
        choferId = this.choferId,
        puntos = this.puntos.map { it.toDomain() }
    )

fun Ruta.toRequestDto(): CrearRutaRequestDTO {
    return CrearRutaRequestDTO(
        nombre = this.nombre,
        fecha = this.fecha,
        estado = this.estado,
        idChoferAsignado = this.choferId,
        idVehiculo = this.vehiculoId
    )
}
