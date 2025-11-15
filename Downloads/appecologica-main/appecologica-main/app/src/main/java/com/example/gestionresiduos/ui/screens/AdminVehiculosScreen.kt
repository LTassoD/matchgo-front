package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
fun AdminVehiculosScreen(vm: AdminViewModel = viewModel()) {
    val vehiculos by vm.vehiculos.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Vehículos", style = MaterialTheme.typography.headlineSmall)
        VehiculoForm(onSubmit = vm::crearVehiculo)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(vehiculos) { vehiculo ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("${vehiculo.patente} • ${vehiculo.modelo}", style = MaterialTheme.typography.titleMedium)
                        Text("Capacidad: ${vehiculo.capacidadKg} Kg")
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(if (vehiculo.habilitado) "Habilitado" else "En mantención")
                            Switch(
                                checked = vehiculo.habilitado,
                                onCheckedChange = { vm.actualizarVehiculo(vehiculo.id, it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VehiculoForm(onSubmit: (String, String, Int) -> Unit) {
    var patente by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(patente, { patente = it }, label = { Text("Patente") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(modelo, { modelo = it }, label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            capacidad,
            { capacidad = it.filter(Char::isDigit) },
            label = { Text("Capacidad (Kg)") },
            modifier = Modifier.fillMaxWidth()
        )
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(
            onClick = {
                val capValue = capacidad.toIntOrNull()
                error = when {
                    patente.length < 5 -> "Patente inválida"
                    modelo.isBlank() -> "Modelo requerido"
                    capValue == null || capValue <= 0 -> "Capacidad inválida"
                    else -> null
                }
                if (error == null) {
                    onSubmit(patente, modelo, capValue ?: 0)
                    patente = ""
                    modelo = ""
                    capacidad = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Registrar vehículo") }
    }
}
