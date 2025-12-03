package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.model.UserRole
import com.example.gestionresiduos.model.Vehiculo
import com.example.gestionresiduos.viewmodel.AdminViewModel
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAsignacionScreen(
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    // Se obtienen los estados desde el ViewModel
    val clientes by adminViewModel.clientes.collectAsState()
    val empleados by adminViewModel.empleados.collectAsState()
    val vehiculos by adminViewModel.vehiculos.collectAsState()
    val isSaving by adminViewModel.isSaving.collectAsState()

    // Filtra para mostrar solo a los choferes en el menú desplegable
    val choferes = remember(empleados) {
        empleados.filter { it.rol == UserRole.CHOFER }
    }

    // Limpia la selección al entrar a la pantalla
    LaunchedEffect(Unit) {
        adminViewModel.resetearSeleccion()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asignar Ruta a Cliente") },
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Selecciona los componentes para la asignación:", style = MaterialTheme.typography.titleMedium)

            // Menú desplegable para Clientes
            DropdownAsignacion(
                label = "Cliente",
                items = clientes,
                itemToString = { it.nombre },
                onItemSelected = { adminViewModel.onClienteSeleccionado(it.idcliente!!) }
            )

            // Menú desplegable para Choferes
            DropdownAsignacion(
                label = "Chofer",
                items = choferes,
                itemToString = { it.nombre },
                onItemSelected = { adminViewModel.onChoferSeleccionado(it.id) }
            )

            // Menú desplegable para Vehículos
            DropdownAsignacion(
                label = "Vehículo",
                items = vehiculos,
                itemToString = { "${it.marca} ${it.modelo} (${it.patente})" },
                onItemSelected = { adminViewModel.onVehiculoSeleccionado(it.id!!) }
            )

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = {
                    adminViewModel.asignarRuta {
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Asignar Ruta")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> DropdownAsignacion(
    label: String,
    items: List<T>,
    itemToString: (T) -> String,
    onItemSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemToString(item)) },
                    onClick = {
                        selectedText = itemToString(item)
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
