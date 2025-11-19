package com.appecologica.backend.controller;

import com.appecologica.backend.dto.PuntoRequest;
import com.appecologica.backend.model.PuntoRecoleccion;
import com.appecologica.backend.service.PuntoRecoleccionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntos")
public class PuntoRecoleccionController {

    private final PuntoRecoleccionService puntoService;

    public PuntoRecoleccionController(PuntoRecoleccionService puntoService) {
        this.puntoService = puntoService;
    }

    @Operation(summary = "Lista todos los puntos")
    @GetMapping
    public List<PuntoRecoleccion> findAll() {
        return puntoService.findAll();
    }

    @Operation(summary = "Puntos por cliente")
    @GetMapping("/cliente/{clienteId}")
    public List<PuntoRecoleccion> findByCliente(@PathVariable Long clienteId) {
        return puntoService.findByCliente(clienteId);
    }

    @Operation(summary = "Crea un punto (solo ADMIN)")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PuntoRecoleccion> create(@Valid @RequestBody PuntoRequest request) {
        return ResponseEntity.ok(puntoService.create(request));
    }

    @Operation(summary = "Actualiza un punto (solo ADMIN)")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PuntoRecoleccion> update(@PathVariable Long id, @Valid @RequestBody PuntoRequest request) {
        return ResponseEntity.ok(puntoService.update(id, request));
    }

    @Operation(summary = "Elimina un punto (solo ADMIN)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        puntoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
