package com.appecologica.backend.service;

import com.appecologica.backend.dto.PuntoRequest;
import com.appecologica.backend.model.Cliente;
import com.appecologica.backend.model.PuntoRecoleccion;
import com.appecologica.backend.repository.ClienteRepository;
import com.appecologica.backend.repository.PuntoRecoleccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntoRecoleccionService {

    private final PuntoRecoleccionRepository puntoRepository;
    private final ClienteRepository clienteRepository;

    public PuntoRecoleccionService(PuntoRecoleccionRepository puntoRepository,
                                   ClienteRepository clienteRepository) {
        this.puntoRepository = puntoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<PuntoRecoleccion> findAll() {
        return puntoRepository.findAll();
    }

    public List<PuntoRecoleccion> findByCliente(Long clienteId) {
        return puntoRepository.findByClienteId(clienteId);
    }

    public PuntoRecoleccion create(PuntoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        PuntoRecoleccion punto = PuntoRecoleccion.builder()
                .cliente(cliente)
                .direccion(request.direccion())
                .material(request.material())
                .contenedor(request.contenedor())
                .observaciones(request.observaciones())
                .build();
        return puntoRepository.save(punto);
    }

    public PuntoRecoleccion update(Long id, PuntoRequest request) {
        PuntoRecoleccion punto = puntoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Punto no encontrado"));
        if (!punto.getCliente().getId().equals(request.clienteId())) {
            Cliente cliente = clienteRepository.findById(request.clienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            punto.setCliente(cliente);
        }
        punto.setDireccion(request.direccion());
        punto.setMaterial(request.material());
        punto.setContenedor(request.contenedor());
        punto.setObservaciones(request.observaciones());
        return puntoRepository.save(punto);
    }

    public void delete(Long id) {
        puntoRepository.deleteById(id);
    }
}
