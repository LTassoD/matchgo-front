package com.example.gestionresiduos.model




data class Cliente(

    val idcliente: Int? = null,
    val nombre: String,
    val rut: String,
    val direccion: String,
    val telefono: String,
    val correo: String,
    val material: String,
    val frecuencia: String,
    val fotoUri : String?,
    var idChoferAsignado: Int? = null,
    var idVehiculoAsignado: Int? = null
)