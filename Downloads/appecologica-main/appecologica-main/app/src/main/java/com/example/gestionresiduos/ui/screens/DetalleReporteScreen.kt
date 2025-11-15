package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetalleReporteScreen(reporteId: String = "RPT-001") {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Detalle reporte $reporteId", style = MaterialTheme.typography.headlineSmall)
        Text("Aquí podrás mostrar gráficos y tablas específicas del reporte seleccionado.")
    }
}
