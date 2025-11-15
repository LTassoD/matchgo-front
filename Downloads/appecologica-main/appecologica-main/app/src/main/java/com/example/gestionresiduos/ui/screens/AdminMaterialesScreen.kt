package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.viewmodel.AdminViewModel

@Composable
fun AdminMaterialesScreen(vm: AdminViewModel = viewModel()) {
    val materiales by vm.materiales.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Materiales", style = MaterialTheme.typography.headlineSmall)
        MaterialForm(onSubmit = vm::crearMaterial)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(materiales) { material ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(material.nombre, style = MaterialTheme.typography.titleMedium)
                        Text(material.descripcion)
                        Text("Unidad: ${material.unidadMedida} • Precio ref: ${material.precioReferencia} CLP/kg")
                    }
                }
            }
        }
    }
}

@Composable
private fun MaterialForm(onSubmit: (String, String, String, Double) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = unidad, onValueChange = { unidad = it }, label = { Text("Unidad (Kg, Ton)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it.filter { char -> char.isDigit() || char == '.' || char == ',' } },
            label = { Text("Precio referencia") },
            modifier = Modifier.fillMaxWidth()
        )
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(
            onClick = {
                val price = precio.replace(",", ".").toDoubleOrNull()
                error = when {
                    nombre.isBlank() -> "Ingresa un nombre"
                    descripcion.isBlank() -> "Ingresa una descripción"
                    unidad.isBlank() -> "Ingresa una unidad"
                    price == null -> "Precio inválido"
                    else -> null
                }
                if (error == null) {
                    onSubmit(nombre.trim(), descripcion.trim(), unidad.trim(), price ?: 0.0)
                    nombre = ""
                    descripcion = ""
                    unidad = ""
                    precio = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar material") }
    }
}
