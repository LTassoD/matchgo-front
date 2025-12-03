package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Vehiculo
import com.example.gestionresiduos.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminVehiculosScreen(
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateToCreateVehiculo: () -> Unit,
    onNavigateToEditVehiculo: (vehiculoId: Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    val vehiculos by adminViewModel.vehiculos.collectAsState()
    val isLoading by adminViewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Vehículos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            // El botón "+" llama a la navegación para crear
            FloatingActionButton(onClick = onNavigateToCreateVehiculo) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Vehículo")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading && vehiculos.isEmpty()) {
                CircularProgressIndicator()
            } else if (vehiculos.isEmpty()) {
                Text("No hay vehículos registrados.", textAlign = TextAlign.Center)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = vehiculos, key = { it.id!! }) { vehiculo ->
                        VehiculoItem(
                            vehiculo = vehiculo,
                            onEdit = {
                                // La acción de editar llama a la navegación con el ID
                                onNavigateToEditVehiculo(vehiculo.id!!)
                            },
                            onDelete = {
                                // La acción de eliminar llama al ViewModel
                                // Deberás crear la función 'borrarVehiculo' en tu AdminViewModel
                                // adminViewModel.borrarVehiculo(vehiculo.id!!)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VehiculoItem(
    vehiculo: Vehiculo,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onEdit() }) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${vehiculo.marca} ${vehiculo.modelo} (${vehiculo.anio})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("Patente: ${vehiculo.patente}", style = MaterialTheme.typography.bodyMedium)
                Text("Capacidad: ${vehiculo.capacidad} Kg", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Vehículo")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Vehículo")
            }
        }
    }
}
