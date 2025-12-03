package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.viewmodel.RutaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarRutaScreen(
    rutaId: String?, // Nulo para crear, con valor para editar
    rutaViewModel: RutaViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val esModoEdicion = rutaId != null

    // Lógica para buscar la ruta a editar (si aplica)
    val rutaAEditar by remember(rutaId, rutaViewModel.rutas.collectAsState().value) {
        derivedStateOf {
            if (esModoEdicion) {
                rutaViewModel.rutas.value.find { it.id == rutaId }
            } else {
                null
            }
        }
    }

    var nombreRuta by remember(rutaAEditar) { mutableStateOf(rutaAEditar?.nombre ?: "") }
    val isSaving by rutaViewModel.loading.collectAsState() // Reutilizamos el estado de carga

    // Título de la pantalla y del botón
    val topBarTitle = if (esModoEdicion) "Editar Ruta" else "Crear Nueva Ruta"
    val buttonText = if (esModoEdicion) "Guardar Cambios" else "Crear Ruta"

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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo de texto para el nombre de la ruta
            OutlinedTextField(
                value = nombreRuta,
                onValueChange = { nombreRuta = it },
                label = { Text("Nombre de la Ruta") },
                modifier = Modifier.fillMaxWidth(),
                isError = nombreRuta.isBlank(),
                singleLine = true
            )

            // Aquí podrías añadir más campos, como un DatePicker para la 'fecha'.
            // Por ahora, la fecha se generará automáticamente.

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

            // Botón para guardar
            Button(
                onClick = {
                    val onComplete = { onNavigateBack() }

                    // --- CORRECCIÓN DEFINITIVA ---
                    // Se crea una copia local de la propiedad delegada ANTES de la comprobación.
                    val rutaLocal = rutaAEditar

                    if (esModoEdicion && rutaLocal != null) {
                        // Ahora se usa la copia local, que el compilador SÍ puede tratar como no nula.
                        val rutaActualizada = rutaLocal.copy(nombre = nombreRuta)
                        // Llama a la función para actualizar
                        rutaViewModel.actualizarRuta(rutaActualizada, onComplete)
                    } else {
                        val nuevaRuta = Ruta(
                            id = "", // El backend debería generarlo
                            nombre = nombreRuta,
                            fecha = String(), // Fecha actual como ejemplo
                            estado = "PENDIENTE",
                            choferId = String(),
                            puntos = emptyList()

                        )
                        // Llama a la función para crear
                        rutaViewModel.crearRuta(nuevaRuta, onComplete)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombreRuta.isNotBlank() && !isSaving // El botón se deshabilita si el nombre está vacío o si está guardando
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(buttonText)
                }
            }
        }
    }
}
