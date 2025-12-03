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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
// Se eliminan los imports de getValue/setValue que no son necesarios
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Material
import com.example.gestionresiduos.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMaterialesScreen(
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateToCreateMaterial: () -> Unit,
    onNavigateToEditMaterial: (materialId: Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    val materiales by adminViewModel.materiales.collectAsState()
    val isLoading by adminViewModel.isLoading.collectAsState()

    // --- CORRECCIÓN: Se usa un único estado para controlar el diálogo ---
    // Si 'materialAEliminar' es null, el diálogo está oculto.
    // Si tiene un objeto Material, el diálogo se muestra con los datos de ese material.
    var materialAEliminar by remember { mutableStateOf<Material?>(null) }
    val cerrarDialogo: () -> Unit = { materialAEliminar = null }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Materiales") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateMaterial) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Material")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading && materiales.isEmpty()) {
                CircularProgressIndicator()
            } else if (materiales.isEmpty()) {
                Text("No hay materiales registrados.", textAlign = TextAlign.Center)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = materiales, key = { it.id }) { material ->
                        MaterialItem(
                            material = material,
                            onEdit = { onNavigateToEditMaterial(material.id) },
                            // --- CORRECCIÓN: La acción de borrado ahora solo asigna el material ---
                            onDelete = {
                                materialAEliminar = material // Esto hará que el diálogo aparezca
                            }
                        )
                    }
                }
            }
        }

        // --- CORRECCIÓN: El diálogo ahora se muestra si 'materialAEliminar' no es null ---
        materialAEliminar?.let { material ->
            AlertDialog(
                // Al tocar fuera, se resetea el estado para ocultar el diálogo
                onDismissRequest = cerrarDialogo,
                title = { Text("Confirmar Eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar el material '${material.nombre}'?") },
                confirmButton = {
                    Button(
                        onClick = {
                            adminViewModel.borrarMaterial(material.id)
                            cerrarDialogo() // Oculta el diálogo después de borrar
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = cerrarDialogo) { // Oculta el diálogo
                        Text("Cancelar")
                    }
                }
            )
        }
    } // <-- Cierre del Scaffold
} // <-- Cierre de AdminMaterialesScreen

@Composable
private fun MaterialItem(
    material: Material,
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
                    text = material.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                material.descripcion?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                }
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Material")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Material")
            }
        }
    }
}
