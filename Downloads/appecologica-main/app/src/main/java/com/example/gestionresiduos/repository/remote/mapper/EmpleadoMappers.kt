
package com.example.gestionresiduos.repository.remote.mapper
import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.model.UserRole
import com.example.gestionresiduos.repository.remote.dto.CrearEmpleadoRequestDTO
import com.example.gestionresiduos.repository.remote.dto.CrearEmpleadoResponseDTO
import com.example.gestionresiduos.repository.remote.dto.EmpleadoDTO
import com.example.gestionresiduos.repository.remote.dto.LoginResponseDTO

// -------------------------------------------------------------------
// --- MAPPERS PARA EL REGISTRO DE EMPLEADOS ---
// -------------------------------------------------------------------

/**
 * CORRECCIÓN: Esta función convierte un objeto de dominio 'Empleado' a un 'CrearEmpleadoRequestDTO'
 * para poder enviarlo a la API.
 */
fun Empleado.toRequestDto(): CrearEmpleadoRequestDTO {
    return CrearEmpleadoRequestDTO(
        nombre = this.nombre,
        rut = this.rut,
        correo = this.correo,
        telefono = this.telefono,
        // Convierte el enum a su representación en String (ej. UserRole.CHOFER -> "CHOFER")
        rol = this.rol.name,
        fotoUri = this.fotoUri
    )
}

fun Empleado.toDto(): EmpleadoDTO {
    return EmpleadoDTO(
        id = this.id,
        nombre = this.nombre,
        rut = this.rut,
        correo = this.correo, // Asegúrate de que los nombres coincidan
        telefono = this.telefono,
        rol = this.rol.name, // Convierte el enum a su representación en String
        fotoUri = this.fotoUri
    )
}

/**
 * CORRECCIÓN: Esta función convierte un 'CrearEmpleadoResponseDTO' (la respuesta de la API)
 * a un objeto de dominio 'Empleado' que la app puede usar.
 */
fun CrearEmpleadoResponseDTO.toDomain(): Empleado {
    // Se debe devolver un objeto 'Empleado', no un DTO.
    return Empleado(
        id = this.id,
        nombre = this.nombre,
        rut = this.rut,
        correo = this.correo,
        telefono = this.telefono,
        // Convierte el String de la API de vuelta a un enum.
        // Se usa uppercase() para asegurar la compatibilidad (e.g., "chofer" -> "CHOFER").
        rol = UserRole.valueOf(this.rol.uppercase()),
        fotoUri = this.fotoUri
    )
}


// -------------------------------------------------------------------
// --- MAPPER PARA LA RESPUESTA DEL LOGIN ---
// -------------------------------------------------------------------

/**
 * Convierte un 'LoginResponseDTO' (la respuesta completa de la API de login)
 * a un objeto de dominio 'Empleado', extrayendo los datos del empleado del DTO.
 */
fun LoginResponseDTO.toDomain(): Pair<Empleado, String?> {
    // --- CORRECCIÓN DEFINITIVA ---
    // 1. Comprueba si el objeto 'empleado' dentro de la respuesta es nulo.
    if (this.empleado == null) {
        // 2. Si es nulo, lanza una excepción clara.
        //    El ViewModel/Repository atrapará esta excepción y mostrará un error al usuario.
        throw Exception("Credenciales incorrectas o usuario no encontrado")
    }
    val empleadoDominio = this.empleado.toDomain()
    val tokenRespuesta = this.token

    return Pair(empleadoDominio, tokenRespuesta)

}


// -------------------------------------------------------------------
// --- MAPPER PARA EL DTO GENÉRICO DE EMPLEADO ---
// -------------------------------------------------------------------

fun EmpleadoDTO.toDomain(): Empleado {
    return Empleado(
        id = this.id,
        nombre = this.nombre,
        rut = this.rut,
        correo = this.correo, // Asegúrate de que los nombres coincidan (email en DTO, correo en modelo)
        telefono = this.telefono,
        rol = UserRole.valueOf(this.rol.uppercase()),
        // Si tu modelo 'Empleado' tiene más campos que no vienen en 'EmpleadoDTO',
        // tendrás que asignarles valores por defecto o hacerlos opcionales.
        fotoUri = null // Por ejemplo, si el DTO no incluye la foto.
    )
}
