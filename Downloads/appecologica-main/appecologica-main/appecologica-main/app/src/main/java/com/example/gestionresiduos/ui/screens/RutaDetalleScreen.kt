package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RutaDetalleScreen(onOpenOS: (String, String) -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ruta Detalle", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        // TODO: LazyColumn con puntos de recolección. Por ahora, ejemplo:
        Button(
            onClick = { onOpenOS("r1", "p1") },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Punto 1 → abrir OS") }
    }
}