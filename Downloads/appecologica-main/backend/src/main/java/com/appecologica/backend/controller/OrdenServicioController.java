package com.appecologica.backend.controller;

import com.appecologica.backend.dto.FinalizarOrdenRequest;
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

    @Operation(summary = "Ordenes sin ruta asignada")
    @GetMapping("/sin-ruta")
    public List<OrdenServicio> findSinRuta() {
        return ordenServicioService.findSinRuta();
    }

    @Operation(summary = "Obtiene una orden por ruta y punto")
    @GetMapping("/{rutaId}/{puntoId}")
    public OrdenServicio findByRutaAndPunto(@PathVariable Long rutaId, @PathVariable Long puntoId) {
        return ordenServicioService.findByRutaAndPunto(rutaId, puntoId);
    }

    @Operation(summary = "Actualiza estado de la orden (chofer/ADMIN)")
    @PutMapping("/{ordenId}")
    @PreAuthorize("hasAnyRole('ADMIN','CHOFER')")
    public ResponseEntity<OrdenServicio> updateEstado(@PathVariable Long ordenId,
                                                      @Valid @RequestBody OrdenUpdateRequest request) {
        return ResponseEntity.ok(ordenServicioService.updateEstado(ordenId, request));
    }

    @Operation(summary = "Inicia una orden asociada a un punto (chofer/ADMIN)")
    @PostMapping("/{puntoId}/iniciar")
    @PreAuthorize("hasAnyRole('ADMIN','CHOFER')")
    public ResponseEntity<OrdenServicio> iniciarOrden(@PathVariable Long puntoId) {
        return ResponseEntity.ok(ordenServicioService.iniciarPorPunto(puntoId));
    }

    @Operation(summary = "Finaliza una orden asociada a un punto (chofer/ADMIN)")
    @PostMapping("/{puntoId}/finalizar")
    @PreAuthorize("hasAnyRole('ADMIN','CHOFER')")
    public ResponseEntity<OrdenServicio> finalizarOrden(@PathVariable Long puntoId,
                                                        @RequestBody(required = false) FinalizarOrdenRequest request) {
        String observacion = request != null ? request.observacion() : null;
        return ResponseEntity.ok(ordenServicioService.finalizarPorPunto(puntoId, observacion));
    }
}
