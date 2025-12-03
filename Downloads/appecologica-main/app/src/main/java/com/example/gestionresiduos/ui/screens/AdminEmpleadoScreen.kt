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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEmpleadoScreen(
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateToCreateEmployee: () -> Unit,
    onNavigateToEditEmployee: (empleadoId: Int) -> Unit,
    onOpenEmployeeDetail: (empleadoId: Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    val empleados by adminViewModel.empleados.collectAsState()
    val isLoading by adminViewModel.isLoading.collectAsState()
    val error by adminViewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var empleadoParaEliminar by remember { mutableStateOf<Empleado?>(null) }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            adminViewModel.errorMostrado()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Empleados") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateEmployee) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Empleado")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading && empleados.isEmpty()) {
                CircularProgressIndicator()
            } else if (empleados.isEmpty()) {
                Text(
                    text = "No hay empleados registrados.\nPresiona '+' para añadir uno.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = empleados,
                        key = { it.id }
                    ) { empleado ->
                        EmpleadoItem(
                            empleado = empleado,
                            onEdit = { onNavigateToEditEmployee(empleado.id) },
                            onDelete = { empleadoParaEliminar = empleado }, // Abre el diálogo de confirmación
                            onOpenDetail = { onOpenEmployeeDetail(empleado.id) } // Llama a la navegación de detalle
                        )
                    }
                }
            }
        }

        // Diálogo de confirmación para eliminar
        empleadoParaEliminar?.let { empleado ->
            AlertDialog(
                onDismissRequest = { empleadoParaEliminar = null },
                title = { Text("Confirmar Eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar al empleado '${empleado.nombre}'?") },
                confirmButton = {
                    Button(
                        onClick = {
                            adminViewModel.borrarEmpleado(empleado.id)
                            empleadoParaEliminar = null // Cierra el diálogo
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { empleadoParaEliminar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}


@Composable
private fun EmpleadoItem(
    empleado: Empleado,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onOpenDetail: () -> Unit
) {
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenDetail() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(empleado.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("RUT: ${empleado.rut}", style = MaterialTheme.typography.bodyMedium)
                Text("Rol: ${empleado.rol.name}", style = MaterialTheme.typography.bodyMedium)
            }
            // Los botones de acción permanecen a la derecha
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Empleado")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar Empleado")
                }
            }
        }
    }
}
