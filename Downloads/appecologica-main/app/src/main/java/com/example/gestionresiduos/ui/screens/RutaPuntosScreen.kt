package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.PuntoRecoleccion
import com.example.gestionresiduos.viewmodel.RutaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutaPuntosScreen(
    rutaId: String,
    rutaViewModel: RutaViewModel = viewModel(),
    onOpenPunto: (String) -> Unit, // Callback para abrir la Orden de Servicio
    onNavigateBack: () -> Unit
) {
    // Busca la ruta específica en la lista que ya cargó el ViewModel
    // Usamos 'remember' para optimizar y no recalcular en cada recomposición a menos que cambien las claves.
    val ruta by remember(rutaId, rutaViewModel.rutas.collectAsState().value) {
        derivedStateOf {
            rutaViewModel.rutas.value.find { it.id == rutaId }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ruta?.nombre ?: "Detalle de Ruta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        ruta?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Puntos de Recolección", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(it.puntos, key = { punto -> punto.id }) { punto ->
                    PuntoRecoleccionItem(
                        punto = punto,
                        onClick = { onOpenPunto(punto.id) }
                    )
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ruta no encontrada o cargando...")
        }
    }
}

@Composable
private fun PuntoRecoleccionItem(punto: PuntoRecoleccion, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(punto.nombreCliente, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(punto.direccion, style = MaterialTheme.typography.bodyMedium)
            Text(
                "Estado: ${punto.estado}",
                style = MaterialTheme.typography.bodySmall,
                color = when (punto.estado.uppercase()) {
                    "PENDIENTE" -> MaterialTheme.colorScheme.primary
                    "COMPLETADO" -> MaterialTheme.colorScheme.tertiary
                    else -> LocalContentColor.current
                }
            )
        }
    }
}
