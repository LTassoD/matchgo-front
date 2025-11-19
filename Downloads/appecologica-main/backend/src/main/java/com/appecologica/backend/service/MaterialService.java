package com.appecologica.backend.service;

import com.appecologica.backend.dto.MaterialRequest;
import com.appecologica.backend.model.Material;
import com.appecologica.backend.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public Material findById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Material no encontrado"));
    }

    @Transactional
    public Material create(MaterialRequest request) {
        Material material = Material.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .unidadMedida(request.unidadMedida())
                .precioReferencia(request.precioReferencia())
                .build();
        return materialRepository.save(material);
    }

    @Transactional
    public Material update(Long id, MaterialRequest request) {
        Material material = findById(id);
        material.setNombre(request.nombre());
        material.setDescripcion(request.descripcion());
        material.setUnidadMedida(request.unidadMedida());
        material.setPrecioReferencia(request.precioReferencia());
        return materialRepository.save(material);
    }

    public void delete(Long id) {
        materialRepository.deleteById(id);
    }
}
