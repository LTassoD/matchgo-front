package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionresiduos.repository.RutaRepositoryFake
import java.time.format.DateTimeFormatter

@Composable
fun HistorialRutasScreen() {
    val historial = remember { RutaRepositoryFake().historialDelChofer("chofer-1") }
    val formatter = remember { DateTimeFormatter.ofPattern("dd MMM") }

    Column(Modifier.fillMaxSize()) {
        Text(
            "Historial de rutas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(historial) { registro ->
                Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Column(Modifier.fillMaxWidth().padding(12.dp)) {
                        Text(registro.fecha.format(formatter), style = MaterialTheme.typography.titleMedium)
                        Text(registro.resumen)
                        Text("Puntos completados: ${registro.puntosCompletados}")
                    }
                }
            }
        }
    }
}
