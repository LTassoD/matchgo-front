package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.*


interface Repository {

    // --- AUTENTICACIÓN ---
    suspend fun login(rut: String, pass: String): Pair<Empleado, String?>

    // --- GESTIÓN DE CLIENTES ---
    suspend fun listarClientes(): List<Cliente>
    suspend fun crearCliente(nuevoCliente: Cliente): Cliente
    suspend fun actualizarCliente(clienteActualizado: Cliente): Cliente
    suspend fun eliminarCliente(clienteId: Int)

    // --- GESTIÓN DE EMPLEADOS ---
    suspend fun listarEmpleados(): List<Empleado>
    suspend fun registrarEmpleado(empleado: Empleado): Empleado
    suspend fun eliminarEmpleado(empleadoId: Int)
    suspend fun actualizarEmpleado(empleado: Empleado)

    // --- GESTIÓN DE RUTAS ---
    suspend fun asignarRuta(clienteId: Int, choferId: Int, vehiculoId: Int): Cliente
    suspend fun crearRuta(ruta: Ruta): Ruta
    suspend fun actualizarRuta(ruta: Ruta): Ruta
    suspend fun eliminarRuta(rutaId: String)

    // --- GESTIÓN DE VEHÍCULOS ---
    suspend fun listarVehiculos(): List<Vehiculo>
    suspend fun crearVehiculo(nuevoVehiculo: Vehiculo): Vehiculo
    suspend fun actualizarVehiculo(vehiculoActualizado: Vehiculo): Vehiculo
    suspend fun eliminarVehiculo(vehiculoId: Int)

    // --- GESTIÓN DE MATERIALES ---
    suspend fun listarMateriales(): List<Material>
    suspend fun crearMaterial(material: Material): Material
    suspend fun actualizarMaterial(materialActualizado: Material): Material
    suspend fun eliminarMaterial(materialId: Int)


    // --- REPORTES ---
    suspend fun obtenerRankingEmpleados(): List<Pair<Empleado, Float>>
    suspend fun obtenerHistorialEmpleado(empleadoId: Int): List<HistorialLabor>

    suspend fun enviarComentario(userId: Int, comentario: String)

    suspend fun rutasParaChofer(choferId: String): List<Ruta>
    suspend fun rutaPorId(rutaId: String): Ruta

    suspend fun iniciarOrden(puntoId: String)
    suspend fun finalizarOrden(puntoId: String, observacion: String?)
}
