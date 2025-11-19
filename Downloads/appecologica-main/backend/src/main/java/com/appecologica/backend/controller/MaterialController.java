package com.appecologica.backend.controller;

import com.appecologica.backend.dto.MaterialRequest;
import com.appecologica.backend.model.Material;
import com.appecologica.backend.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @Operation(summary = "Listado de materiales")
    @GetMapping
    public List<Material> findAll() {
        return materialService.findAll();
    }

    @Operation(summary = "Crea un material (solo ADMIN)")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Material> create(@Valid @RequestBody MaterialRequest request) {
        return ResponseEntity.ok(materialService.create(request));
    }

    @Operation(summary = "Actualiza un material (solo ADMIN)")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Material> update(@PathVariable Long id, @Valid @RequestBody MaterialRequest request) {
        return ResponseEntity.ok(materialService.update(id, request));
    }

    @Operation(summary = "Elimina un material (solo ADMIN)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
