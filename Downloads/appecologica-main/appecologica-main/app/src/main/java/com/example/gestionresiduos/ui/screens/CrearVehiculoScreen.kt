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
fun CrearVehiculoScreen() {
    var patente by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Registrar vehículo", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(patente, { patente = it.uppercase() }, label = { Text("Patente") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(modelo, { modelo = it }, label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            capacidad,
            { capacidad = it.filter(Char::isDigit) },
            label = { Text("Capacidad en Kg") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                mensaje = when {
                    patente.length < 5 -> "Patente inválida"
                    modelo.isBlank() -> "Modelo requerido"
                    capacidad.toIntOrNull() == null -> "Capacidad inválida"
                    else -> "Vehículo $patente listo para usar"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar") }
        mensaje?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
    }
}
