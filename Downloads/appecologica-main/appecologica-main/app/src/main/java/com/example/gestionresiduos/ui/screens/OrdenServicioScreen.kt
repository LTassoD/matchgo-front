package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.ui.components.StateContainer
import com.example.gestionresiduos.viewmodel.RutaViewModel
import com.example.gestionresiduos.viewmodel.RutaViewModelFactory

@Composable
fun OrdenServicioScreen(
    rutaId: String,
    puntoId: String,
    vm: RutaViewModel = viewModel(factory = RutaViewModelFactory.default("chofer-1"))
) {
    var obs by remember { mutableStateOf("") }
    var obsError by remember { mutableStateOf<String?>(null) }
    val ordenState by vm.orden.collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()

    LaunchedEffect(rutaId, puntoId) {
        vm.cargarOrden(rutaId, puntoId)
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Orden de Servicio", style = MaterialTheme.typography.headlineSmall)
        Text("Ruta: $rutaId • Punto: $puntoId")
        Spacer(Modifier.height(12.dp))

        StateContainer(state = ordenState) { orden ->
            Text("Estado actual: ${orden.estado}")
        }

        Spacer(Modifier.height(12.dp))
        Row {
            Button(onClick = { vm.iniciarOrden(puntoId) }) { Text("Iniciar") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                obsError = if (obs.length < 5) "Describe la atención (mínimo 5 caracteres)" else null
                if (obsError == null) vm.finalizarOrden(puntoId, obs)
            }) { Text("Finalizar") }
        }

        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = obs,
            onValueChange = {
                obs = it
                obsError = null
            },
            isError = obsError != null,
            supportingText = { obsError?.let { Text(it) } },
            label = { Text("Observación") },
            modifier = Modifier.fillMaxWidth()
        )

        mensaje?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.primary)
        }
    }
}
