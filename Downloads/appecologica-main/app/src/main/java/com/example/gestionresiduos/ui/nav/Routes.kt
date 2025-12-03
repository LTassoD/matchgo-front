package com.example.gestionresiduos.ui.nav


sealed class Route(val path: String) {

    // =================================================================
    //         RUTA DE AUTENTICACIÓN
    // =================================================================
    data object Login : Route("login")

    // =================================================================
    //         RUTAS DEL CHOFER
    // =================================================================
    data object ChoferHome : Route("chofer/home") // Pantalla principal del chofer con sus rutas
    data object ChoferHistorial : Route("chofer/historial")

    // --- Rutas con argumentos para Chofer ---
    data object PuntoDetalle : Route("chofer/punto/{rutaId}/{puntoId}") {
        fun build(rutaId: String, puntoId: String) = "chofer/punto/$rutaId/$puntoId"
    }

    data object CapturaFotos : Route("chofer/captura/{rutaId}/{puntoId}") {
        fun build(rutaId: String, puntoId: String) = "chofer/captura/$rutaId/$puntoId"
    }

    // =================================================================
    //         RUTAS DEL ADMINISTRADOR
    // =================================================================
    data object AdminHome : Route("admin/home") // Panel principal del admin

    // --- Secciones de Gestión del Admin ---
    data object AdminClientes : Route("admin/clientes")
    data object AdminRutas : Route("admin/rutas")
    data object AdminEmpleado : Route("admin/empleados")
    data object AdminCrearEmpleado : Route("admin/empleados/crear")// Renombrado de 'Empleado' a 'Usuarios' para consistencia
    data object AdminVehiculos : Route("admin/vehiculos")
    data object AdminMateriales : Route("admin/materiales")
    data object AdminAsignacion : Route("admin/asignacion")
    data object AdminReportes : Route("admin/reportes")
    data object AdminGraficos : Route("admin/graficos")

    // --- Rutas de Creación/Edición del Admin (con argumentos opcionales) ---
    // Se definen en el NavHost directamente, pero podemos tener constantes si se desea.
    // Ejemplo: data object EditarCliente : Route("admin/clientes/editar?clienteId={clienteId}")

    // --- Rutas con argumentos para Admin ---
    data object DetalleReporte : Route("admin/reportes/{userId}") {
        fun createRoute(userId: String) = "admin/reportes/$userId"
    }

    // =================================================================
    //         RUTAS DE CONFIGURACIÓN (Comunes o específicas)
    // =================================================================
    data object Settings : Route("settings")
    data object SettingsProfile : Route("settings/profile")
    data object SettingsPrivacy : Route("settings/privacy")
    data object SettingsData : Route("settings/data")
    data object SettingsAbout : Route("settings/about")

    // Ruta para funcionalidades en desarrollo (Work In Progress)
    data object WorkInProgress : Route("wip")
}
