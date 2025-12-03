@file:OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)

package com.example.gestionresiduos.ui.nav

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.gestionresiduos.ui.screens.*
import com.example.gestionresiduos.viewmodel.AdminViewModel
import com.example.gestionresiduos.viewmodel.AuthViewModel
import com.example.gestionresiduos.viewmodel.RutaViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(),
    adminViewModel: AdminViewModel = viewModel(),
    rutaViewModel: RutaViewModel = viewModel()
) {
    val empleado by authViewModel.empleado.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Route.Login.path
    ) {

        // =================================================================
        //                         1. PANTALLA DE LOGIN
        // =================================================================
        composable(
            route = Route.Login.path,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            LoginScreen(
                authViewModel = authViewModel,
                onChofer = {
                    navController.navigate(Route.ChoferHome.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onAdmin = {
                    navController.navigate(Route.AdminHome.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                }
            )
        }

        // =================================================================
        //                         2. PANTALLAS DEL CHOFER
        // =================================================================
        composable(Route.ChoferHome.path) {
            ChoferHomeScreen(onOpenRuta = { navController.navigate("chofer/rutas") })
        }

        composable("chofer/rutas") {
            empleado?.let { user ->
                RutasDelDiaScreen(
                    choferId = user.id,
                    rutaViewModel = rutaViewModel,
                    onOpenRuta = { rutaId -> navController.navigate("rutaDetalle/$rutaId") },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = "rutaDetalle/{rutaId}",
            arguments = listOf(navArgument("rutaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val rutaId = requireNotNull(backStackEntry.arguments?.getString("rutaId"))
            RutaPuntosScreen(
                rutaId = rutaId,
                rutaViewModel = rutaViewModel,
                onOpenPunto = { puntoId -> navController.navigate("ordenServicio/$rutaId/$puntoId") },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "ordenServicio/{rutaId}/{puntoId}",
            arguments = listOf(
                navArgument("rutaId") { type = NavType.StringType },
                navArgument("puntoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rutaId = requireNotNull(backStackEntry.arguments?.getString("rutaId"))
            val puntoId = requireNotNull(backStackEntry.arguments?.getString("puntoId"))
            OrdenServicioScreen(
                rutaId = rutaId,
                puntoId = puntoId,
                rutaViewModel = rutaViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // =================================================================
        //                         3. PANTALLAS DEL ADMIN
        // =================================================================
        composable(Route.AdminHome.path) {
            AdminHomeScreen(
                navController = navController,
                onOpenClientes = { navController.navigate(Route.AdminClientes.path) },
                onOpenRutas = { navController.navigate(Route.AdminRutas.path) },
                onOpenEmpleado = { navController.navigate(Route.AdminEmpleado.path) },
                onOpenVehiculos = { navController.navigate(Route.AdminVehiculos.path) },
                onOpenMateriales = { navController.navigate(Route.AdminMateriales.path) },
                onOpenReportes = { navController.navigate(Route.AdminEmpleado.path) },
                onOpenAsignaciones = { navController.navigate(Route.AdminAsignacion.path) },
                onOpenGraficos = { navController.navigate(Route.AdminGraficos.path) },
                onNavigateToSettings = { navController.navigate(Route.Settings.path) },
                onLogout = {
                    navController.navigate(Route.Login.path) {
                        popUpTo(0)
                    }
                }
            )
        }

        // --- 3.1. Pantallas de Gestión del Admin ---
        composable(Route.AdminClientes.path) {
            AdminClientesScreen(
                adminViewModel = adminViewModel,
                onNavigateToCreateCliente = { navController.navigate("editarCliente") },
                onNavigateToEditCliente = { clienteId -> navController.navigate("editarCliente?clienteId=$clienteId") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Route.AdminRutas.path) {
            AdminRutasScreen(
                rutaViewModel = rutaViewModel,
                onNavigateToCreateRuta = { navController.navigate("editarRuta") },
                onNavigateToEditRuta = { rutaId -> navController.navigate("editarRuta?rutaId=$rutaId") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Route.AdminEmpleado.path) {
            AdminEmpleadoScreen(
                adminViewModel = adminViewModel,
                onNavigateToCreateEmployee = { navController.navigate("editarEmpleado") },
                onNavigateToEditEmployee = { empleadoId -> navController.navigate("editarEmpleado?empleadoId=$empleadoId") },
                onOpenEmployeeDetail = { empleadoId -> navController.navigate("detalleEmpleado/$empleadoId") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Route.AdminVehiculos.path) {
            AdminVehiculosScreen(
                adminViewModel = adminViewModel,
                onNavigateToCreateVehiculo = { navController.navigate("crearVehiculo") },
                onNavigateToEditVehiculo = { vehiculoId -> navController.navigate("editarVehiculo/$vehiculoId") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Route.AdminMateriales.path) {
            AdminMaterialesScreen(
                adminViewModel = adminViewModel,
                onNavigateToCreateMaterial = { navController.navigate("crearMaterial") },
                onNavigateToEditMaterial = { materialId -> navController.navigate("editarMaterial/$materialId") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Route.AdminAsignacion.path) {
            AdminAsignacionScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Route.AdminGraficos.path) {
            AdminGraficosScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Route.Settings.path) {
            SettingsScreen(
                onNavigate = { route ->
                    if (route == "back") navController.popBackStack() else navController.navigate(route)
                },
                onLogout = {
                    navController.navigate(Route.Login.path) { popUpTo(0) }
                }
            )
        }
        composable(Route.WorkInProgress.path) {
            WorkInProgressScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- 3.2. Pantallas de Detalle ---
        composable(
            route = "detalleEmpleado/{empleadoId}",
            arguments = listOf(navArgument("empleadoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val empleadoId = requireNotNull(backStackEntry.arguments?.getInt("empleadoId"))
            DetalleEmpleadoScreen(
                empleadoId = empleadoId,
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Route.DetalleReporte.path, // "detalleReporte/{userId}"
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = requireNotNull(backStackEntry.arguments?.getInt("userId"))
            DetalleReporteScreen(
                userId = userId,
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // =================================================================
        //      4. PANTALLAS DE CREACIÓN / EDICIÓN (UNIFICADAS Y CORREGIDAS)
        // =================================================================

        composable(
            route = "editarCliente?clienteId={clienteId}",
            arguments = listOf(navArgument("clienteId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            EditarClienteScreen(
                clienteId = backStackEntry.arguments?.getString("clienteId"),
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "editarEmpleado?empleadoId={empleadoId}",
            arguments = listOf(navArgument("empleadoId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            CrearEmpleadoScreen(
                empleadoId = backStackEntry.arguments?.getString("empleadoId"),
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "crearVehiculo"
        ) {
            EditorVehiculoRoute(
                vehiculoId = null,
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- RUTA PARA EDITAR VEHÍCULO ---
        composable(
            route = "editarVehiculo/{vehiculoId}",
            arguments = listOf(navArgument("vehiculoId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            EditorVehiculoRoute(
                vehiculoId = backStackEntry.arguments?.getString("vehiculoId")?.toIntOrNull(),
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "crearMaterial"
        ) {
            EditarMaterialScreen(
                materialId = null,
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "editarMaterial/{materialId}", // Ruta con argumento para editar
            arguments = listOf(navArgument("materialId") { type = NavType.StringType })
        ) { backStackEntry ->
            EditarMaterialScreen(
                materialId = backStackEntry.arguments?.getString("materialId")?.toIntOrNull(),
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "editarRuta?rutaId={rutaId}",
            arguments = listOf(navArgument("rutaId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            EditarRutaScreen(
                rutaId = backStackEntry.arguments?.getString("rutaId"),
                rutaViewModel = rutaViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

// --- PANTALLA AUXILIAR TEMPORAL ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkInProgressScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("En Construcción") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("¡Esta funcionalidad estará disponible pronto!")
        }
    }
}
