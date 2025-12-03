package com.example.gestionresiduos.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HistorialRutasScreen() {
    // You can build the UI for the route history here.
    Text(text = "Historial de Rutas")
}

@Preview(showBackground = true)
@Composable
fun HistorialRutasScreenPreview() {
    HistorialRutasScreen()
}

