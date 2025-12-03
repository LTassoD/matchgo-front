// En: repository/remote/mapper/MaterialMapper.kt
package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.Material
import com.example.gestionresiduos.repository.remote.dto.CrearMaterialRequestDTO
import com.example.gestionresiduos.repository.remote.dto.MaterialDTO

/**
 * Convierte el modelo de dominio 'Material' a un DTO para enviarlo a la API.
 */
fun Material.toRequestDto(): CrearMaterialRequestDTO {
    return CrearMaterialRequestDTO(
        nombre = this.nombre,
        descripcion = this.descripcion
    )
}

/**
 * Convierte el 'MaterialDTO' de la API a un modelo de dominio 'Material'.
 */
fun MaterialDTO.toDomain(): Material {
    return Material(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion
    )
}
