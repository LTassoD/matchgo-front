package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrdenServicioScreen(rutaId: String, puntoId: String) {
    var obs by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Orden de Servicio", style = MaterialTheme.typography.headlineSmall)
        Text("Ruta: $rutaId • Punto: $puntoId")
        Spacer(Modifier.height(12.dp))
        Row {
            Button(onClick = { /* vm.iniciarOrden(puntoId) */ }) { Text("Iniciar") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { /* vm.finalizarOrden(puntoId, obs) */ }) { Text("Finalizar") }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            obs, { obs = it },
            label = { Text("Observación") },
            modifier = Modifier.fillMaxWidth()
        )

    }
}
