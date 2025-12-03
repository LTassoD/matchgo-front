package com.example.gestionresiduos.repository

import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.model.UserRole
import kotlinx.coroutines.delay

/**
 * Un repositorio falso para simular la autenticación sin necesidad de una base de datos real o una API.
 * Ideal para pruebas de UI y desarrollo rápido.
 *
 * ¡CORREGIDO! Ahora utiliza 'id' de tipo Int para ser compatible con el modelo de Room.
 */
class AuthRepositoryFake {

    suspend fun login(rut: String, password : String): Empleado {
        // Simula una espera de red de 1.5 segundos para que se pueda ver el indicador de carga.
        delay(1500)

        // --- LÓGICA DE PRUEBA ACTUALIZADA ---
        // Simula un login exitoso para credenciales específicas.

        if (rut == "1-1" && password == "admin") {
            // Si las credenciales son de admin, devuelve un usuario ADMIN con ID de tipo Int.
            return Empleado(
                id = 1, // <-- CORREGIDO: de "user001" a 1 (Int)
                nombre = "Nico Administrador",
                rut = rut,
                rol = UserRole.ADMIN,
                telefono = "912345678",
                correo = "nico.admin@empresa.com"
            )
        } else if (rut == "2-2" && password == "chofer") {
            // Si las credenciales son de chofer, devuelve un usuario CHOFER con ID de tipo Int.
            return Empleado(
                id = 2, // <-- CORREGIDO: de "user002" a 2 (Int)
                nombre = "Juan Chofer",
                rut = rut,
                rol = UserRole.CHOFER,
                telefono = "987654321",
                correo = "juan.chofer@empresa.com"
            )
        } else {
            // Si las credenciales son incorrectas, lanza una excepción
            // como lo haría un repositorio real.
            throw Exception("RUT o contraseña incorrectos")
        }
    }
}
