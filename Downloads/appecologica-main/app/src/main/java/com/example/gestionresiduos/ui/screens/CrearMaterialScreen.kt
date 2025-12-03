// DESPUÉS (Código corregido para CrearMaterialScreen.kt)
package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionresiduos.model.Material

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearMaterialScreen(
    onNavigateBack: () -> Unit,
    onSaveClick: (Material) -> Unit, // <-- CAMBIO 1: Callback para guardar
    isSaving: Boolean                 // <-- CAMBIO 2: Estado de guardado externo
) {
    // --- ESTADOS DEL FORMULARIO ---
    var nombre by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Nuevo Material") },
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
                    val nuevoMaterial = Material(
                        id = 0, // El ID será generado por el backend
                        nombre = nombre,
                        descripcion = descripcion.takeIf { it.isNotBlank() }
                    )
                    // --- CAMBIO 3: Llamar al callback en lugar de al ViewModel directamente ---
                    onSaveClick(nuevoMaterial)
                },
                modifier = Modifier.fillMaxWidth(),
                // El botón se habilita si el nombre no está vacío y no se está guardando.
                enabled = nombre.isNotBlank() && !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar Material")
                }
            }
        }
    }
}
