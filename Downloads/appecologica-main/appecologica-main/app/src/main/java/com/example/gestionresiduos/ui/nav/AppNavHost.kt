package com.example.gestionresiduos.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.ExperimentalAnimationApi

// Importa tus pantallas (asegúrate de tenerlas en ui/screens)
import com.example.gestionresiduos.ui.screens.LoginScreen
import com.example.gestionresiduos.ui.screens.ChoferHomeScreen
import com.example.gestionresiduos.ui.screens.RutaDetalleScreen
import com.example.gestionresiduos.ui.screens.OrdenServicioScreen
import com.example.gestionresiduos.ui.screens.AdminHomeScreen
import com.example.gestionresiduos.ui.screens.AdminClientesScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost() {

    val nav = rememberNavController()

    AnimatedNavHost(
        navController = nav,
        startDestination = Route.Login.path
    ) {
        // LOGIN
        composable(
            route = Route.Login.path,
            enterTransition = { fadeScaleIn() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { fadeScaleOut() }
        ) {
            LoginScreen(
                onChofer = {
                    nav.navigate(Route.ChoferHome.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onAdmin = {
                    nav.navigate(Route.AdminHome.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                }
            )
        }

        // HOME CHOFER
        composable(
            route = Route.ChoferHome.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            ChoferHomeScreen(
                onOpenRuta = { rutaId ->
                    nav.navigate(Route.RutaDetalle.build(rutaId))
                }
            )
        }

        // RUTA DETALLE
        composable(
            route = Route.RutaDetalle.path,
            arguments = listOf(navArgument("rutaId"){ type = NavType.StringType }),
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            RutaDetalleScreen(
                onOpenOS = { rutaId, puntoId ->
                    nav.navigate(Route.OrdenServicio.build(rutaId, puntoId))
                }
            )
        }

        // ORDEN DE SERVICIO
        composable(
            route = Route.OrdenServicio.path,
            arguments = listOf(
                navArgument("rutaId"){ type = NavType.StringType },
                navArgument("puntoId"){ type = NavType.StringType },
            ),
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { backStack ->
            val rutaId  = backStack.arguments?.getString("rutaId") ?: ""
            val puntoId = backStack.arguments?.getString("puntoId") ?: ""
            OrdenServicioScreen(rutaId = rutaId, puntoId = puntoId)
        }

        // ADMIN
        composable(
            route = Route.AdminHome.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            AdminHomeScreen(onOpenClientes = { nav.navigate(Route.AdminClientes.path) })
        }

        composable(
            route = Route.AdminClientes.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            AdminClientesScreen()
        }

        // RUTAS DEL DÍA (lista)
        composable(
            route = Route.RutasDelDia.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            RutasDelDiaScreen(
                onOpenPunto = { rutaId, puntoId -> nav.navigate(Route.PuntoDetalle.build(rutaId, puntoId)) }
            )
        }

// PUNTO DETALLE (acciones del punto)
        composable(
            route = Route.PuntoDetalle.path,
            arguments = listOf(
                navArgument("rutaId"){ type = NavType.StringType },
                navArgument("puntoId"){ type = NavType.StringType },
            ),
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { backStack ->
            val rutaId = backStack.arguments?.getString("rutaId") ?: ""
            val puntoId = backStack.arguments?.getString("puntoId") ?: ""
            PuntoDetalleScreen(
                rutaId = rutaId,
                puntoId = puntoId,
                onAbrirCaptura = { nav.navigate(Route.CapturaFotos.build(rutaId, puntoId)) }
            )
        }

// CAPTURA FOTOS (cámara/galería)
        composable(
            route = Route.CapturaFotos.path,
            arguments = listOf(
                navArgument("rutaId"){ type = NavType.StringType },
                navArgument("puntoId"){ type = NavType.StringType },
            ),
            enterTransition = { fadeScaleIn() },
            exitTransition = { fadeScaleOut() },
            popEnterTransition = { fadeScaleIn() },
            popExitTransition = { fadeScaleOut() }
        ) { args ->
            CapturaFotosScreen(
                rutaId = args.arguments?.getString("rutaId") ?: "",
                puntoId = args.arguments?.getString("puntoId") ?: "",
                onOk = { nav.popBackStack() }
            )
        }

// HISTORIAL RUTAS (chofer)
        composable(
            route = Route.HistorialRutas.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { HistorialRutasScreen() }

// --- ADMIN extras ---
        composable(Route.AdminUsuarios.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { AdminUsuariosScreen() }

        composable(Route.AdminVehiculos.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { AdminVehiculosScreen() }

        composable(Route.AdminMateriales.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { AdminMaterialesScreen() }

        composable(Route.AdminReportes.path,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { AdminReportesScreen() }

        // agregar aquí las pantallas extra con la misma pauta
    }
}