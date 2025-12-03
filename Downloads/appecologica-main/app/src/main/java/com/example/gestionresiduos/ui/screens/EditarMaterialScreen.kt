package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Material
import com.example.gestionresiduos.viewmodel.AdminViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarMaterialScreen(
    materialId: Int?, // ID opcional
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val esModoEdicion = materialId != null
    val materiales by adminViewModel.materiales.collectAsState()

    // Busca el material a editar solo si estamos en modo edición
    val materialAEditar by remember(materialId, materiales) {
        derivedStateOf {
            if (esModoEdicion) {
                materiales.find { it.id == materialId }
            } else {
                null
            }
        }
    }

    // Estados del formulario
    var nombre by remember(materialAEditar) { mutableStateOf(materialAEditar?.nombre ?: "") }
    var descripcion by remember(materialAEditar) { mutableStateOf(materialAEditar?.descripcion ?: "") }

    val isSaving by adminViewModel.isSaving.collectAsState()
    val isFormValid by remember(nombre) { derivedStateOf { nombre.isNotBlank() } }

    val topBarTitle = if (esModoEdicion) "Editar Material" else "Crear Nuevo Material"

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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Material") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val onComplete = { onNavigateBack() }
                    if (esModoEdicion) {
                        val materialActualizado = materialAEditar!!.copy(
                            nombre = nombre,
                            descripcion = descripcion.ifEmpty { null }
                        )
                        // Debes crear 'editarMaterial' en tu ViewModel
                        adminViewModel.editarMaterial(materialActualizado, onComplete)
                    } else {
                        val nuevoMaterial = Material(
                            id = 0,
                            nombre = nombre,
                            descripcion = descripcion.ifEmpty { null }
                        )
                        adminViewModel.crearMaterial(nuevoMaterial, onComplete)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid && !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text(if (esModoEdicion) "Guardar Cambios" else "Guardar Material")
                }
            }
        }
    }
}
