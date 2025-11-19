package com.appecologica.backend.controller;

import com.appecologica.backend.dto.OrdenUpdateRequest;
import com.appecologica.backend.model.OrdenServicio;
import com.appecologica.backend.service.OrdenServicioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenServicioController {

    private final OrdenServicioService ordenServicioService;

    public OrdenServicioController(OrdenServicioService ordenServicioService) {
        this.ordenServicioService = ordenServicioService;
    }

    @Operation(summary = "Ordenes por ruta")
    @GetMapping("/ruta/{rutaId}")
    public List<OrdenServicio> findByRuta(@PathVariable Long rutaId) {
        return ordenServicioService.findByRuta(rutaId);
    }

    @Operation(summary = "Actualiza estado de la orden (chofer/ADMIN)")
    @PutMapping("/{ordenId}")
    @PreAuthorize("hasAnyRole('ADMIN','CHOFER')")
    public ResponseEntity<OrdenServicio> updateEstado(@PathVariable Long ordenId,
                                                      @Valid @RequestBody OrdenUpdateRequest request) {
        return ResponseEntity.ok(ordenServicioService.updateEstado(ordenId, request));
    }
}
