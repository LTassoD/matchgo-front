package com.example.gestionresiduos.model




data class Empleado(

    val id: Int,
    val nombre: String,
    val rut: String,
    val correo: String,
    val telefono: String,
    val rol: UserRole,
    val fotoUri: String? = null,
    val idChoferAsignado: Int? = null,
    val idVehiculoAsignado: Int? = null
)
// Enum para representar los roles de manera segura.
enum class Rol {
    CHOFER,
    ADMIN
}