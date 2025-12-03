package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.viewmodel.RutaViewModel
// En ui/screens/AdminRutasScreen.kt

// ... (imports)
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRutasScreen(
    // La pantalla ya no necesita el choferId, lo manejará el ViewModel si es necesario
    onNavigateToEditRuta: (rutaId: String) -> Unit,
    onNavigateToCreateRuta: () -> Unit,
    onNavigateBack: () -> Unit,
    // Se puede usar un ViewModel de Admin o uno específico para rutas
    rutaViewModel: RutaViewModel = viewModel()
) {
    val isLoading by rutaViewModel.loading.collectAsState()
    val rutas by rutaViewModel.rutas.collectAsState()

    LaunchedEffect(Unit) {
        rutaViewModel.cargarTodasLasRutas() // Carga todas las rutas o las de un chofer
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gestión de Rutas") },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateRuta) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Ruta")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(rutas, key = { it.id }) { ruta ->
                        RutaItem( // El Composable RutaItem ahora tendrá más opciones
                            ruta = ruta,
                            onEdit = { onNavigateToEditRuta(ruta.id) },
                            onDelete = { rutaViewModel.eliminarRuta(ruta.id) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable que representa una ruta con opciones de edición y eliminación.
 */
@Composable
fun RutaItem(
    ruta: Ruta,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(ruta.nombre, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                // ... (otros detalles de la ruta)
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Ruta")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Ruta")
            }
        }
    }
}
