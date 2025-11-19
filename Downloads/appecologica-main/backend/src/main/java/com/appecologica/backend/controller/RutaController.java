package com.appecologica.backend.controller;

import com.appecologica.backend.dto.RutaRequest;
import com.appecologica.backend.model.Ruta;
import com.appecologica.backend.service.RutaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @Operation(summary = "Listado de rutas")
    @GetMapping
    public List<Ruta> findAll() {
        return rutaService.findAll();
    }

    @Operation(summary = "Rutas asignadas a un chofer")
    @GetMapping("/chofer/{choferId}")
    public List<Ruta> findByChofer(@PathVariable Long choferId) {
        return rutaService.findByChofer(choferId);
    }

    @Operation(summary = "Crea una ruta con sus ordenes (solo ADMIN)")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ruta> create(@Valid @RequestBody RutaRequest request) {
        return ResponseEntity.ok(rutaService.create(request));
    }
}
