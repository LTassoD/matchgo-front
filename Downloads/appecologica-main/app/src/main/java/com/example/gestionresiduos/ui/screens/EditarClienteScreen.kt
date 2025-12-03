package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarClienteScreen(
    clienteId: String?, // Nulo para crear, con valor para editar
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val esModoEdicion = clienteId != null
    val clientes by adminViewModel.clientes.collectAsState()

    // Busca el cliente a editar solo si estamos en modo edición
    val clienteAEditar by remember(clienteId, clientes) {
        derivedStateOf {
            if (esModoEdicion) {
                // Comparamos el ID del cliente (Int) con el clienteId de la ruta (String)
                clientes.find { it.idcliente.toString() == clienteId }
            } else {
                null
            }
        }
    }

    // --- Estados para los campos del formulario ---
    var nombre by remember(clienteAEditar) { mutableStateOf(clienteAEditar?.nombre ?: "") }
    var rut by remember(clienteAEditar) { mutableStateOf(clienteAEditar?.rut ?: "") }
    var direccion by remember(clienteAEditar) { mutableStateOf(clienteAEditar?.direccion ?: "") }
    var telefono by remember(clienteAEditar) { mutableStateOf(clienteAEditar?.telefono ?: "") }
    var correo by remember(clienteAEditar) { mutableStateOf(clienteAEditar?.correo ?: "") }
    var material by remember(clienteAEditar) { mutableStateOf(clienteAEditar?.material ?: "") }
    var frecuencia by remember(clienteAEditar) { mutableStateOf(clienteAEditar?.frecuencia ?: "") }

    val isSaving by adminViewModel.isLoading.collectAsState()
    val topBarTitle = if (esModoEdicion) "Editar Cliente" else "Crear Nuevo Cliente"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Para que el formulario sea deslizable
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // --- Campos del Formulario ---
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Empresa") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = rut, onValueChange = { rut = it }, label = { Text("RUT") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())

            // --- CORRECCIÓN DEFINITIVA ---
            // Se separan los dos OutlinedTextField que estaban mezclados.
            OutlinedTextField(
                value = material,
                onValueChange = { material = it },
                label = { Text("Material") }, // Label corregido
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = frecuencia,
                onValueChange = { frecuencia = it },
                label = { Text("Frecuencia") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Botón de Guardar ---
            Button(
                onClick = {
                    val onComplete = { onNavigateBack() }
                    val clienteParaGuardar = Cliente(
                        idcliente = clienteAEditar?.idcliente, // Mantiene el ID si estamos editando
                        nombre = nombre,
                        rut = rut,
                        direccion = direccion,
                        telefono = telefono,
                        correo = correo,
                        material = material,
                        frecuencia = frecuencia,
                        fotoUri = clienteAEditar?.fotoUri // Mantiene la foto si existe
                    )

                    if (esModoEdicion) {
                        adminViewModel.actualizarCliente(clienteParaGuardar, onComplete)
                    } else {
                        adminViewModel.crearCliente(clienteParaGuardar, onComplete)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving && nombre.isNotBlank() && rut.isNotBlank()
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}
