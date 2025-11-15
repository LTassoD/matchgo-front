package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.model.Rol
import com.example.gestionresiduos.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    vm: AuthViewModel = viewModel(),
    onChofer: () -> Unit,
    onAdmin: () -> Unit
) {
    val usuario by vm.usuario.collectAsState()
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()
    val validation by vm.validation.collectAsState()

    var rut by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    LaunchedEffect(usuario) {
        usuario?.let { if (it.rol == Rol.CHOFER) onChofer() else onAdmin() }
    }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("GestioResiduos", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = rut,
            onValueChange = {
                rut = it
                vm.clearError()
            },
            label = { Text("RUT") },
            isError = validation.rutError != null,
            supportingText = { validation.rutError?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = pass,
            onValueChange = {
                pass = it
                vm.clearError()
            },
            label = { Text("Contraseña") },
            singleLine = true,
            isError = validation.passwordError != null,
            supportingText = { validation.passwordError?.let { Text(it) } },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { vm.login(rut.trim(), pass) },
            enabled = !loading, modifier = Modifier.fillMaxWidth()
        ) { Text(if (loading) "Ingresando..." else "Ingresar") }

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        // TODO: maqueta layout según tu mockup (logo, branding, etc.)
    }
}
