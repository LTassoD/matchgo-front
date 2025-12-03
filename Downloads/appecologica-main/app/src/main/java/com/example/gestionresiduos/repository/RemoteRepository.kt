package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.*
import com.example.gestionresiduos.repository.remote.ApiClient
import com.example.gestionresiduos.repository.remote.dto.AsignacionRequestDTO
import com.example.gestionresiduos.repository.remote.dto.ComentarioRequestDTO
import com.example.gestionresiduos.repository.remote.dto.FinalizarOrdenRequestDTO
import com.example.gestionresiduos.repository.remote.dto.LoginRequestDTO
import com.example.gestionresiduos.repository.remote.mapper.toDomain
import com.example.gestionresiduos.repository.remote.mapper.*
import retrofit2.HttpException


class RemoteRepository : Repository {

    // =================================================================
    //         AUTENTICACIÓN Y REGISTRO
    // =================================================================
    override suspend fun login(rut: String, pass: String): Pair<Empleado, String?> {
        val request = LoginRequestDTO(rut = rut, password = pass)
        val response = ApiClient.api.login(request)
        return response.toDomain()
    }

    override suspend fun crearCliente(nuevoCliente: Cliente): Cliente {
        val request = nuevoCliente.toRequestDto()
        val response = ApiClient.api.crearCliente(request)
        if (!response.isSuccessful) throw HttpException(response)
        val body = response.body() ?: throw IllegalStateException("Respuesta vacía al crear cliente")
        return body.toDomain()
    }

    override suspend fun registrarEmpleado(empleado: Empleado): Empleado {
        val request = empleado.toRequestDto()
        val response = ApiClient.api.crearEmpleado(request)
        if (!response.isSuccessful) throw HttpException(response)
        val body = response.body() ?: throw IllegalStateException("Respuesta vacía al crear empleado")
        return body.toDomain()
    }
    override suspend fun listarEmpleados(): List<Empleado> {
        return ApiClient.api.listarEmpleados().map { it.toDomain() }
    }

    override suspend fun eliminarEmpleado(empleadoId: Int) {
        val response = ApiClient.api.eliminarEmpleado(empleadoId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
    }
    override suspend fun actualizarEmpleado(empleado: Empleado) {
        val request = empleado.toDto()
        val response = ApiClient.api.actualizarEmpleado(empleado.id, request)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
    }

    // =================================================================
    //         GESTIÓN DE CLIENTES
    // =================================================================
    override suspend fun listarClientes(): List<Cliente> {
        val listaDeDTOs = ApiClient.api.listarClientes()
        return listaDeDTOs.map { it.toDomain() }
    }

    override suspend fun actualizarCliente(clienteActualizado: Cliente): Cliente {
        val clienteId = clienteActualizado.idcliente
            ?: throw IllegalArgumentException("El ID del cliente no puede ser nulo para actualizarlo.")

        val request = clienteActualizado.toRequestDto()
        val responseDto = ApiClient.api.actualizarCliente(clienteId, request)
        return responseDto.toDomain()
    }

    override suspend fun eliminarCliente(clienteId: Int) {
        val response = ApiClient.api.eliminarCliente(clienteId)

        if (!response.isSuccessful) {
            throw Exception("Error del servidor al eliminar el cliente: ${response.code()}")
        }

    }


    // =================================================================
    //         GESTIÓN DE RUTAS Y ASIGNACIONES
    // =================================================================
    override suspend fun asignarRuta(clienteId: Int, choferId: Int, vehiculoId: Int): Cliente {
        val request = AsignacionRequestDTO(idChofer = choferId, idVehiculo = vehiculoId)
        val responseDto = ApiClient.api.asignarRuta(clienteId, request)
        return responseDto.toDomain()
    }

    override suspend fun crearRuta(ruta: Ruta): Ruta {
        val request = ruta.toRequestDto()
        val responseDto = ApiClient.api.crearRuta(request)
        return responseDto.toDomain()
    }

    override suspend fun actualizarRuta(ruta: Ruta): Ruta {
        val request = ruta.toRequestDto()
        val responseDto = ApiClient.api.actualizarRuta(ruta.id, request)
        return responseDto.toDomain()
    }

    override suspend fun eliminarRuta(rutaId: String) {
        ApiClient.api.eliminarRuta(rutaId)
    }

    // =================================================================
    //         GESTIÓN DE EMPLEADOS Y VEHÍCULOS
    // =================================================================

    override suspend fun crearVehiculo(nuevoVehiculo: Vehiculo): Vehiculo {
        val request = nuevoVehiculo.toRequestDto()
        val response = ApiClient.api.crearVehiculo(request)
        if (!response.isSuccessful) throw HttpException(response)
        val body = response.body() ?: throw IllegalStateException("Respuesta vacía al crear vehículo")
        return body.toDomain()
    }

    override suspend fun listarVehiculos(): List<Vehiculo> {
        val listaDto = ApiClient.api.listarVehiculos()
        return listaDto.map { it.toDomain() }
    }

    override suspend fun actualizarVehiculo(vehiculoActualizado: Vehiculo): Vehiculo {
        val request = vehiculoActualizado.toRequestDto() // Necesitas este mapper
        val vehiculoId = vehiculoActualizado.id ?: throw IllegalArgumentException("ID de vehículo no puede ser nulo para actualizar")

        val response = ApiClient.api.actualizarVehiculo(vehiculoId, request)

        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return vehiculoActualizado
    }

    override suspend fun eliminarVehiculo(vehiculoId: Int) {
        val response = ApiClient.api.eliminarVehiculo(vehiculoId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
    }

    // =================================================================
    //         GESTIÓN DE MATERIALES
    // =================================================================
    override suspend fun crearMaterial(material: Material): Material {
        val request = material.toRequestDto()
        val responseDto = ApiClient.api.crearMaterial(request)
        return responseDto.toDomain()
    }

    override suspend fun listarMateriales(): List<Material> {
        val listaDto = ApiClient.api.listarMateriales()
        return listaDto.map { it.toDomain() }
    }

    override suspend fun actualizarMaterial(materialActualizado: Material): Material {
        val request = materialActualizado.toRequestDto()
        val responseDto = ApiClient.api.actualizarMaterial(materialActualizado.id, request)
        return responseDto.toDomain()
    }

    override suspend fun eliminarMaterial(materialId: Int) {
        val response = ApiClient.api.eliminarMaterial(materialId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }    }

    // =================================================================
    //         REPORTES E HISTORIAL
    // =================================================================
    override suspend fun obtenerRankingEmpleados(): List<Pair<Empleado, Float>> {
        val response = ApiClient.api.obtenerRankingEmpleados()
        return response.ranking.map { it.toDomain() }
    }

    override suspend fun obtenerHistorialEmpleado(empleadoId: Int): List<HistorialLabor> {
        val listaDto = ApiClient.api.obtenerHistorialEmpleado(empleadoId)
        return listaDto.map { it.toDomain() }
    }

    override suspend fun enviarComentario(userId: Int, comentario: String) {
        val request = ComentarioRequestDTO(comentario = comentario)
        ApiClient.api.enviarComentario(userId, request)
    }

    // =================================================================
    //         OPERACIONES DEL CHOFER
    // =================================================================
    override suspend fun rutasParaChofer(choferId: String): List<Ruta> {
        val listaDto = ApiClient.api.obtenerRutasPorChofer(choferId)
        return listaDto.map { it.toDomain() }
    }

    override suspend fun rutaPorId(rutaId: String): Ruta {
        val rutaDto = ApiClient.api.obtenerRutaPorId(rutaId)
        return rutaDto.toDomain()
    }


    override suspend fun iniciarOrden(puntoId: String) {
        ApiClient.api.iniciarOrden(puntoId)
    }

    override suspend fun finalizarOrden(puntoId: String, observacion: String?) {
        val request = FinalizarOrdenRequestDTO(observacion = observacion)
        ApiClient.api.finalizarOrden(puntoId, request)
    }
}
