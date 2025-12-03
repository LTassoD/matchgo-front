package com.example.gestionresiduos.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description


@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        // Reemplaza el Dispatcher.Main con el dispatcher de prueba antes de que la prueba comience.
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        // Restaura el Dispatcher.Main original después de que la prueba haya terminado.
        Dispatchers.resetMain()
    }
}
