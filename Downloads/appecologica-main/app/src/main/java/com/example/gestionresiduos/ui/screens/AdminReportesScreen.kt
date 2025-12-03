package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReportesScreen(
    onNavigateToDetail: (userId: String) -> Unit,
    onNavigateBack: () -> Unit,
    adminViewModel: AdminViewModel = viewModel()
) {
    val ranking: List<Pair<Empleado, Float>> by adminViewModel.rankingEmpleados.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reportes y Ranking") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ranking) { (usuario, porcentaje) ->
                RankingItem(
                    usuario = usuario,
                    porcentaje = porcentaje,
                    onClick = { onNavigateToDetail(usuario.id.toString()) }
                )
            }
        }
    }
}

@Composable
private fun RankingItem(usuario: Empleado, porcentaje: Float, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(usuario.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Rol: ${usuario.rol.name}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "${"%.1f".format(porcentaje)}%",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (porcentaje > 80) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
