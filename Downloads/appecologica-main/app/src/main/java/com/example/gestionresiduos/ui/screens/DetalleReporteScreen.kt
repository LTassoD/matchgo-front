package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
// CORRECCIÓN: Import para la recolección de estado consciente del ciclo de vida
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.HistorialLabor
import com.example.gestionresiduos.viewmodel.AdminViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleReporteScreen(
    userId: Int, // El ID ya es un Int, lo cual es correcto.
    onNavigateBack: () -> Unit,
    adminViewModel: AdminViewModel = viewModel()
) {
    // CORRECCIÓN: Usar collectAsStateWithLifecycle para una mejor gestión del ciclo de vida.
    val historial by adminViewModel.historialEmpleado.collectAsStateWithLifecycle()
    val isLoading by adminViewModel.isLoading.collectAsStateWithLifecycle()
    val isSaving by adminViewModel.isSaving.collectAsStateWithLifecycle()

    var comentario by rememberSaveable { mutableStateOf("") }

    // CORRECCIÓN: Se elimina el LaunchedEffect anidado e innecesario.
    LaunchedEffect(key1 = userId) {
        adminViewModel.loadHistorialParaEmpleado(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Labores") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (isLoading && historial.isEmpty()) { // Muestra loading solo si la lista está vacía
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(historial) { labor ->
                        HistorialItem(labor = labor)
                    }
                }
            }

            // Sección para enviar comentario
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = comentario,
                    onValueChange = { comentario = it },
                    label = { Text("Añadir comentario o reseña...") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        // CORRECCIÓN: 'userId' ya es un Int, se puede usar directamente.
                        // No se necesita '.toIntOrNull()' ni 'let'.
                        adminViewModel.enviarComentario(userId, comentario) {
                            comentario = "" // Limpia el campo después de enviar
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving && comentario.isNotBlank()
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Enviar Comentario")
                    }
                }
            }
        }
    }
}

@Composable
private fun HistorialItem(labor: HistorialLabor) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Ruta ID: ${labor.rutaId} - ${dateFormat.format(labor.fecha)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Puntos Completados: ${labor.puntosCompletados}", style = MaterialTheme.typography.bodyMedium)
            labor.observacion?.let {
                if(it.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Observación: $it", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
