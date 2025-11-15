package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminHomeScreen(onOpenClientes: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Menú Administrador", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onOpenClientes, modifier = Modifier.fillMaxWidth()) { Text("Clientes") }
        // TODO: Agrega más módulos (Usuarios, Vehículos, Materiales) según tu mockup
    }
}