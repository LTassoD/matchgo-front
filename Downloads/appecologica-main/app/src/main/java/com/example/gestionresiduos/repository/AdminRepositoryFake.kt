package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class AdminRepositoryFake {

    // --- BASE DE DATOS FALSA DE HISTORIAL POR USUARIO ---
    private val fakeHistory = mapOf(
        2 to listOf( // Historial para el usuario con ID 2 ("Juan Chofer")
            HistorialLabor(1, 2, Date(), "R001", 5, "Todo en orden."),
            HistorialLabor(2, 2, Date(), "R002", 8, null),
            HistorialLabor(3, 2, Date(), "R003", 6, "Tráfico denso.")
        ),
        1 to emptyList() // Usuario con ID 1 ("Nico Administrador")
    )

    suspend fun getHistoryForUser(userId: Int): List<HistorialLabor> {
        delay(800)
        return fakeHistory[userId] ?: emptyList()
    }

    // ... (otras funciones como sendFeedback)

    // --- BASE DE DATOS FALSA DE MATERIALES (IDs como Int) ---
    private val _fakeMaterialsFlow = MutableStateFlow(
        mutableListOf(
            Material(id = 1, nombre = "Cartón", descripcion = "Cajas y embalajes."),
            Material(id = 2, nombre = "Plástico PET", descripcion = "Botellas de bebidas."),
        )
    )
    fun getMaterialsFlow() = _fakeMaterialsFlow.asStateFlow()

    suspend fun saveNewMaterial(material: Material): Boolean {
        delay(1000)
        val currentMaterials = _fakeMaterialsFlow.value.toMutableList()
        // No generamos ID porque en Room es autogenerado. Solo lo añadimos.
        currentMaterials.add(material)
        _fakeMaterialsFlow.value = currentMaterials
        return true
    }

    // --- BASE DE DATOS FALSA DE VEHÍCULOS (IDs como Int) ---
    private val _fakeVehiclesFlow = MutableStateFlow(
        mutableListOf(
            Vehiculo(id = 1, patente = "ABCD-12", marca = "Mercedes-Benz", modelo = "Atego", anio = 2021, capacidad = 10000),
            Vehiculo(id = 2, patente = "EFGH-34", marca = "Scania", modelo = "P-Series", anio = 2022, capacidad = 15000),
        )
    )
    fun getVehiclesFlow() = _fakeVehiclesFlow.asStateFlow()

    suspend fun saveNewVehicle(vehiculo: Vehiculo): Boolean {
        delay(1000)
        val currentVehicles = _fakeVehiclesFlow.value.toMutableList()
        currentVehicles.add(vehiculo)
        _fakeVehiclesFlow.value = currentVehicles
        return true
    }

    // --- BASE DE DATOS FALSA DE USUARIOS (IDs como Int) ---
    private val _fakeUsersFlow = MutableStateFlow(
        mutableListOf(
            Empleado(
                id = 1,
                nombre = "Nico Administrador",
                rut = "1-1",
                rol = UserRole.ADMIN,
                telefono = "+56900000001",
                correo = "nico.admin@empresa.com"
            ),
            Empleado(
                id = 2,
                nombre = "Juan Chofer",
                rut = "2-2",
                rol = UserRole.CHOFER,
                telefono = "+56900000002",
                correo = "juan.chofer@empresa.com"
            )
        )
    )
    fun getUsersFlow() = _fakeUsersFlow.asStateFlow()

    suspend fun saveNewEmployee(empleado: Empleado): Boolean {
        delay(1000)
        val currentUsers = _fakeUsersFlow.value.toMutableList()
        currentUsers.add(empleado)
        _fakeUsersFlow.value = currentUsers
        return true
    }

    // --- BASE DE DATOS FALSA DE CLIENTES (IDs como Int) ---
    private val _fakeClientsFlow = MutableStateFlow(
        mutableListOf(
            Cliente(
                idcliente = 1,
                nombre = "DuocUC Plaza Norte",
                rut = "12345678-9",
                direccion = "Av. Américo Vespucio 1501",
                telefono = "+56911112222",
                correo = "contacto@duoc.cl",
                material = "Cartón",
                frecuencia = "Semanal",
                fotoUri = null

            )
        )
    )
    fun getClientsFlow(): Flow<List<Cliente>> = _fakeClientsFlow.asStateFlow()

    suspend fun saveNewUser(cliente: Cliente): Boolean {
        delay(1000)
        val currentClients = _fakeClientsFlow.value.toMutableList()
        currentClients.add(cliente)
        _fakeClientsFlow.value = currentClients
        return true
    }

    // ... (otras funciones como updateAsignacion)
}
