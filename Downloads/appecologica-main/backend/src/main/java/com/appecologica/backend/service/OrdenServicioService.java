package com.appecologica.backend.service;

import com.appecologica.backend.dto.OrdenUpdateRequest;
import com.appecologica.backend.model.EstadoOrden;
import com.appecologica.backend.model.HistorialLabor;
import com.appecologica.backend.model.OrdenServicio;
import com.appecologica.backend.repository.HistorialLaborRepository;
import com.appecologica.backend.repository.OrdenServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdenServicioService {

    private final OrdenServicioRepository ordenServicioRepository;
    private final HistorialLaborRepository historialLaborRepository;

    public OrdenServicioService(OrdenServicioRepository ordenServicioRepository,
                                HistorialLaborRepository historialLaborRepository) {
        this.ordenServicioRepository = ordenServicioRepository;
        this.historialLaborRepository = historialLaborRepository;
    }

    public List<OrdenServicio> findByRuta(Long rutaId) {
        return ordenServicioRepository.findByRutaId(rutaId);
    }

    @Transactional
    public OrdenServicio updateEstado(Long ordenId, OrdenUpdateRequest request) {
        OrdenServicio orden = ordenServicioRepository.findById(ordenId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        if (request.estado() != null) {
            orden.setEstado(request.estado());
        }
        orden.setObservacion(request.observacion());

        OrdenServicio saved = ordenServicioRepository.save(orden);

        if (request.estado() == EstadoOrden.COMPLETADA && saved.getRuta() != null) {
            HistorialLabor entry = HistorialLabor.builder()
                    .chofer(saved.getRuta().getChofer())
                    .fecha(LocalDate.now())
                    .resumen("Orden " + saved.getId() + " completada")
                    .puntosCompletados(1)
                    .build();
            historialLaborRepository.save(entry);
        }

        return saved;
    }
}
