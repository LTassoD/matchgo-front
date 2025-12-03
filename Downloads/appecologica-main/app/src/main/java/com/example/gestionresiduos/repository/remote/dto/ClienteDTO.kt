// En: repository/remote/dto/ClienteDTO.kt
package com.example.gestionresiduos.repository.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para RECIBIR y ENVIAR datos de un cliente a la API.
 * Se usa @SerializedName en todas las propiedades para desacoplar el código de la app
 * de los nombres exactos de los campos en el JSON del backend.
 */
data class ClienteDTO(

    @SerializedName("id_cliente")
    val idcliente: Int?, // Hecho opcional para ser más robusto

    @SerializedName("nombre_empresa")
    val nombreEmpresa: String?,

    @SerializedName("rut")
    val rut: String?,

    @SerializedName("direccion")
    val direccion: String?,

    @SerializedName("telefono")
    val telefono: String?,

    @SerializedName("correo")
    val correo: String?,

    @SerializedName("material")
    val material: String?,

    @SerializedName("frecuencia")
    val frecuencia: String?,

    @SerializedName("foto_uri") // Asumiendo que el JSON usa snake_case
    val fotoUri: String?
)
