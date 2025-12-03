package com.example.gestionresiduos.model


data class Vehiculo(
    val id: Int =0,
    val patente: String,
    val marca: String,
    val modelo: String,
    val anio: Int,
    val capacidad: Int
)
