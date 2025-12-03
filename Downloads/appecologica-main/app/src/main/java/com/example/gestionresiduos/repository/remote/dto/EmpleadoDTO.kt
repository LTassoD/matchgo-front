package com.example.gestionresiduos.repository.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Representación de un empleado en las respuestas de la API.
 */
data class EmpleadoDTO(
    @SerializedName("id_empleado") // Ejemplo de anotación
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("rut")
    val rut: String,

    @SerializedName("correo")
    val correo: String,

    @SerializedName("telefono")
    val telefono: String,

    @SerializedName("rol")
    val rol: String,

    // --- CORRECCIÓN DEFINITIVA: Se añade el campo que faltaba ---
    // El nombre "foto_uri" debe coincidir con el que usa tu API.
    @SerializedName("foto_uri")
    val fotoUri: String? // Se define como opcional (String?) por si algún empleado no tiene foto.
)
