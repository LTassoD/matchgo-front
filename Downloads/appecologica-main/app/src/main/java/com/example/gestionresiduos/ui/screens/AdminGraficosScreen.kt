package com.example.gestionresiduos.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.automirrored.filled.ArrowBack



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminGraficosScreen(
    onNavigateBack: () -> Unit
) {

    val infiniteTransition = rememberInfiniteTransition(label = "graficos_transition")

    // Animación de tamaño (escala)
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Animación de color
    val color by infiniteTransition.animateColor(
        initialValue = Color.Gray,
        targetValue = MaterialTheme.colorScheme.primary,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color"
    )

    // Animación de rotación
    val rotation by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                -15f at 0
                0f at 750
                15f at 1500
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gráficos y Estadísticas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al panel de administrador"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 4. APLICA LAS ANIMACIONES AL ICONO
            Icon(
                imageVector = Icons.Default.Construction,
                contentDescription = "En construcción",
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        rotationZ = rotation
                    },
                tint = color
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¡Pantalla de Gráficos en Construcción!",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Próximamente habilitado.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
