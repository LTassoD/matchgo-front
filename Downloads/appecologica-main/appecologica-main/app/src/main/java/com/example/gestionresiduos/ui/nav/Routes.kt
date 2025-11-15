package com.example.gestionresiduos.ui.nav

// Rutas "tipadas" para evitar strings mágicos
sealed class Route(val path: String) {

    // Login
    data object Login : Route("login")

    // Chofer
    data object ChoferHome : Route("chofer/home")
    data object RutaDetalle : Route("chofer/ruta/{rutaId}") {
        fun build(rutaId: String) = "chofer/ruta/$rutaId"
    }
    data object OrdenServicio : Route("chofer/os/{rutaId}/{puntoId}") {
        fun build(rutaId: String, puntoId: String) = "chofer/os/$rutaId/$puntoId"
    }

    // Admin
    data object AdminHome : Route("admin/home")
    data object AdminClientes : Route("admin/clientes")

    // --- Chofer ---
    data object RutasDelDia : Route("chofer/rutasDelDia")
    data object PuntoDetalle : Route("chofer/punto/{rutaId}/{puntoId}") {
        fun build(rutaId:String, puntoId:String) = "chofer/punto/$rutaId/$puntoId"
    }
    data object CapturaFotos : Route("chofer/captura/{rutaId}/{puntoId}") {
        fun build(rutaId:String, puntoId:String) = "chofer/captura/$rutaId/$puntoId"
    }
    data object HistorialRutas : Route("chofer/historial")

    // --- Admin extra ---
    data object AdminUsuarios : Route("admin/usuarios")
    data object AdminVehiculos : Route("admin/vehiculos")
    data object AdminMateriales : Route("admin/materiales")
    data object AdminReportes : Route("admin/reportes")

    // TODO: agrega aquí tus rutas extra manteniendo el mismo estilo
}