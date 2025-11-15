package com.example.gestionresiduos.model

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingItem(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector? = null,
    val enabled: Boolean = true
)
