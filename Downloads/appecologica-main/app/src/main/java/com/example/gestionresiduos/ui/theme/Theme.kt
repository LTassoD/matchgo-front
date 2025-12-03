package com.example.gestionresiduos.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- PALETA DE COLORES PARA EL TEMA CLARO ---
private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,        // Color principal (botones, FABs, links)
    onPrimary = Color.White,       // Color del texto/íconos sobre el color primario
    primaryContainer = GreenLight, // Un contenedor con un tono más claro del primario
    onPrimaryContainer = GreenDark,  // Texto sobre el primaryContainer

    secondary = YellowDark,
    onSecondary = Color.Black,
    secondaryContainer = YellowLight,
    onSecondaryContainer = Color.Black,

    background = Color(0xFFF7F7F7), // Fondo general de la app
    surface = Color.White,         // Color de las superficies (Cards, Bottom Sheets)


)

// PALETA DE COLORES PARA EL TEMA OSCURO

private val DarkColorScheme = darkColorScheme(
    primary = GreenLight,
    onPrimary = GreenDark,
    primaryContainer = GreenPrimary,
    onPrimaryContainer = Color.White,

    secondary = YellowLight,
    onSecondary = Color.Black,
    secondaryContainer = YellowDark,
    onSecondaryContainer = Color.Black,

    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
)

@Composable
fun GestionResiduosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
