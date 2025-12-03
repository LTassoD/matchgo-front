package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.viewmodel.RutaViewModel
import androidx.compose.material.icons.automirrored.filled.ExitToApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoferRutasScreen(
    choferId: Int, // ID del chofer autenticado
    rutaViewModel: RutaViewModel = viewModel(),
    onOpenPunto: (rutaId: String, puntoId: String) -> Unit, // Navega a los detalles de un punto
    onNavigateBack: () -> Unit // Para desloguear y volver
) {
    // Observamos los estados del ViewModel
    val isLoading by rutaViewModel.loading.collectAsState()
    val rutas by rutaViewModel.rutas.collectAsState()
    val error by rutaViewModel.error.collectAsState()

    // Usamos LaunchedEffect para cargar las rutas del chofer específico
    // cuando la pantalla entra en la composición por primera vez.
    LaunchedEffect(key1 = choferId) {
        rutaViewModel.cargarRutasPorChofer(choferId.toString())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Rutas del Día") },
                actions = {
                    // Botón para cerrar sesión
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar Sesión"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading && rutas.isEmpty()) {
                CircularProgressIndicator()
            } else if (error != null) {
                Text(
                    text = "Error al cargar las rutas:\n$error",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error
                )
            } else if (rutas.isEmpty()) {
                Text(
                    text = "No tienes rutas asignadas para hoy.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(rutas, key = { it.id }) { ruta ->
                        // Usamos un Composable para mostrar cada ruta
                        ChoferRutaItem(
                            ruta = ruta,
                            onStartRoute = {
                                // Aquí podrías navegar al primer punto de la ruta, por ejemplo.
                                // Suponiendo que cada ruta tiene una lista de puntos.
                                val primerPuntoId = ruta.puntos.firstOrNull()?.id
                                if (primerPuntoId != null) {
                                    onOpenPunto(ruta.id, primerPuntoId)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable que representa una tarjeta de ruta para el chofer.
 */
@Composable
private fun ChoferRutaItem(
    ruta: Ruta,
    onStartRoute: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onStartRoute() } // Hacemos que toda la tarjeta sea clicable
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = ruta.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Fecha: ${ruta.fecha}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Estado: ${ruta.estado}",
                style = MaterialTheme.typography.bodyMedium,
                color = when (ruta.estado.uppercase()) {
                    "PENDIENTE" -> MaterialTheme.colorScheme.primary
                    "EN PROGRESO" -> MaterialTheme.colorScheme.secondary
                    "COMPLETADA" -> MaterialTheme.colorScheme.tertiary
                    else -> LocalContentColor.current
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onStartRoute,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Ruta")
            }
        }
    }
}
