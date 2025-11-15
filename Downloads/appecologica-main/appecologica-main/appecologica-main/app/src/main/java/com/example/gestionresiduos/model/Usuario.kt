package com.example.gestionresiduos.model

// Representa a un usuario del sistema, puede ser Chofer o Admin
data class Usuario(
    val id: String,     // Identificador único (UUID o generado por backend)
    val nombre: String, // Nombre completo del usuario
    val rut: String,    // RUT chileno o identificación equivalente
    val rol: Rol        // Rol: CHOFER o ADMIN
)
