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
fun CrearMaterialScreen() {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Crear material", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(unidad, { unidad = it }, label = { Text("Unidad (Kg/Ton)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            precio,
            { precio = it.filter { ch -> ch.isDigit() || ch == '.' } },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(
            onClick = {
                val price = precio.toDoubleOrNull()
                error = when {
                    nombre.isBlank() -> "Nombre obligatorio"
                    descripcion.length < 5 -> "Descripción muy corta"
                    unidad.isBlank() -> "Define una unidad"
                    price == null -> "Precio inválido"
                    else -> null
                }
                if (error == null) {
                    nombre = ""
                    descripcion = ""
                    unidad = ""
                    precio = ""
                    error = "Material guardado"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar material") }
    }
}
