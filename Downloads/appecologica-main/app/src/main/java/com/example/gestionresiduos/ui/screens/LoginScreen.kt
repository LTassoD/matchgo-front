package com.example.gestionresiduos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier // La importación incorrecta fue eliminada, esta es la correcta.
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionresiduos.R
import com.example.gestionresiduos.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onChofer: () -> Unit,
    onAdmin: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var rut by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val isLoading by authViewModel.isLoading.collectAsState()
    val error by authViewModel.error.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Column organiza los elementos: logo, campos de texto y botón.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()), // Para que no se corte en pantallas pequeñas
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // IMAGEN DEL LOGO
            Image(
                painter = painterResource(id = R.drawable.vectorecologica), // Usa el nombre de tu logo
                contentDescription = "Logo de la Empresa",
                modifier = Modifier
                    .fillMaxWidth(0.6f) // El logo ocupará el 60% del ancho
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Fit // Asegura que la imagen se escale correctamente
            )

            // Título de la pantalla
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Inicia sesión para continuar",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de texto para el RUT
            OutlinedTextField(
                value = rut,
                onValueChange = { rut = it },
                label = { Text("RUT") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Muestra el mensaje de error si existe
            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de Iniciar Sesión
            Button(
                onClick = {
                    authViewModel.login(rut, password) { usuario ->
                        if (usuario.rol.name == "ADMIN") {
                            onAdmin()
                        } else {
                            onChofer()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading // El botón se deshabilita mientras carga
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Iniciar Sesión", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
