package com.example.gestionresiduos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestionresiduos.repository.RutaRepository

class RutaViewModelFactory(
    private val choferId: String,
    private val repository: RutaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RutaViewModel::class.java)) {
            return RutaViewModel(choferId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        fun default(choferId: String) = RutaViewModelFactory(
            choferId = choferId,
            repository = com.example.gestionresiduos.repository.RutaRepositoryFake()
        )
    }
}
