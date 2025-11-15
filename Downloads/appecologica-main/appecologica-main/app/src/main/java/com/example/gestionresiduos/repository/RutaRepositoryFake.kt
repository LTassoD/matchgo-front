package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.EstadoOS
import com.example.gestionresiduos.model.HistorialLabor
import com.example.gestionresiduos.model.OrdenServicio
import com.example.gestionresiduos.model.PuntoRecoleccion
import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.repository.local.HistorialLaborDao
import com.example.gestionresiduos.repository.local.InMemoryHistorialLaborDao
import java.time.LocalDate

class RutaRepositoryFake(
    private val historialDao: HistorialLaborDao = InMemoryHistorialLaborDao
) : RutaRepository {

    private val puntos = listOf(
        PuntoRecoleccion("p1", clienteId = "c1", direccion = "Av. Norte 123", material = "Cartón", contenedor = "240L", observaciones = "Horario 9:00"),
        PuntoRecoleccion("p2", clienteId = "c2", direccion = "Camino Los Pinos 45", material = "Vidrio", contenedor = "120L", observaciones = "Carga frágil"),
        PuntoRecoleccion("p3", clienteId = "c3", direccion = "Ruta 5 KM 50", material = "Plástico", contenedor = "360L", observaciones = null)
    )

    private val rutas = listOf(
        Ruta("r1", fecha = LocalDate.now(), choferId = "chofer-1", puntos = puntos),
        Ruta("r2", fecha = LocalDate.now().plusDays(1), choferId = "chofer-1", puntos = puntos.take(2))
    )

    private val ordenes = mutableListOf(
        OrdenServicio(id = "os1", rutaId = "r1", puntoId = "p1", estado = EstadoOS.PENDIENTE, fotos = emptyList(), observacion = null),
        OrdenServicio(id = "os2", rutaId = "r1", puntoId = "p2", estado = EstadoOS.PENDIENTE, fotos = emptyList(), observacion = null),
        OrdenServicio(id = "os3", rutaId = "r1", puntoId = "p3", estado = EstadoOS.PENDIENTE, fotos = emptyList(), observacion = null)
    )

    override suspend fun rutasParaChofer(choferId: String): List<Ruta> =
        rutas.filter { it.choferId == choferId }

    override suspend fun rutaPorId(rutaId: String): Ruta =
        rutas.first { it.id == rutaId }

    override suspend fun obtenerOrden(rutaId: String, puntoId: String): OrdenServicio =
        ordenes.first { it.rutaId == rutaId && it.puntoId == puntoId }

    override suspend fun iniciarOrden(puntoId: String) {
        ordenes.replaceAll { if (it.puntoId == puntoId) it.copy(estado = EstadoOS.EN_PROCESO) else it }
    }

    override suspend fun finalizarOrden(puntoId: String, observacion: String?) {
        ordenes.replaceAll {
            if (it.puntoId == puntoId) it.copy(estado = EstadoOS.COMPLETADA, observacion = observacion) else it
        }
    }

    override fun historialDelChofer(choferId: String): List<HistorialLabor> =
        historialDao.historialDelChofer(choferId)
}
