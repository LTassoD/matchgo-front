package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gestionresiduos.model.SettingItem
import androidx.compose.material.icons.automirrored.filled.Logout

/**
 * Pantalla de Configuración.
 * Ahora, cualquier ítem navega a una pantalla de "En Construcción".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigate: (route: String) -> Unit, // Función única para navegar
    onLogout: () -> Unit
) {
    // Lista de ítems de configuración. No necesitamos los estados 'remember' por ahora.
    val settingItems = listOf(
        SettingItem.NavigationItem("Perfil", Icons.Default.Person, "settings/wip"),
        SettingItem.SwitchItem("Alertas Push", Icons.Default.Notifications, true),
        SettingItem.ValueItem("Tema", Icons.Default.DarkMode, "Claro"),
        SettingItem.ValueItem("Idioma", Icons.Default.Language, "Español"),
        SettingItem.NavigationItem("Privacidad y Seguridad", Icons.Default.Security, "settings/wip"),
        SettingItem.NavigationItem("Datos y Almacenamiento", Icons.Default.Storage, "settings/wip"),
        SettingItem.NavigationItem("Información de la App", Icons.Default.Info, "settings/wip"),
        SettingItem.NavigationItem("Cerrar Sesión", Icons.AutoMirrored.Filled.Logout, "logout")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate("back") }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(settingItems) { item ->
                // Lógica unificada para manejar el clic
                val onItemClick = {
                    when (item) {
                        is SettingItem.NavigationItem -> {
                            if (item.route == "logout") {
                                onLogout()
                            } else {
                                onNavigate(item.route) // Navega a la ruta del ítem (ej. "settings/wip")
                            }
                        }
                        // Para los ítems con interruptor o valor, también navegamos a "en construcción"
                        else -> onNavigate("settings/wip")
                    }
                }

                // Renderiza el tipo de ítem correcto según el modelo
                when (item) {
                    is SettingItem.NavigationItem -> SettingNavigationItem(item = item, onClick = onItemClick)
                    is SettingItem.SwitchItem -> SettingSwitchItem(item = item, onClick = onItemClick)
                    is SettingItem.ValueItem -> SettingValueItem(item = item, onClick = onItemClick)
                }
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

// --- COMPOSABLES DE ÍTEMS MODIFICADOS PARA ACEPTAR UN ÚNICO ONCLICK ---

@Composable
private fun SettingNavigationItem(item: SettingItem.NavigationItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Usa la acción de clic unificada
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = item.icon, contentDescription = item.title, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
    }
}

@Composable
private fun SettingSwitchItem(item: SettingItem.SwitchItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Usa la acción de clic unificada
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = item.icon, contentDescription = item.title, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        // El Switch se vuelve "no interactivo" visualmente, ya que el clic lo maneja la fila.
        Switch(checked = item.isChecked, onCheckedChange = null)
    }
}

@Composable
private fun SettingValueItem(item: SettingItem.ValueItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Usa la acción de clic unificada
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = item.icon, contentDescription = item.title, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = item.value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
    }
}
