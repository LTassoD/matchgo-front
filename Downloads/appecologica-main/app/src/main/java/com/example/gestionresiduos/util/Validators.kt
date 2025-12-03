
package com.example.gestionresiduos.utils

object Validators {


    fun esCorreoValido(correo: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    fun esTelefonoValido(telefono: String): Boolean {
        // Expresión regular para +569 seguido de 8 dígitos numéricos.
        val phoneRegex = Regex("^\\+569\\d{8}$")
        return phoneRegex.matches(telefono)
    }

    fun esRutValido(rut: String): Boolean {
        if (!rut.matches(Regex("^[0-9]{1,2}(\\.[0-9]{3})*-[0-9kK]$"))) {
            return false
        }
        val rutLimpio = rut.replace(".", "").replace("-", "")
        val cuerpo = rutLimpio.substring(0, rutLimpio.length - 1)
        val dv = rutLimpio.substring(rutLimpio.length - 1).lowercase()

        if (cuerpo.toIntOrNull() == null) return false

        return dv == calcularDV(cuerpo)
    }

    private fun calcularDV(cuerpoRut: String): String {
        var suma = 0
        var multiplo = 2
        for (i in cuerpoRut.length - 1 downTo 0) {
            suma += cuerpoRut[i].toString().toInt() * multiplo
            multiplo = if (multiplo == 7) 2 else multiplo + 1
        }
        val resto = 11 - (suma % 11)
        return when (resto) {
            11 -> "0"
            10 -> "k"
            else -> resto.toString()
        }
    }
}

