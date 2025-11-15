package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.repository.remote.dto.RutaDTO
import java.time.LocalDate

// Pasa fecha String -> LocalDate
fun RutaDTO.toDomain(): Ruta =
    Ruta(
        id = id,
        fecha = LocalDate.parse(fecha),   // formato "yyyy-MM-dd"
        choferId = choferId,
        puntos = puntos.map { it.toDomain() }
    )