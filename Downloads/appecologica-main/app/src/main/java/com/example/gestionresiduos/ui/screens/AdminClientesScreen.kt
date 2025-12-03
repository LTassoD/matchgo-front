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
import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminClientesScreen(
    onNavigateToEditCliente: (clienteId: Int) -> Unit,
    onNavigateToCreateCliente: () -> Unit,
    onNavigateBack: () -> Unit,
    adminViewModel: AdminViewModel = viewModel()
) {
    val clientes by adminViewModel.clientes.collectAsState()
    val isLoading by adminViewModel.isLoading.collectAsState()
    val error by adminViewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

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
                title = { Text("Gestión de Clientes") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateCliente) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Cliente")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading && clientes.isEmpty()) {
                CircularProgressIndicator()
            } else if (clientes.isEmpty()) {
                Text(
                    text = "No hay clientes registrados.\nPresiona '+' para añadir uno.",
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
                        items = clientes,
                        key = { it.idcliente ?: -1 }
                    ) { cliente ->
                        cliente.idcliente?.let { clienteId ->
                            ClienteItem(
                                cliente = cliente,
                                onEdit = {

                                    onNavigateToEditCliente(clienteId)
                                },
                                onDelete = {
                                    adminViewModel.borrarCliente(clienteId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ClienteItem(
    cliente: Cliente,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Usamos 'cliente.nombre' porque es el nombre de la propiedad en el modelo 'Cliente'.
                Text(
                    text = cliente.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("RUT: ${cliente.rut}", style = MaterialTheme.typography.bodySmall)
                Text("Dirección: ${cliente.direccion}", style = MaterialTheme.typography.bodyMedium)
                Text("Material: ${cliente.material} - Frecuencia: ${cliente.frecuencia}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Cliente")
            }
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Cliente")
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar al cliente '${cliente.nombre}'? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
