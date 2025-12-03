package com.example.appecologica.web;

import com.example.appecologica.domain.Material;
import com.example.appecologica.repository.MaterialRepository;
import com.example.appecologica.web.dto.MaterialDtos;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materiales")
public class MaterialController {

    private final MaterialRepository materialRepository;

    public MaterialController(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @PostMapping
    public MaterialDtos.MaterialResponse crear(@RequestBody MaterialDtos.CrearMaterialRequest request) {
        Material material = Material.builder()
            .nombre(request.getNombre())
            .descripcion(request.getDescripcion())
            .build();
        return MaterialDtos.MaterialResponse.fromEntity(materialRepository.save(material));
    }

    @GetMapping
    public List<MaterialDtos.MaterialResponse> listar() {
        return materialRepository.findAll().stream().map(MaterialDtos.MaterialResponse::fromEntity).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDtos.MaterialResponse> actualizar(@PathVariable Integer id, @RequestBody MaterialDtos.CrearMaterialRequest request) {
        return materialRepository.findById(id)
            .map(m -> {
                m.setNombre(request.getNombre());
                m.setDescripcion(request.getDescripcion());
                return ResponseEntity.ok(MaterialDtos.MaterialResponse.fromEntity(materialRepository.save(m)));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!materialRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        materialRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
