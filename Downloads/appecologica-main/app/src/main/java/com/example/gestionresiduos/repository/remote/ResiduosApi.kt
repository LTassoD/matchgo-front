package com.example.gestionresiduos.repository.remote

import com.example.gestionresiduos.repository.remote.dto.ClienteDTO
import com.example.gestionresiduos.repository.remote.dto.EmpleadoDTO
import com.example.gestionresiduos.repository.remote.dto.CrearClienteRequestDTO
import com.example.gestionresiduos.repository.remote.dto.CrearClienteResponseDTO
import com.example.gestionresiduos.repository.remote.dto.CrearEmpleadoRequestDTO
import com.example.gestionresiduos.repository.remote.dto.CrearEmpleadoResponseDTO
import com.example.gestionresiduos.repository.remote.dto.LoginRequestDTO
import com.example.gestionresiduos.repository.remote.dto.LoginResponseDTO
import com.example.gestionresiduos.repository.remote.dto.AsignacionRequestDTO
import com.example.gestionresiduos.repository.remote.dto.RankingResponseDTO
import com.example.gestionresiduos.repository.remote.dto.CrearRutaRequestDTO
import com.example.gestionresiduos.repository.remote.dto.RutaDTO
import com.example.gestionresiduos.repository.remote.dto.CrearMaterialRequestDTO
import com.example.gestionresiduos.repository.remote.dto.MaterialDTO
import com.example.gestionresiduos.repository.remote.dto.CrearVehiculoRequestDTO
import com.example.gestionresiduos.repository.remote.dto.VehiculoDTO
import com.example.gestionresiduos.repository.remote.dto.ComentarioRequestDTO
import com.example.gestionresiduos.repository.remote.dto.HistorialLaborDTO
import com.example.gestionresiduos.repository.remote.dto.FinalizarOrdenRequestDTO
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ResiduosApi {

    @POST("api/login")
    suspend fun login(@Body request: LoginRequestDTO): LoginResponseDTO

    // --- CREATE (Crear) ---
    @POST("clientes")
    suspend fun crearCliente(@Body request: CrearClienteRequestDTO): Response<CrearClienteResponseDTO>

    // Obtener la lista de TODOS los clientes
    @GET("clientes")
    suspend fun listarClientes(): List<ClienteDTO>


    // Actualizar un cliente existente. Se envía el cuerpo completo del cliente.
    @PUT("clientes/{id}")
    suspend fun actualizarCliente(
        @Path("id") clienteId: Int,
        @Body request: CrearClienteRequestDTO // Reutilizamos el DTO de creación
    ): ClienteDTO // La API devuelve el cliente actualizado

    // Eliminar un cliente por su ID
    @DELETE("clientes/{id}")
    suspend fun eliminarCliente(@Path("id") clienteId: Int): Response<Unit>


    // --- CORRECCIÓN: La nueva función va aquí, DENTRO de la interfaz ---
    @POST("empleados")
    suspend fun crearEmpleado(@Body request: CrearEmpleadoRequestDTO): Response<CrearEmpleadoResponseDTO>
    @GET("empleados")
    suspend fun listarEmpleados(): List<EmpleadoDTO>
    @DELETE("empleados/{id}")
    suspend fun eliminarEmpleado(@Path("id") empleadoId: Int): Response<Unit>
    @PUT("empleados/{id}")
    suspend fun actualizarEmpleado(@Path("id") empleadoId: Int, @Body request: EmpleadoDTO): Response<Unit>


    // --- ASIGNACIÓN ---
    @PUT("clientes/{id}/asignar")
    suspend fun asignarRuta(
        @Path("id") clienteId: Int,
        @Body request: AsignacionRequestDTO
    ): ClienteDTO // La API podría devolver el cliente actualizado

    // =================================================================
    //         SECCIÓN DE REPORTES
    // =================================================================
    @GET("reportes/ranking-empleados")
    suspend fun obtenerRankingEmpleados(): RankingResponseDTO

    // =================================================================
    //         SECCIÓN DE RUTAS
    // =================================================================

    // --- CREATE (Crear) ---
    @POST("rutas")
    suspend fun crearRuta(@Body request: CrearRutaRequestDTO): RutaDTO

    // --- READ (Leer) ---
    // Ya deberías tener una función para obtener las rutas de un chofer

    // --- UPDATE (Actualizar/Editar) ---
    @PUT("rutas/{id}")
    suspend fun actualizarRuta(
        @Path("id") rutaId: String,
        @Body request: CrearRutaRequestDTO // Reutilizamos el DTO de creación
    ): RutaDTO

    // --- DELETE (Eliminar) ---
    @DELETE("rutas/{id}")
    suspend fun eliminarRuta(@Path("id") rutaId: String)

    // =================================================================
    //         SECCIÓN DE VEHÍCULOS
    // =================================================================

    // --- CREATE (Crear) ---
    @POST("vehiculos")
    suspend fun crearVehiculo(@Body request: CrearVehiculoRequestDTO): Response<VehiculoDTO>

    // --- READ (Leer) ---
    @GET("vehiculos")
    suspend fun listarVehiculos(): List<VehiculoDTO>

    // --- UPDATE (Actualizar/Editar) ---
    @PUT("vehiculos/{id}")
    suspend fun actualizarVehiculo(
        @Path("id") vehiculoId: Int,
        @Body request: CrearVehiculoRequestDTO
    ): Response<Unit>

    // --- DELETE (Eliminar) ---
    @DELETE("vehiculos/{id}")
    suspend fun eliminarVehiculo(@Path("id") vehiculoId: Int): Response<Unit>

    // =================================================================
    //         SECCIÓN DE MATERIALES
    // =================================================================

    // --- CREATE (Crear) ---
    @POST("materiales")
    suspend fun crearMaterial(@Body request: CrearMaterialRequestDTO): MaterialDTO

    // --- READ (Leer) ---
    @GET("materiales")
    suspend fun listarMateriales(): List<MaterialDTO>

    // --- UPDATE (Actualizar/Editar) ---
    @PUT("materiales/{id}")
    suspend fun actualizarMaterial(
        @Path("id") materialId: Int,
        @Body request: CrearMaterialRequestDTO // Reutilizamos el DTO de creación
    ): MaterialDTO

    // --- DELETE (Eliminar) ---
    @DELETE("materiales/{id}")
    suspend fun eliminarMaterial(@Path("id") materialId: Int): Response<Unit>

    // =================================================================
    //         SECCIÓN DE HISTORIAL Y COMENTARIOS
    // =================================================================

    // --- READ (Leer Historial) ---
    @GET("empleados/{id}/historial")
    suspend fun obtenerHistorialEmpleado(@Path("id") empleadoId: Int): List<HistorialLaborDTO>

    // --- UPDATE (Enviar Comentario) ---
    // Usamos un POST o PUT. POST es común para añadir un recurso nuevo como un comentario.
    @POST("empleados/{id}/comentarios")
    suspend fun enviarComentario(
        @Path("id") empleadoId: Int,
        @Body request: ComentarioRequestDTO
    ) // No esperamos respuesta, o podría ser un 201 Created

    @GET("rutas/chofer/{id}")
    suspend fun obtenerRutasPorChofer(@Path("id") choferId: String): List<RutaDTO>

    @GET("rutas/{id}")
    suspend fun obtenerRutaPorId(@Path("id") rutaId: String): RutaDTO

    @POST("puntos/{id}/iniciar")
    suspend fun iniciarOrden(@Path("id") puntoId: String) // No se envía cuerpo, no se espera respuesta.

    @POST("puntos/{id}/finalizar")
    suspend fun finalizarOrden(@Path("id") puntoId: String, @Body request: FinalizarOrdenRequestDTO)





}


