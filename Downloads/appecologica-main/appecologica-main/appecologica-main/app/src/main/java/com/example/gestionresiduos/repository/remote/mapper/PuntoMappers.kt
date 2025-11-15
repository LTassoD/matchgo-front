package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.PuntoRecoleccion
import com.example.gestionresiduos.repository.remote.dto.PuntoDTO

fun PuntoDTO.toDomain(): PuntoRecoleccion =
    PuntoRecoleccion(
        id = id,
        clienteId = clienteId,
        direccion = direccion,
        material = material,
        contenedor = contenedor,
        observaciones = observaciones
    )