package com.example.gestionresiduos.repository.local

import com.example.gestionresiduos.model.HistorialLabor

interface HistorialLaborDao {
    fun historialDelChofer(choferId: String): List<HistorialLabor>
}

internal object InMemoryHistorialLaborDao : HistorialLaborDao {
    override fun historialDelChofer(choferId: String): List<HistorialLabor> =
        AppDatabase.historial.filter { it.choferId == choferId }.sortedByDescending { it.fecha }
}
