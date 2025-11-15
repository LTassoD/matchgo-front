package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.model.Material
import com.example.gestionresiduos.model.Usuario
import com.example.gestionresiduos.model.Vehiculo
import com.example.gestionresiduos.repository.local.InMemoryClienteDao
import com.example.gestionresiduos.repository.local.InMemoryMaterialDao
import com.example.gestionresiduos.repository.local.InMemoryUsuarioDao
import com.example.gestionresiduos.repository.local.InMemoryVehiculoDao

class AdminRepositoryFake(
    private val clienteDao: com.example.gestionresiduos.repository.local.ClienteDao = InMemoryClienteDao,
    private val materialDao: com.example.gestionresiduos.repository.local.MaterialDao = InMemoryMaterialDao,
    private val vehiculoDao: com.example.gestionresiduos.repository.local.VehiculoDao = InMemoryVehiculoDao,
    private val usuarioDao: com.example.gestionresiduos.repository.local.UsuarioDao = InMemoryUsuarioDao
) : AdminRepository {
    override suspend fun obtenerClientes(forceRemote: Boolean): List<Cliente> = clienteDao.getAll()

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
