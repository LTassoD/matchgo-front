package com.example.gestionresiduos.model

/**
 * Modelo sencillo para encapsular las credenciales ingresadas en el formulario.
 */
data class LoginRequest(
    val rut: String,
    val password: String
) {
    val sanitizedRut: String = rut.trim().replace(".", "").uppercase()

    fun validate(): LoginValidationResult {
        val rutError = when {
            sanitizedRut.isEmpty() -> "El RUT es obligatorio"
            !sanitizedRut.matches(Regex("^[0-9K]+-?[0-9K]\$")) -> "Formato de RUT inválido"
            else -> null
        }

        val passError = when {
            password.isBlank() -> "La contraseña es obligatoria"
            password.length < 4 -> "La contraseña debe tener al menos 4 caracteres"
            else -> null
        }

        return LoginValidationResult(rutError, passError)
    }
}

data class LoginValidationResult(
    val rutError: String? = null,
    val passwordError: String? = null
) {
    val isValid: Boolean get() = rutError == null && passwordError == null
}
