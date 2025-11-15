package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.*

interface Repository {
    suspend fun login(rut: String, password: String): Usuario
    suspend fun rutasParaChofer(choferId: String): List<Ruta>
    suspend fun rutaPorId(rutaId: String): Ruta
    suspend fun obtenerOrden(rutaId: String, puntoId: String): OrdenServicio
    suspend fun iniciarOrden(puntoId: String)
    suspend fun finalizarOrden(puntoId: String, observacion: String?)
    suspend fun listarClientes(): List<Cliente>
}