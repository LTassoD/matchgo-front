package com.appecologica.backend.service;

import com.appecologica.backend.dto.VehiculoRequest;
import com.appecologica.backend.model.Vehiculo;
import com.appecologica.backend.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo findById(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
    }

    @Transactional
    public Vehiculo create(VehiculoRequest request) {
        boolean habilitado = request.habilitado() == null || request.habilitado();
        Vehiculo vehiculo = Vehiculo.builder()
                .patente(request.patente().toUpperCase())
                .modelo(request.modelo())
                .capacidadKg(request.capacidadKg())
                .habilitado(habilitado)
                .build();
        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    public Vehiculo update(Long id, VehiculoRequest request) {
        Vehiculo vehiculo = findById(id);
        vehiculo.setModelo(request.modelo());
        vehiculo.setCapacidadKg(request.capacidadKg());
        if (request.habilitado() != null) {
            vehiculo.setHabilitado(request.habilitado());
        }
        return vehiculoRepository.save(vehiculo);
    }

    public void delete(Long id) {
        vehiculoRepository.deleteById(id);
    }
}
