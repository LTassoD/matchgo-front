package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.model.Material
import com.example.gestionresiduos.model.Usuario
import com.example.gestionresiduos.model.Vehiculo
import com.example.gestionresiduos.repository.local.InMemoryClienteDao
import com.example.gestionresiduos.repository.local.InMemoryMaterialDao
import com.example.gestionresiduos.repository.local.InMemoryVehiculoDao
import com.example.gestionresiduos.repository.local.UsuarioDao

interface AdminRepository {
    suspend fun obtenerClientes(forceRemote: Boolean = false): List<Cliente>
    suspend fun crearOActualizarCliente(cliente: Cliente)
    fun obtenerVehiculos(): List<Vehiculo>
    suspend fun registrarVehiculo(vehiculo: Vehiculo)
    fun actualizarEstadoVehiculo(id: String, habilitado: Boolean)
    fun obtenerMateriales(): List<Material>
    suspend fun crearOModificarMaterial(material: Material)
    fun obtenerUsuarios(): List<Usuario>
}

class AdminRepositoryImpl(
    private val remote: Repository = RemoteRepository(),
    private val clienteDao: com.example.gestionresiduos.repository.local.ClienteDao = InMemoryClienteDao,
    private val vehiculoDao: com.example.gestionresiduos.repository.local.VehiculoDao = InMemoryVehiculoDao,
    private val materialDao: com.example.gestionresiduos.repository.local.MaterialDao = InMemoryMaterialDao,
    private val usuarioDao: UsuarioDao = com.example.gestionresiduos.repository.local.InMemoryUsuarioDao
) : AdminRepository {
    override suspend fun obtenerClientes(forceRemote: Boolean): List<Cliente> {
        return if (forceRemote) {
            runCatching { remote.listarClientes() }
                .onSuccess { clientes -> clientes.forEach { clienteDao.insert(it) } }
                .getOrElse { clienteDao.getAll() }
        } else {
            clienteDao.getAll().ifEmpty {
                val remoteClientes = runCatching { remote.listarClientes() }.getOrDefault(emptyList())
                remoteClientes.forEach { clienteDao.insert(it) }
                remoteClientes
            }
        }
    }

    override suspend fun crearOActualizarCliente(cliente: Cliente) {
        clienteDao.insert(cliente)
    }

    override fun obtenerVehiculos(): List<Vehiculo> = vehiculoDao.getAll()

    override suspend fun registrarVehiculo(vehiculo: Vehiculo) {
        vehiculoDao.upsert(vehiculo)
    }

    override fun actualizarEstadoVehiculo(id: String, habilitado: Boolean) {
        vehiculoDao.toggleEstado(id, habilitado)
    }

    override fun obtenerMateriales(): List<Material> = materialDao.getAll()

    override suspend fun crearOModificarMaterial(material: Material) {
        materialDao.upsert(material)
    }

    override fun obtenerUsuarios(): List<Usuario> = usuarioDao.getAll().map { it.usuario }
}
