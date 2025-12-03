package com.example.gestionresiduos.model

import androidx.compose.ui.graphics.vector.ImageVector


sealed class SettingItem {
    // Ítem simple que solo navega a otra pantalla (ej: "Perfil", "Idioma")
    data class NavigationItem(
        val title: String,
        val icon: ImageVector,
        val route: String
    ) : SettingItem()

    // Ítem que tiene un interruptor (ej: "Activar notificaciones")
    data class SwitchItem(
        val title: String,
        val icon: ImageVector,
        val isChecked: Boolean
    ) : SettingItem()

    // Ítem que muestra el valor actual y abre un diálogo o sub-pantalla (ej: "Tema: Claro")
    data class ValueItem(
        val title: String,
        val icon: ImageVector,
        val value: String
    ) : SettingItem()
}

