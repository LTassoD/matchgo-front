package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.EstadoOS
import com.example.gestionresiduos.model.OrdenServicio
import com.example.gestionresiduos.repository.remote.dto.OrdenServicioDTO

fun OrdenServicioDTO.toDomain(): OrdenServicio =
    OrdenServicio(
        id = id,
        rutaId = rutaId,
        puntoId = puntoId,
        estado = EstadoOS.valueOf(estado.uppercase()),
        fotos = fotos ?: emptyList(), // para normalizar null -> lista vacía
        observacion = observacion
    )