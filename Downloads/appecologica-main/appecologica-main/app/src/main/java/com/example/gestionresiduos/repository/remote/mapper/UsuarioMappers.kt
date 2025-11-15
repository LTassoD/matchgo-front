package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.Rol
import com.example.gestionresiduos.model.Usuario
import com.example.gestionresiduos.repository.remote.dto.UsuarioDTO

// Convierte el String rol del backend a enum Rol seguro
fun UsuarioDTO.toDomain(): Usuario =
    Usuario(
        id = id,
        nombre = nombre,
        rut = rut,
        rol = Rol.valueOf(rol.uppercase()) // asegura mayúsculas
    )