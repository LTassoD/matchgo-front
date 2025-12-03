package com.example.gestionresiduos.ui.screens

// --- CORRECCIÓN: Se añaden los imports necesarios para la imagen ---
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.gestionresiduos.R // Import para acceder a los recursos (drawable)
import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEmpleadoScreen(
    empleadoId: Int,
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val empleados by adminViewModel.empleados.collectAsState()

    // Busca el empleado específico usando el ID que recibimos
    val empleado by remember(empleadoId, empleados) {
        derivedStateOf {
            empleados.find { it.id == empleadoId }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Empleado") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        empleado?.let { emp ->
            // Se llama al Composable de contenido que ahora sí tiene la foto
            DetalleEmpleadoContent(emp, Modifier.padding(paddingValues))
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Empleado no encontrado.")
            }
        }
    }
}

@Composable
private fun DetalleEmpleadoContent(empleado: Empleado, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        // Se centra horizontalmente para que la foto quede centrada
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- CORRECCIÓN: Se añade la sección para mostrar la foto ---
        AsyncImage(
            model = empleado.fotoUri,
            contentDescription = "Foto de ${empleado.nombre}",
            // Un placeholder que se muestra si el empleado no tiene foto o mientras carga
            placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
            // Una imagen que se muestra si la URL es inválida o hay un error de red
            error = painterResource(id = R.drawable.ic_profile_placeholder),
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape), // Recorta la imagen en forma de círculo
            contentScale = ContentScale.Crop // Asegura que la imagen llene el círculo
        )

        // --- SECCIÓN DE DATOS ---
        Text(
            text = empleado.nombre,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoCard(label = "RUT", value = empleado.rut)
        InfoCard(label = "Rol", value = empleado.rol.name)
        InfoCard(label = "Teléfono", value = empleado.telefono)
        InfoCard(label = "Correo", value = empleado.correo)
    }
}

@Composable
private fun InfoCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
