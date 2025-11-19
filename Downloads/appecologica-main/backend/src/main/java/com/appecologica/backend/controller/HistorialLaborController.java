package com.appecologica.backend.controller;

import com.appecologica.backend.model.HistorialLabor;
import com.appecologica.backend.service.HistorialLaborService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class HistorialLaborController {

    private final HistorialLaborService historialLaborService;

    public HistorialLaborController(HistorialLaborService historialLaborService) {
        this.historialLaborService = historialLaborService;
    }

    @Operation(summary = "Historial de rutas completadas por chofer (ADMIN/CHOFER)")
    @GetMapping("/chofer/{choferId}")
    @PreAuthorize("hasAnyRole('ADMIN','CHOFER')")
    public List<HistorialLabor> historialPorChofer(@PathVariable Long choferId) {
        return historialLaborService.historialPorChofer(choferId);
    }
}
