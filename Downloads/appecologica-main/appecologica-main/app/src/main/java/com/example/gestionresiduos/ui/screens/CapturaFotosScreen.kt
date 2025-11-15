package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CapturaFotosScreen(
    rutaId: String,
    puntoId: String,
    onOk: () -> Unit
) {
    val imagenes = remember { mutableStateListOf<String>() }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Captura de fotos", style = MaterialTheme.typography.headlineSmall)
        Text("Ruta $rutaId • Punto $puntoId", style = MaterialTheme.typography.bodyMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { imagenes.add("Foto ${imagenes.size + 1}") },
                modifier = Modifier.weight(1f)
            ) { Text("Agregar foto") }
            Button(
                onClick = {
                    if (imagenes.isNotEmpty()) imagenes.removeLast()
                },
                modifier = Modifier.weight(1f),
                enabled = imagenes.isNotEmpty()
            ) { Text("Eliminar última") }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f, fill = true)
        ) {
            items(imagenes) { foto ->
                Card(Modifier.fillMaxWidth()) {
                    Text(
                        foto,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = onOk,
            enabled = imagenes.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar evidencia") }
    }
}
