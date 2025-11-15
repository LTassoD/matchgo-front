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
fun CrearEmpleadoScreen() {
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Nuevo empleado", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = rut, onValueChange = { rut = it }, label = { Text("RUT") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = rol, onValueChange = { rol = it }, label = { Text("Rol (Chofer/Admin)") }, modifier = Modifier.fillMaxWidth())
        Button(
            onClick = {
                mensaje = when {
                    nombre.isBlank() -> "El nombre es obligatorio"
                    !rut.contains("-") -> "Formato de RUT inválido"
                    rol.isBlank() -> "Indica un rol"
                    else -> "Empleado $nombre creado"
                }
                if (mensaje?.startsWith("Empleado") == true) {
                    nombre = ""
                    rut = ""
                    rol = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar") }
        mensaje?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
    }
}
