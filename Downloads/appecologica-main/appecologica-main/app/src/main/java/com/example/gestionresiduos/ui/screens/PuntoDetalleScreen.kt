package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionresiduos.model.PuntoRecoleccion
import com.example.gestionresiduos.repository.RutaRepository
import com.example.gestionresiduos.repository.RutaRepositoryFake

@Composable
fun PuntoDetalleScreen(
    rutaId: String,
    puntoId: String,
    onAbrirCaptura: () -> Unit,
    repository: RutaRepository = RutaRepositoryFake()
) {
    var punto by remember { mutableStateOf<PuntoRecoleccion?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(rutaId, puntoId) {
        loading = true
        error = null
        runCatching { repository.rutaPorId(rutaId) }
            .onSuccess { ruta -> punto = ruta.puntos.firstOrNull { it.id == puntoId } }
            .onFailure { error = it.message ?: "No se pudo cargar el punto" }
        loading = false
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Detalle del punto", style = MaterialTheme.typography.headlineSmall)
        if (loading) {
            Text("Cargando información...")
        } else if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
        } else {
            punto?.let { PuntoCard(it) } ?: Text("No encontramos información del punto")
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = onAbrirCaptura, modifier = Modifier.fillMaxWidth()) {
            Text("Registrar evidencia fotográfica")
        }
    }
}

@Composable
private fun PuntoCard(punto: PuntoRecoleccion) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(punto.direccion, style = MaterialTheme.typography.titleMedium)
            Text("Material: ${punto.material}")
            Text("Contenedor: ${punto.contenedor}")
            punto.observaciones?.let { Text("Notas: $it") }
        }
    }
}
