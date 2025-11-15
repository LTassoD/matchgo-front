package com.example.gestionresiduos.repository

import com.example.gestionresiduos.repository.remote.NetworkModule
import com.example.gestionresiduos.repository.remote.dto.FinalizarOrdenRequestDTO
import com.example.gestionresiduos.repository.remote.dto.LoginRequestDTO
import com.example.gestionresiduos.repository.remote.mapper.*
import com.example.gestionresiduos.model.*

class RemoteRepository : Repository {

    // Api de Retrofit lista desde NetworkModule
    private val api = NetworkModule.api

    override suspend fun login(rut: String, password: String): Usuario {
        val dto = api.login(LoginRequestDTO(rut, password))
        return dto.toDomain()
    }

    override suspend fun rutasParaChofer(choferId: String): List<Ruta> =
        api.rutasParaChofer(choferId).map { it.toDomain() }

    override suspend fun rutaPorId(rutaId: String): Ruta =
        api.rutaPorId(rutaId).toDomain()

    override suspend fun obtenerOrden(rutaId: String, puntoId: String): OrdenServicio =
        api.obtenerOrden(rutaId, puntoId).toDomain()

    override suspend fun iniciarOrden(puntoId: String) {
        api.iniciarOrden(puntoId)
    }

    override suspend fun finalizarOrden(puntoId: String, observacion: String?) {
        api.finalizarOrden(puntoId, FinalizarOrdenRequestDTO(observacion))
    }

    override suspend fun listarClientes(): List<Cliente> =
        api.listarClientes().map { it.toDomain() }
}