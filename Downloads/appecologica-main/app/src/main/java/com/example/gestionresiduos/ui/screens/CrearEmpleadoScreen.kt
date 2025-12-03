package com.example.gestionresiduos.ui.screens

// Imports necesarios y limpios
import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.gestionresiduos.R
import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.model.UserRole
import com.example.gestionresiduos.util.createImageUri
import com.example.gestionresiduos.viewmodel.AdminViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

// Se usa @OptIn una sola vez a nivel de función
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CrearEmpleadoScreen(
    empleadoId: String?,
    adminViewModel: AdminViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    // ===================================================================
    //         1. LÓGICA Y ESTADOS
    // ===================================================================

    // --- Lógica de Edición ---
    val esModoEdicion = empleadoId != null
    val empleados by adminViewModel.empleados.collectAsState()
    val empleadoAEditar by remember(empleadoId, empleados) {
        derivedStateOf {
            if (esModoEdicion) {
                empleados.find { it.id.toString() == empleadoId }
            } else {
                null
            }
        }
    }

    // --- Estados del Formulario ---
    var nombre by remember(empleadoAEditar) { mutableStateOf(empleadoAEditar?.nombre ?: "") }
    var rut by remember(empleadoAEditar) { mutableStateOf(empleadoAEditar?.rut ?: "") }
    var telefono by remember(empleadoAEditar) { mutableStateOf(empleadoAEditar?.telefono ?: "") }
    var correo by remember(empleadoAEditar) { mutableStateOf(empleadoAEditar?.correo ?: "") }
    var rol by remember(empleadoAEditar) { mutableStateOf(empleadoAEditar?.rol ?: UserRole.CHOFER) }
    var rolExpanded by remember { mutableStateOf(false) }

    // --- Lógica de Cámara y Permisos ---
    val context = LocalContext.current
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    // Si estamos editando, inicializamos la URI con la del empleado
    LaunchedEffect(empleadoAEditar) {
        if (esModoEdicion && empleadoAEditar?.fotoUri != null) {
            imageUri = Uri.parse(empleadoAEditar!!.fotoUri)
            hasImage = true
        }
    }

    // --- Estados del ViewModel y Validación ---
    val isSaving by adminViewModel.isSaving.collectAsState()
    val isFormValid by remember(nombre, rut) {
        derivedStateOf { nombre.isNotBlank() && rut.isNotBlank() }
    }

    val topBarTitle = if (esModoEdicion) "Editar Empleado" else "Crear Nuevo Empleado"

    // ===================================================================
    //         2. INTERFAZ DE USUARIO (UN SOLO SCAFFOLD)
    // ===================================================================

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- CÍRCULO PARA MOSTRAR/CAPTURAR LA IMAGEN ---
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .clickable {
                        if (cameraPermissionState.status.isGranted) {
                            val uri = createImageUri(context)
                            imageUri = uri
                            cameraLauncher.launch(uri)
                        } else {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (hasImage && imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Foto del empleado",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                        error = painterResource(id = R.drawable.ic_profile_placeholder)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Añadir foto",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // --- CAMPOS DEL FORMULARIO ---
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = rut,
                onValueChange = { rut = it },
                label = { Text("RUT") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = rolExpanded,
                onExpandedChange = { rolExpanded = !rolExpanded }
            ) {
                OutlinedTextField(
                    value = rol.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Rol del Empleado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = rolExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = rolExpanded,
                    onDismissRequest = { rolExpanded = false }
                ) {
                    UserRole.entries.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.name) },
                            onClick = {
                                rol = item
                                rolExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- BOTÓN DE ACCIÓN: GUARDAR O ACTUALIZAR ---
            Button(
                onClick = {
                    val onCompleteCallback = { onNavigateBack() }
                    val finalImageUri = if (hasImage) imageUri?.toString() else empleadoAEditar?.fotoUri

                    if (esModoEdicion) {
                        val empleadoActualizado = empleadoAEditar!!.copy(
                            nombre = nombre,
                            rut = rut,
                            telefono = telefono,
                            correo = correo,
                            rol = rol,
                            fotoUri = finalImageUri
                        )
                        adminViewModel.actualizarEmpleado(empleadoActualizado, onCompleteCallback)
                    } else {
                        val nuevoEmpleado = Empleado(
                            id = 0,
                            nombre = nombre,
                            rut = rut,
                            telefono = telefono,
                            correo = correo,
                            rol = rol,
                            fotoUri = finalImageUri
                        )
                        adminViewModel.saveEmpleado(nuevoEmpleado, onCompleteCallback)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid && !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text(text = if (esModoEdicion) "Guardar Cambios" else "Guardar Empleado")
                }
            }
        }
    }
}
