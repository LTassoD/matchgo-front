package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChoferHomeScreen(onOpenRuta: (String) -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Menú Chofer", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        // TODO: Cards / botones grandes según tu mockup (Rutas del día, Historial, Perfil, etc.)
        Button(
            onClick = { onOpenRuta("r1") },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Ver rutas de hoy") }
    }
}