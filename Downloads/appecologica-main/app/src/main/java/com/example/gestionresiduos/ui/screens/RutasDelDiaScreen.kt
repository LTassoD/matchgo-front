package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.viewmodel.RutaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutasDelDiaScreen(
    choferId: Int,
    rutaViewModel: RutaViewModel = viewModel(),
    onOpenRuta: (rutaId: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    // 1. Estados que la UI observa del ViewModel
    val isLoading by rutaViewModel.loading.collectAsStateWithLifecycle()
    val rutas by rutaViewModel.rutas.collectAsStateWithLifecycle()
    val error by rutaViewModel.error.collectAsStateWithLifecycle()

    // 2. Se crea un estado para el Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // 3. Este LaunchedEffect se ejecuta cada vez que el estado 'error' cambia.
    LaunchedEffect(error) {
        // Si el error no es nulo...
        error?.let { errorMessage ->
            // ...muestra el Snackbar.
            val result = snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = "Cerrar"
            )
            // 4. Cuando el Snackbar se cierra (por acción del usuario o por tiempo)...
            if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                // ...llama a la función en el ViewModel para limpiar el error.
                rutaViewModel.errorMostrado()
            }
        }
    }

    // 5. Carga inicial de datos
    LaunchedEffect(key1 = choferId) {
        rutaViewModel.cargarRutasPorChofer(choferId.toString())
    }

    // 6. Se añade el SnackbarHost al Scaffold
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mis Rutas del Día") },
                actions = {
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
            // La lógica de la UI para Carga, Error y Datos ya está bien.
            if (isLoading && rutas.isEmpty()) {
                CircularProgressIndicator()
            } else if (rutas.isEmpty() && error == null) { // Solo muestra este mensaje si no hay un error
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
                        RutaItemCard(
                            ruta = ruta,
                            onItemClick = {
                                onOpenRuta(ruta.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RutaItemCard(ruta: Ruta, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                text = "${ruta.puntos.size} puntos de recolección",
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
        }
    }
}
