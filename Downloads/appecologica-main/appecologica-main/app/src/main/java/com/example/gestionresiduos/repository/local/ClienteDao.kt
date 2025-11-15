package com.example.gestionresiduos.repository.local

import com.example.gestionresiduos.model.Cliente
import kotlinx.coroutines.delay

interface ClienteDao {
    suspend fun getAll(): List<Cliente>
    suspend fun insert(cliente: Cliente)
}

internal object InMemoryClienteDao : ClienteDao {
    override suspend fun getAll(): List<Cliente> {
        delay(150) // Simula IO
        return AppDatabase.clientes.toList()
    }

    override suspend fun insert(cliente: Cliente) {
        AppDatabase.clientes.removeAll { it.id == cliente.id }
        AppDatabase.clientes.add(cliente)
    }
}
