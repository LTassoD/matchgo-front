package com.example.gestionresiduos.repository.local

import com.example.gestionresiduos.model.Vehiculo

interface VehiculoDao {
    fun getAll(): List<Vehiculo>
    fun upsert(vehiculo: Vehiculo)
    fun toggleEstado(id: String, habilitado: Boolean)
}

internal object InMemoryVehiculoDao : VehiculoDao {
    override fun getAll(): List<Vehiculo> = AppDatabase.vehiculos.toList()

    override fun upsert(vehiculo: Vehiculo) {
        AppDatabase.vehiculos.removeAll { it.id == vehiculo.id }
        AppDatabase.vehiculos.add(vehiculo)
    }

    override fun toggleEstado(id: String, habilitado: Boolean) {
        AppDatabase.vehiculos.replaceAll { vehiculo ->
            if (vehiculo.id == id) vehiculo.copy(habilitado = habilitado) else vehiculo
        }
    }
}
