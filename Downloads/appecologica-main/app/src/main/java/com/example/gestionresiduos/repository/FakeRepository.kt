package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.*
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRepository : Repository {

    private val empleadosFlow = MutableStateFlow<List<Empleado>>(emptyList())
    private val clientesFlow = MutableStateFlow<List<Cliente>>(emptyList())
    private val vehiculosFlow = MutableStateFlow<List<Vehiculo>>(emptyList())
    private val materialesFlow = MutableStateFlow<List<Material>>(emptyList())


    // Función de utilidad para configurar el estado inicial de las pruebas
    fun setEmpleados(empleados: List<Empleado>) {
        empleadosFlow.value = empleados
    }

    // =================================================================
    //         IMPLEMENTACIONES REALES PARA PRUEBAS DE EMPLEADOS
    // =================================================================

    override suspend fun listarEmpleados(): List<Empleado> {
        return empleadosFlow.value
    }

    override suspend fun eliminarEmpleado(empleadoId: Int) {
        val empleadosActuales = empleadosFlow.value
        empleadosFlow.value = empleadosActuales.filterNot { it.id == empleadoId }
    }

    override suspend fun actualizarEmpleado(empleado: Empleado) {
        val empleadosActuales = empleadosFlow.value
        empleadosFlow.value = empleadosActuales.map {
            if (it.id == empleado.id) empleado else it
        }
    }

    override suspend fun registrarEmpleado(empleado: Empleado): Empleado {
        val empleadosActuales = empleadosFlow.value.toMutableList()
        // Simulamos un ID autoincremental
        val nuevoId = (empleadosActuales.maxOfOrNull { it.id } ?: 0) + 1
        val nuevoEmpleado = empleado.copy(id = nuevoId)
        empleadosActuales.add(nuevoEmpleado)
        empleadosFlow.value = empleadosActuales
        return nuevoEmpleado
    }

    // =================================================================
    //         MÉTODOS NO USADOS EN LAS PRUEBAS ACTUALES (IMPLEMENTACIONES VACÍAS)
    // =================================================================

    override suspend fun login(rut: String, pass: String): Pair<Empleado, String?> {
        throw NotImplementedError("La función 'login' no es necesaria para estas pruebas")
    }

    // --- Clientes ---
    override suspend fun listarClientes(): List<Cliente> = clientesFlow.value
    override suspend fun crearCliente(nuevoCliente: Cliente): Cliente { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun actualizarCliente(clienteActualizado: Cliente): Cliente { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun eliminarCliente(clienteId: Int) { /* No hace nada */ }

    // --- Rutas ---
    override suspend fun asignarRuta(clienteId: Int, choferId: Int, vehiculoId: Int): Cliente {
        throw NotImplementedError("La función 'asignarRuta' no es necesaria para estas pruebas")
    }
    override suspend fun crearRuta(ruta: Ruta): Ruta { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun actualizarRuta(ruta: Ruta): Ruta { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun eliminarRuta(rutaId: String) { /* No hace nada */ }

    // --- Vehículos ---
    override suspend fun listarVehiculos(): List<Vehiculo> = vehiculosFlow.value
    override suspend fun crearVehiculo(nuevoVehiculo: Vehiculo): Vehiculo { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun actualizarVehiculo(vehiculoActualizado: Vehiculo): Vehiculo { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun eliminarVehiculo(vehiculoId: Int) { /* No hace nada */ }

    // --- Materiales ---
    override suspend fun listarMateriales(): List<Material> = materialesFlow.value
    override suspend fun crearMaterial(material: Material): Material { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun actualizarMaterial(materialActualizado: Material): Material { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun eliminarMaterial(materialId: Int) { /* No hace nada */ }

    // --- Reportes e Historial ---
    override suspend fun obtenerRankingEmpleados(): List<Pair<Empleado, Float>> = emptyList()
    override suspend fun obtenerHistorialEmpleado(empleadoId: Int): List<HistorialLabor> = emptyList()
    override suspend fun enviarComentario(userId: Int, comentario: String) { /* No hace nada */ }

    // --- Operaciones del Chofer ---
    override suspend fun rutasParaChofer(choferId: String): List<Ruta> = emptyList()
    override suspend fun rutaPorId(rutaId: String): Ruta { throw NotImplementedError("Función no implementada para pruebas") }
    override suspend fun iniciarOrden(puntoId: String) { /* No hace nada */ }
    override suspend fun finalizarOrden(puntoId: String, observacion: String?) { /* No hace nada */ }
}
