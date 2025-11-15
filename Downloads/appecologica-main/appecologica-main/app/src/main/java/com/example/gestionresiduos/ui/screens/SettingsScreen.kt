package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionresiduos.model.SettingItem

@Composable
fun SettingsScreen() {
    val settings = listOf(
        SettingItem("notifications", "Notificaciones", "Recibe alertas de nuevas rutas"),
        SettingItem("biometric", "Ingreso biométrico", "Habilita FaceID o huella"),
        SettingItem("autoupdate", "Actualizaciones automáticas", "Descarga actualizaciones de datos")
    )
    val toggles = remember { mutableStateMapOf<String, Boolean>() }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Configuración", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(settings) { item ->
                val checked = toggles[item.id] ?: item.enabled
                Card(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(item.title, style = MaterialTheme.typography.titleMedium)
                            Text(item.description, style = MaterialTheme.typography.bodySmall)
                        }
                        Switch(
                            checked = checked,
                            onCheckedChange = { toggles[item.id] = it }
                        )
                    }
                }
            }
        }
    }
}
