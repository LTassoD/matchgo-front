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

    public OrdenServicio findByRutaAndPunto(Long rutaId, Long puntoId) {
        return ordenServicioRepository.findByRutaIdAndPuntoId(rutaId, puntoId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada para la ruta y punto"));
    }

    public List<OrdenServicio> findSinRuta() {
        return ordenServicioRepository.findByRutaIsNull();
    }

    @Transactional
    public OrdenServicio updateEstado(Long ordenId, OrdenUpdateRequest request) {
        OrdenServicio orden = ordenServicioRepository.findById(ordenId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        aplicarEstadoYObservacion(orden, request.estado(), request.observacion());

        OrdenServicio saved = ordenServicioRepository.save(orden);
        registrarHistorialSiCompleta(saved, request.estado());

        return saved;
    }

    @Transactional
    public OrdenServicio iniciarPorPunto(Long puntoId) {
        OrdenServicio orden = obtenerPorPunto(puntoId);
        aplicarEstadoYObservacion(orden, EstadoOrden.EN_PROCESO, orden.getObservacion());
        return ordenServicioRepository.save(orden);
    }

    @Transactional
    public OrdenServicio finalizarPorPunto(Long puntoId, String observacion) {
        OrdenServicio orden = obtenerPorPunto(puntoId);
        aplicarEstadoYObservacion(orden, EstadoOrden.COMPLETADA, observacion);
        OrdenServicio saved = ordenServicioRepository.save(orden);
        registrarHistorialSiCompleta(saved, EstadoOrden.COMPLETADA);
        return saved;
    }

    private OrdenServicio obtenerPorPunto(Long puntoId) {
        return ordenServicioRepository.findFirstByPuntoId(puntoId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada para el punto " + puntoId));
    }

    private void aplicarEstadoYObservacion(OrdenServicio orden, EstadoOrden nuevoEstado, String observacion) {
        if (nuevoEstado != null) {
            orden.setEstado(nuevoEstado);
        }
        orden.setObservacion(observacion);
    }

    private void registrarHistorialSiCompleta(OrdenServicio saved, EstadoOrden nuevoEstado) {
        if (nuevoEstado == EstadoOrden.COMPLETADA && saved.getRuta() != null) {
            HistorialLabor entry = HistorialLabor.builder()
                    .chofer(saved.getRuta().getChofer())
                    .fecha(LocalDate.now())
                    .resumen("Orden " + saved.getId() + " completada")
                    .puntosCompletados(1)
                    .build();
            historialLaborRepository.save(entry);
        }
    }
}
