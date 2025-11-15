package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.repository.remote.dto.ClienteDTO

fun ClienteDTO.toDomain(): Cliente =
    Cliente(id = id, nombre = nombre, direccion = direccion)