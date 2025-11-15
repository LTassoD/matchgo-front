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
fun RutasDelDiaScreen(
    onOpenPunto: (String, String) -> Unit,
    vm: RutaViewModel = viewModel(factory = RutaViewModelFactory.default("chofer-1"))
) {
    val rutasState by vm.rutas.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Rutas del día", style = MaterialTheme.typography.headlineSmall)
        StateContainer(state = rutasState) { rutas ->
            RutaList(rutas = rutas, onOpenPunto = onOpenPunto)
        }
    }
}

@Composable
private fun RutaList(rutas: List<Ruta>, onOpenPunto: (String, String) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
        items(rutas) { ruta ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Ruta ${ruta.id}", style = MaterialTheme.typography.titleMedium)
                    ruta.puntos.forEach { punto ->
                        Button(
                            onClick = { onOpenPunto(ruta.id, punto.id) },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("${punto.direccion} • ${punto.material}") }
                    }
                }
            }
        }
    }
}
