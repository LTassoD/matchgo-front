package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.PuntoRecoleccion
import com.example.gestionresiduos.repository.remote.dto.PuntoDTO

fun PuntoDTO.toDomain(): PuntoRecoleccion =
    PuntoRecoleccion(
        id = this.id,
        clienteId = this.clienteId,
        nombreCliente = this.nombreCliente ?: "",
        direccion = this.direccion,
        material = this.material,
        contenedor = this.contenedor,
        estado = this.estado ?: "",
        observaciones = this.observaciones
    )
