package com.example.gestionresiduos.repository.remote

import com.example.gestionresiduos.repository.remote.dto.ClienteDTO
import com.example.gestionresiduos.repository.remote.dto.FinalizarOrdenRequestDTO
import com.example.gestionresiduos.repository.remote.dto.LoginRequestDTO
import com.example.gestionresiduos.repository.remote.dto.LoginResponseDTO
import com.example.gestionresiduos.repository.remote.dto.OrdenServicioDTO
import com.example.gestionresiduos.repository.remote.dto.RutaDTO
import com.example.gestionresiduos.repository.remote.dto.UsuarioDTO
import com.example.gestionresiduos.repository.r.dto.*
import retrofit2.http.*

// fijarse bien en las rutas de backend /api/v1/ u otros nombres.
interface ApiService {

    // AUTH
    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequestDTO): LoginResponseDTO

    // CHOFER / RUTAS
    @GET("api/chofer/{choferId}/rutas")
    suspend fun rutasParaChofer(@Path("choferId") choferId: String): List<RutaDTO>

    @GET("api/rutas/{rutaId}")
    suspend fun rutaPorId(@Path("rutaId") rutaId: String): RutaDTO

    // ORDENES
    @GET("api/ordenes/{rutaId}/{puntoId}")
    suspend fun obtenerOrden(
        @Path("rutaId") rutaId: String,
        @Path("puntoId") puntoId: String
    ): OrdenServicioDTO

    @POST("api/ordenes/{puntoId}/iniciar")
    suspend fun iniciarOrden(@Path("puntoId") puntoId: String)

    @POST("api/ordenes/{puntoId}/finalizar")
    suspend fun finalizarOrden(
        @Path("puntoId") puntoId: String,
        @Body body: FinalizarOrdenRequestDTO
    )

    // ADMIN
    @GET("api/admin/clientes")
    suspend fun listarClientes(): List<ClienteDTO>
}
