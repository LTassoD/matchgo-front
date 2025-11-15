package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.ui.components.StateContainer
import com.example.gestionresiduos.viewmodel.AdminViewModel

@Composable
fun AdminClientesScreen(vm: AdminViewModel = viewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Clientes", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        ClienteForm(
            onSubmit = { nombre, direccion ->
                vm.crearCliente(nombre, direccion)
            }
        )

        Spacer(Modifier.height(16.dp))

        StateContainer(state = state) { clientes ->
            ClienteList(clientes = clientes, onRefresh = { vm.cargarClientes(true) })
        }
    }
}

@Composable
private fun ClienteForm(onSubmit: (String, String) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var nombreError by remember { mutableStateOf<String?>(null) }
    var direccionError by remember { mutableStateOf<String?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                nombreError = null
            },
            label = { Text("Nombre") },
            isError = nombreError != null,
            supportingText = { nombreError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = direccion,
            onValueChange = {
                direccion = it
                direccionError = null
            },
            label = { Text("Dirección") },
            isError = direccionError != null,
            supportingText = { direccionError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                nombreError = if (nombre.isBlank()) "Ingresa un nombre" else null
                direccionError = if (direccion.isBlank()) "Ingresa una dirección" else null
                if (nombreError == null && direccionError == null) {
                    onSubmit(nombre.trim(), direccion.trim())
                    nombre = ""
                    direccion = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar cliente") }
    }
}

@Composable
private fun ClienteList(clientes: List<Cliente>, onRefresh: () -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Button(onClick = onRefresh, modifier = Modifier.fillMaxWidth()) { Text("Actualizar desde backend") }
        }
        items(clientes) { cliente ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(12.dp)) {
                    Text(cliente.nombre, style = MaterialTheme.typography.titleMedium)
                    Text(cliente.direccion, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
