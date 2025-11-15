package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.HistorialLabor
import com.example.gestionresiduos.model.OrdenServicio
import com.example.gestionresiduos.model.Ruta

interface RutaRepository {
    suspend fun rutasParaChofer(choferId: String): List<Ruta>
    suspend fun rutaPorId(rutaId: String): Ruta
    suspend fun obtenerOrden(rutaId: String, puntoId: String): OrdenServicio
    suspend fun iniciarOrden(puntoId: String)
    suspend fun finalizarOrden(puntoId: String, observacion: String?)
    fun historialDelChofer(choferId: String): List<HistorialLabor>
}

class RutaRepositoryRemote(
    private val remote: Repository = RemoteRepository(),
    private val historialDao: com.example.gestionresiduos.repository.local.HistorialLaborDao =
        com.example.gestionresiduos.repository.local.InMemoryHistorialLaborDao
) : RutaRepository {
    override suspend fun rutasParaChofer(choferId: String): List<Ruta> =
        remote.rutasParaChofer(choferId)

    override suspend fun rutaPorId(rutaId: String): Ruta = remote.rutaPorId(rutaId)

    override suspend fun obtenerOrden(rutaId: String, puntoId: String): OrdenServicio =
        remote.obtenerOrden(rutaId, puntoId)

    override suspend fun iniciarOrden(puntoId: String) {
        remote.iniciarOrden(puntoId)
    }

    override suspend fun finalizarOrden(puntoId: String, observacion: String?) {
        remote.finalizarOrden(puntoId, observacion)
    }

    override fun historialDelChofer(choferId: String): List<HistorialLabor> =
        historialDao.historialDelChofer(choferId)
}
