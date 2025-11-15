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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Ruta
import com.example.gestionresiduos.ui.components.StateContainer
import com.example.gestionresiduos.viewmodel.RutaViewModel
import com.example.gestionresiduos.viewmodel.RutaViewModelFactory

@Composable
fun RutaDetalleScreen(
    rutaId: String,
    onOpenOS: (String, String) -> Unit,
    vm: RutaViewModel = viewModel(factory = RutaViewModelFactory.default("chofer-1"))
) {
    val rutaState by vm.rutaDetalle.collectAsStateWithLifecycle()

    LaunchedEffect(rutaId) { vm.cargarRuta(rutaId) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Detalle ruta $rutaId", style = MaterialTheme.typography.headlineSmall)
        StateContainer(state = rutaState) { ruta ->
            PuntosList(ruta = ruta, onOpenOS = onOpenOS)
        }
    }
}

@Composable
private fun PuntosList(ruta: Ruta, onOpenOS: (String, String) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
        items(ruta.puntos) { punto ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(punto.direccion, style = MaterialTheme.typography.titleMedium)
                    Text("Material: ${punto.material}")
                    Button(onClick = { onOpenOS(ruta.id, punto.id) }) {
                        Text("Atender punto")
                    }
                }
            }
        }
    }
}
