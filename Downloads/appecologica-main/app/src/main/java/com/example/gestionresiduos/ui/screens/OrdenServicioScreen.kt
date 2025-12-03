package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.viewmodel.RutaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdenServicioScreen(
    rutaId: String,
    puntoId: String,
    // La pantalla de la orden de servicio debe usar el mismo ViewModel que gestiona las rutas
    rutaViewModel: RutaViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    // Busca el estado del punto específico dentro del ViewModel
    val punto by remember(rutaId, puntoId, rutaViewModel.rutas.collectAsState().value) {
        derivedStateOf {
            rutaViewModel.rutas.value
                .find { it.id == rutaId }
                ?.puntos?.find { it.id == puntoId }
        }
    }

    var obs by rememberSaveable { mutableStateOf("") }
    val isSaving by rutaViewModel.loading.collectAsState() // Reutilizamos el estado de carga

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(punto?.nombreCliente ?: "Orden de Servicio") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Si el punto no se ha encontrado (aún cargando o error), muestra un mensaje
        if (punto == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Cargando detalles del punto...")
            }
        } else {
            // Contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Orden de Servicio", style = MaterialTheme.typography.headlineMedium)
                Text("Cliente: ${punto!!.nombreCliente}", fontWeight = FontWeight.Bold)
                Text("Dirección: ${punto!!.direccion}")
                Text("Estado Actual: ${punto!!.estado}", color = MaterialTheme.colorScheme.primary)

                Spacer(Modifier.height(16.dp))

                // Campo de texto para observaciones
                OutlinedTextField(
                    value = obs,
                    onValueChange = { obs = it },
                    label = { Text("Observaciones (opcional)") },
                    modifier = Modifier.fillMaxWidth().height(120.dp)
                )

                Spacer(Modifier.weight(1f))

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Botón para INICIAR
                    Button(
                        onClick = { rutaViewModel.iniciarOrden(puntoId) },
                        modifier = Modifier.weight(1f),
                        // Se deshabilita si el estado no es PENDIENTE
                        enabled = punto!!.estado.equals("PENDIENTE", ignoreCase = true) && !isSaving
                    ) {
                        Text("Iniciar")
                    }

                    // Botón para FINALIZAR
                    Button(
                        onClick = { rutaViewModel.finalizarOrden(puntoId, obs) { onNavigateBack() } },
                        modifier = Modifier.weight(1f),
                        // Se deshabilita si el estado es PENDIENTE o si ya está COMPLETADO
                        enabled = !punto!!.estado.equals("PENDIENTE", ignoreCase = true) &&
                                !punto!!.estado.equals("COMPLETADO", ignoreCase = true) && !isSaving
                    ) {
                        Text("Finalizar")
                    }
                }
            }
        }
    }
}
