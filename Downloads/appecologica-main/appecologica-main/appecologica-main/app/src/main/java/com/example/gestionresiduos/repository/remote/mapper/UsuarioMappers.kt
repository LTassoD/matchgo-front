package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.Rol
import com.example.gestionresiduos.model.Usuario
import com.example.gestionresiduos.repository.remote.dto.UsuarioDTO
import com.example.gestionresiduos.repository.remote.dto.LoginResponseDTO

// Convierte el String rol del backend a enum Rol seguro
fun UsuarioDTO.toDomain(): Usuario =
    Usuario(
        id = id,
        nombre = nombre,
        rut = rut,
        rol = Rol.valueOf(rol.uppercase()) // asegura mayúsculas
    )

// Mapea LoginResponseDTO (que incluye token) a Usuario ignorando token
fun LoginResponseDTO.toDomain(): Usuario =
    Usuario(
        id = id,
        nombre = nombre,
        rut = rut,
        rol = Rol.valueOf(rol.uppercase())
    )
