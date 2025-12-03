package com.example.gestionresiduos.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.model.UserRole
import com.example.gestionresiduos.repository.FakeRepository
import com.example.gestionresiduos.util.MainCoroutineRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AdminViewModelTest {

    // Regla para ejecutar tareas de LiveData y ViewModel de forma síncrona
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Regla para controlar el hilo de las corrutinas en las pruebas
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: AdminViewModel
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setup() {
        // Antes de cada prueba, creamos un FakeRepository y un AdminViewModel nuevos
        fakeRepository = FakeRepository()
        // ¡Importante! Inyectamos el FakeRepository en el ViewModel
        viewModel = AdminViewModel(fakeRepository)
    }

    @Test
    fun `cargarEmpleados() actualiza la lista de empleados correctamente`() = runTest {
        // ARRANGE: Preparamos los datos de prueba
        val empleadosDePrueba = listOf(
            Empleado(1, "Nico Iturrieta", "111", "nico@test.com", "123", UserRole.ADMIN, null),
            Empleado(2, "Jane Doe", "222", "jane@test.com", "456", UserRole.CHOFER, null)
        )
        fakeRepository.setEmpleados(empleadosDePrueba)

        // ACT: Ejecutamos la acción a probar
        viewModel.cargarEmpleados()

        // ASSERT: Verificamos que el resultado sea el esperado
        val listaActual = viewModel.empleados.value
        assertEquals(2, listaActual.size)
        assertEquals("Nico Iturrieta", listaActual[0].nombre)
    }

    @Test
    fun `borrarEmpleado() elimina el empleado de la lista`() = runTest {
        // ARRANGE
        val empleadosDePrueba = listOf(
            Empleado(1, "Nico Iturrieta", "111", "nico@test.com", "123", UserRole.ADMIN, null),
            Empleado(2, "Jane Doe", "222", "jane@test.com", "456", UserRole.CHOFER, null)
        )
        fakeRepository.setEmpleados(empleadosDePrueba)
        viewModel.cargarEmpleados() // Cargamos los datos iniciales

        // ACT: Ejecutamos la acción de borrar
        viewModel.borrarEmpleado(1) // Borramos a Nico

        // ASSERT: Verificamos el resultado
        val listaActual = viewModel.empleados.value
        assertEquals(1, listaActual.size)
        assertEquals("Jane Doe", listaActual[0].nombre)
    }

    @Test
    fun `actualizarEmpleado() modifica el empleado en la lista`() = runTest {
        // ARRANGE
        val empleadosDePrueba = listOf(
            Empleado(1, "Nico Iturrieta", "111", "nico@test.com", "123", UserRole.ADMIN, null)
        )
        fakeRepository.setEmpleados(empleadosDePrueba)
        viewModel.cargarEmpleados()

        val empleadoActualizado = Empleado(1, "Nico Iturrieta V.", "111", "nico.v@test.com", "123", UserRole.ADMIN, null)

        // ACT: Ejecutamos la acción de actualizar
        viewModel.actualizarEmpleado(empleadoActualizado) { /* onComplete no hace nada en la prueba */ }

        // ASSERT: Verificamos el resultado
        val listaActual = viewModel.empleados.value
        assertEquals(1, listaActual.size)
        assertEquals("Nico Iturrieta V.", listaActual[0].nombre)
        assertEquals("nico.v@test.com", listaActual[0].correo)
    }

    @Test
    fun `cargarEmpleados() con repositorio vacío devuelve lista vacía`() = runTest {
        // ARRANGE: No añadimos empleados al repositorio

        // ACT
        viewModel.cargarEmpleados()

        // ASSERT
        assertTrue(viewModel.empleados.value.isEmpty())
    }
}
