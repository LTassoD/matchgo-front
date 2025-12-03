package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Logout

// Modelo de datos para cada ítem del menú
private data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    navController: NavController,
    onOpenClientes: () -> Unit,
    onOpenRutas: () -> Unit,
    onOpenEmpleado: () -> Unit,
    onOpenVehiculos: () -> Unit,
    onOpenMateriales: () -> Unit,
    onOpenReportes: () -> Unit,
    onOpenAsignaciones: () -> Unit,
    onOpenGraficos: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit
) {
    // CORRECCIÓN: La acción de "Rutas" ahora es un ítem principal del menú.
    val menuItems = listOf(
        MenuItem("Clientes", Icons.Default.People, onOpenClientes),
        MenuItem("Rutas", Icons.Default.Route, onOpenRutas),
        MenuItem("Empleados", Icons.Default.AdminPanelSettings, onOpenEmpleado),
        MenuItem("Vehículos", Icons.Default.LocalShipping, onOpenVehiculos),
        MenuItem("Asignar", Icons.AutoMirrored.Filled.Assignment, onOpenAsignaciones),
        MenuItem("Reportes", Icons.Default.Assessment, onOpenReportes),
        MenuItem("Materiales", Icons.Default.Category, onOpenMateriales),
        MenuItem("Gráficos", Icons.Default.PieChart, onOpenGraficos)
    )

    var menuVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administrador") },
                // CORRECCIÓN: La sección de acciones ahora está limpia y ordenada.
                actions = {
                    // 1. Botón de Configuración
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configuración"
                        )
                    }
                    // 2. Botón para abrir el menú de más opciones
                    IconButton(onClick = { menuVisible = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }
                    // 3. El Menú Desplegable que se abre con el botón anterior
                    DropdownMenu(
                        expanded = menuVisible,
                        onDismissRequest = { menuVisible = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesión") },
                            onClick = {
                                menuVisible = false // Cierra el menú primero
                                onLogout()          // Luego ejecuta la acción
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Logout,
                                    contentDescription = "Cerrar Sesión"
                                )
                            }
                        )
                        // Aquí podrías añadir más opciones al menú si lo necesitas
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Muestra 2 columnas
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(menuItems) { item ->
                MenuCard(item = item)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuCard(item: MenuItem) {
    Card(
        onClick = item.onClick,
        modifier = Modifier.aspectRatio(1f), // Tarjeta cuadrada
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
