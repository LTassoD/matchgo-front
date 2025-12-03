package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.repository.remote.dto.ClienteDTO
import com.example.gestionresiduos.repository.remote.dto.CrearClienteRequestDTO
import com.example.gestionresiduos.repository.remote.dto.CrearClienteResponseDTO

/**
 * Convierte el modelo de dominio 'Cliente' a un 'CrearClienteRequestDTO' para ENVIAR datos a la API.
 */
fun Cliente.toRequestDto(): CrearClienteRequestDTO {
    // Esta función toma los datos de tu modelo de la app (Cliente) y los prepara para el envío.
    return CrearClienteRequestDTO(
        // CORRECCIÓN: El DTO espera 'nombreEmpresa', pero tu modelo usa 'nombre'. Aquí se hace la "traducción".
        nombreEmpresa = this.nombre,
        rut = this.rut,
        direccion = this.direccion,
        telefono = this.telefono,
        correo = this.correo,
        material = this.material,
        frecuencia = this.frecuencia,
        fotoUri = this.fotoUri
    )
}

fun CrearClienteResponseDTO.toDomain(): Cliente {
    // Esta función toma la respuesta del servidor después de crear un cliente.
    // CORRECCIÓN: Se mapean los campos del DTO de respuesta a tu modelo de dominio 'Cliente'.
    return Cliente(
        idcliente = this.idcliente,
        nombre = this.nombreEmpresa ?: "Nombre no disponible", // Se traduce 'nombreEmpresa' a 'nombre'
        rut = this.rut ?: "RUT no disponible",
        direccion = this.direccion ?: "Dirección no disponible",
        telefono = this.telefono ?: "Teléfono no disponible",
        correo = this.correo ?: "Correo no disponible",
        material = this.material ?: "",
        frecuencia = this.frecuencia ?: "",
        fotoUri = this.fotoUri
    )
}

/**
 * Convierte el 'ClienteDTO' (de la lista de clientes) a un modelo de dominio 'Cliente'.
 */
fun ClienteDTO.toDomain(): Cliente {
    // Esta función toma un objeto de la lista de clientes que devuelve la API.
    // CORRECCIÓN: Se mapean los campos del DTO de listado a tu modelo de dominio 'Cliente'.
    return Cliente(
        // Se manejan los posibles nulos para evitar crashes.
        idcliente = this.idcliente ?: throw IllegalArgumentException("El ID del cliente no puede ser nulo"),
        nombre = this.nombreEmpresa ?: "Nombre no disponible", // Se traduce 'nombreEmpresa' a 'nombre'
        rut = this.rut ?: "RUT no disponible",
        direccion = this.direccion ?: "Dirección no disponible",
        telefono = this.telefono ?: "Teléfono no disponible",
        correo = this.correo ?: "Correo no disponible",
        material = this.material ?: "",
        frecuencia = this.frecuencia ?: "",
        fotoUri = this.fotoUri
    )
}
