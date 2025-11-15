package com.example.gestionresiduos.repository.local

import com.example.gestionresiduos.model.Material

interface MaterialDao {
    fun getAll(): List<Material>
    fun upsert(material: Material)
}

internal object InMemoryMaterialDao : MaterialDao {
    override fun getAll(): List<Material> = AppDatabase.materiales.toList()

    override fun upsert(material: Material) {
        AppDatabase.materiales.removeAll { it.id == material.id }
        AppDatabase.materiales.add(material)
    }
}
