package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminAsignacionScreen() {
    var chofer by remember { mutableStateOf("") }
    var ruta by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }
    var choferError by remember { mutableStateOf<String?>(null) }
    var rutaError by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Asignaciones", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = chofer,
            onValueChange = {
                chofer = it
                mensaje = null
                choferError = null
            },
            isError = choferError != null,
            supportingText = { choferError?.let { Text(it) } },
            label = { Text("ID Chofer") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = ruta,
            onValueChange = {
                ruta = it
                mensaje = null
                rutaError = null
            },
            isError = rutaError != null,
            supportingText = { rutaError?.let { Text(it) } },
            label = { Text("ID Ruta") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                choferError = if (chofer.isBlank()) "Ingresa un chofer" else null
                rutaError = if (ruta.isBlank()) "Ingresa una ruta" else null
                if (choferError == null && rutaError == null) {
                    mensaje = "Ruta $ruta asignada a $chofer"
                    chofer = ""
                    ruta = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Asignar") }

        mensaje?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
    }
}
