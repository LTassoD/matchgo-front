package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminHomeScreen(
    onOpenClientes: () -> Unit,
    onOpenUsuarios: () -> Unit = {},
    onOpenVehiculos: () -> Unit = {},
    onOpenMateriales: () -> Unit = {},
    onOpenReportes: () -> Unit = {}
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Menú Administrador", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onOpenClientes, modifier = Modifier.fillMaxWidth()) { Text("Clientes") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onOpenUsuarios, modifier = Modifier.fillMaxWidth()) { Text("Usuarios") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onOpenVehiculos, modifier = Modifier.fillMaxWidth()) { Text("Vehículos") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onOpenMateriales, modifier = Modifier.fillMaxWidth()) { Text("Materiales") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onOpenReportes, modifier = Modifier.fillMaxWidth()) { Text("Reportes") }
    }
}
