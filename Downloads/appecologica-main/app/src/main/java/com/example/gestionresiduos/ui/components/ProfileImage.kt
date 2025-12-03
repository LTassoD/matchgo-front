package com.example.gestionresiduos.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileImage(
    imageUri: Uri?,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    onClick: () -> Unit
) {
    val painter = if (imageUri != null) {
        rememberAsyncImagePainter(model = imageUri)
    } else {
        null
    }

    if (painter != null) {
        Image(
            painter = painter,
            contentDescription = "Foto de perfil",
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { onClick() },
            contentScale = ContentScale.Crop
        )
    } else {
        // Muestra un icono por defecto si no hay imagen
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Tomar foto de perfil",
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { onClick() },
            contentScale = ContentScale.Crop
        )
    }
}
