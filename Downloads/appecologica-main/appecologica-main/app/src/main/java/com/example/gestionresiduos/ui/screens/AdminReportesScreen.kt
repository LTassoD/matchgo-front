package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminReportesScreen() {
    val reportes = remember {
        mutableStateListOf("Cumplimiento rutas", "Materiales recuperados", "Alertas de OS")
    }
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Reportes", style = MaterialTheme.typography.headlineSmall)
        reportes.forEach { reporte ->
            Button(onClick = { /* navegar a detalle */ }, modifier = Modifier.fillMaxWidth()) {
                Text(reporte)
            }
        }
    }
}
