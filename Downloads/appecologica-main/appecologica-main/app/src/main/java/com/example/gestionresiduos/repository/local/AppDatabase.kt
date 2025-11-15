package com.example.gestionresiduos.repository.local

import com.example.gestionresiduos.model.Cliente
import com.example.gestionresiduos.model.HistorialLabor
import com.example.gestionresiduos.model.Material
import com.example.gestionresiduos.model.Rol
import com.example.gestionresiduos.model.Usuario
import com.example.gestionresiduos.model.Vehiculo
import java.time.LocalDate

/**
 * "Base de datos" en memoria para prototipar la app sin backend.
 * Los DAO leen y escriben sobre estas listas.
 */
object AppDatabase {
    internal val clientes = mutableListOf(
        Cliente(id = "c1", nombre = "Eco Plast", direccion = "Av. Norte 123"),
        Cliente(id = "c2", nombre = "Viña Verde", direccion = "Camino Los Pinos 45"),
        Cliente(id = "c3", nombre = "Textiles Andes", direccion = "Ruta 5 KM 50")
    )

    internal val materiales = mutableListOf(
        Material(id = "m1", nombre = "Cartón", descripcion = "Cartón prensado", unidadMedida = "Kg", precioReferencia = 120.0),
        Material(id = "m2", nombre = "Vidrio", descripcion = "Vidrio verde y transparente", unidadMedida = "Kg", precioReferencia = 90.0),
        Material(id = "m3", nombre = "Plástico PET", descripcion = "Botellas PET limpias", unidadMedida = "Kg", precioReferencia = 150.0)
    )

    internal val vehiculos = mutableListOf(
        Vehiculo(id = "v1", patente = "AA-BB11", modelo = "Hino 300", capacidadKg = 2500),
        Vehiculo(id = "v2", patente = "CC-DD22", modelo = "Fuso Canter", capacidadKg = 3200, habilitado = false),
        Vehiculo(id = "v3", patente = "EE-FF33", modelo = "Iveco Daily", capacidadKg = 1800)
    )

    internal val historial = mutableListOf(
        HistorialLabor(id = "h1", choferId = "chofer-1", fecha = LocalDate.now().minusDays(1), resumen = "Ruta Centro", puntosCompletados = 8),
        HistorialLabor(id = "h2", choferId = "chofer-1", fecha = LocalDate.now().minusDays(2), resumen = "Ruta Costera", puntosCompletados = 5)
    )

    internal val usuarios = mutableListOf(
        UsuarioCredential(Usuario(id = "admin-1", nombre = "Ana Admin", rut = "11111111-1", rol = Rol.ADMIN), password = "admin123"),
        UsuarioCredential(Usuario(id = "chofer-1", nombre = "Carlos Chofer", rut = "22222222-2", rol = Rol.CHOFER), password = "chofer123")
    )
}
