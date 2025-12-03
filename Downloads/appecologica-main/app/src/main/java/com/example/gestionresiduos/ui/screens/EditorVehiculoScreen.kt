package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Vehiculo
import com.example.gestionresiduos.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorVehiculoRoute(
    vehiculoId: Int?, // Nulo para crear, con valor para editar
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val vehiculos by adminViewModel.vehiculos.collectAsStateWithLifecycle()
    val isSaving by adminViewModel.isSaving.collectAsStateWithLifecycle()
    val vehiculoAEditar = vehiculos.find { it.id == vehiculoId }

    EditorVehiculoScreen(
        vehiculo = vehiculoAEditar,
        isSaving = isSaving,
        onGuardar = { vehiculoParaGuardar ->
            if (vehiculoId != null) {
                adminViewModel.actualizarVehiculo(vehiculoParaGuardar, onComplete = onNavigateBack)
            } else {
                adminViewModel.crearVehiculo(vehiculoParaGuardar, onComplete = onNavigateBack)
            }
        },
        onCancelar = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorVehiculoScreen(
    vehiculo: Vehiculo?,
    isSaving: Boolean,
    onGuardar: (Vehiculo) -> Unit,
    onCancelar: () -> Unit
) {
    val esModoEdicion = vehiculo != null

    // --- ESTADOS DEL FORMULARIO ---
    // Se inicializan con los datos del vehículo a editar si existe
    var patente by remember(vehiculo) { mutableStateOf(vehiculo?.patente ?: "") }
    var marca by remember(vehiculo) { mutableStateOf(vehiculo?.marca ?: "") }
    var modelo by remember(vehiculo) { mutableStateOf(vehiculo?.modelo ?: "") }
    var anio by remember(vehiculo) { mutableStateOf(vehiculo?.anio?.toString() ?: "") }
    var capacidad by remember(vehiculo) { mutableStateOf(vehiculo?.capacidad?.toString() ?: "") }

    val topBarTitle = if (esModoEdicion) "Editar Vehículo" else "Crear Nuevo Vehículo"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle) },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(value = patente, onValueChange = { patente = it }, label = { Text("Patente") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = marca, onValueChange = { marca = it }, label = { Text("Marca") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = anio, onValueChange = { anio = it }, label = { Text("Año") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = capacidad, onValueChange = { capacidad = it }, label = { Text("Capacidad (Kg)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val vehiculoParaGuardar = Vehiculo(
                        id = vehiculo?.id ?: 0, // Usa el ID existente si editamos, o 0 si creamos
                        patente = patente,
                        marca = marca,
                        modelo = modelo,
                        anio = anio.toIntOrNull() ?: 0,
                        capacidad = capacidad.toIntOrNull() ?: 0
                    )

                    onGuardar(vehiculoParaGuardar)
                },
                modifier = Modifier.fillMaxWidth(),
                // Se elimina la dependencia de 'isViewModelInitialized'
                enabled = !isSaving && patente.isNotBlank() && marca.isNotBlank()
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar Vehículo")
                }
            }
        }
    }
}
