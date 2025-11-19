package com.appecologica.backend.controller;

import com.appecologica.backend.dto.VehiculoRequest;
import com.appecologica.backend.model.Vehiculo;
import com.appecologica.backend.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @Operation(summary = "Listado de vehículos")
    @GetMapping
    public List<Vehiculo> findAll() {
        return vehiculoService.findAll();
    }

    @Operation(summary = "Registra un vehículo (solo ADMIN)")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vehiculo> create(@Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity.ok(vehiculoService.create(request));
    }

    @Operation(summary = "Actualiza vehículo (solo ADMIN)")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vehiculo> update(@PathVariable Long id, @Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity.ok(vehiculoService.update(id, request));
    }

    @Operation(summary = "Elimina un vehículo (solo ADMIN)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
