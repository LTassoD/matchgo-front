@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
package com.example.gestionresiduos.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.gestionresiduos.util.ComposeFileProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


private const val CAMERA_PERMISSION = "android.permission.CAMERA"

@Composable
fun CapturaFotosScreen(
    rutaId: String,
    puntoId: String,
    onOk: () -> Unit
) {
    val context = LocalContext.current
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }
    var showPermissionDialog by remember { mutableStateOf(false) }


    val cameraPermissionState = rememberPermissionState(CAMERA_PERMISSION)


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                tempUri?.let { uri ->
                    imageUris = imageUris + uri
                }
            }
            tempUri = null
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { imageUris = imageUris + it }
        }
    )

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Permiso Requerido") },
            text = { Text("Para tomar fotos con la cámara, es necesario que concedas el permiso. Por favor, habilítalo en la configuración de la aplicación.") },
            confirmButton = {
                Button(onClick = {
                    showPermissionDialog = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Ir a Configuración")
                }
            },
            dismissButton = {
                Button(onClick = { showPermissionDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Capturar Evidencia") },
                navigationIcon = {
                    IconButton(onClick = onOk) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Ruta: $rutaId - Punto: $puntoId", style = MaterialTheme.typography.titleMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    when {
                        cameraPermissionState.status.isGranted -> {

                            val newUri = ComposeFileProvider.getImageUri(context)


                            tempUri = newUri


                            cameraLauncher.launch(newUri)
                        }
                        cameraPermissionState.status.shouldShowRationale -> {
                            showPermissionDialog = true
                        }
                        else -> {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    }
                }) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Cámara", modifier = Modifier.size(ButtonDefaults.IconSize))
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Cámara")
                }
                Button(onClick = { galleryLauncher.launch("image/*") }) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = "Galería", modifier = Modifier.size(ButtonDefaults.IconSize))
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Galería")
                }
            }

            if (imageUris.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(imageUris) { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Imagen capturada",
                            modifier = Modifier
                                .size(120.dp)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            } else {
                Text("No hay imágenes capturadas.")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    println("Imágenes a subir: $imageUris")
                    onOk()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = imageUris.isNotEmpty()
            ) {
                Text("Finalizar y Guardar")
            }
        }
    }
}
