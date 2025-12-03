package com.appecologica.backend.service;

import com.appecologica.backend.dto.RutaRequest;
import com.appecologica.backend.model.OrdenServicio;
import com.appecologica.backend.model.PuntoRecoleccion;
import com.appecologica.backend.model.Ruta;
import com.appecologica.backend.model.User;
import com.appecologica.backend.model.Vehiculo;
import com.appecologica.backend.repository.OrdenServicioRepository;
import com.appecologica.backend.repository.PuntoRecoleccionRepository;
import com.appecologica.backend.repository.RutaRepository;
import com.appecologica.backend.repository.UserRepository;
import com.appecologica.backend.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;
    private final UserRepository userRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PuntoRecoleccionRepository puntoRepository;
    private final OrdenServicioRepository ordenServicioRepository;

    public RutaService(RutaRepository rutaRepository,
                       UserRepository userRepository,
                       VehiculoRepository vehiculoRepository,
                       PuntoRecoleccionRepository puntoRepository,
                       OrdenServicioRepository ordenServicioRepository) {
        this.rutaRepository = rutaRepository;
        this.userRepository = userRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.puntoRepository = puntoRepository;
        this.ordenServicioRepository = ordenServicioRepository;
    }

    public List<Ruta> findAll() {
        return rutaRepository.findAll();
    }

    public Ruta findById(Long id) {
        return rutaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada"));
    }

    public List<Ruta> findByChofer(Long choferId) {
        return rutaRepository.findByChoferId(choferId);
    }

    @Transactional
    public Ruta create(RutaRequest request) {
        User chofer = userRepository.findById(request.choferId())
                .orElseThrow(() -> new IllegalArgumentException("Chofer no encontrado"));
        Vehiculo vehiculo = vehiculoRepository.findById(request.vehiculoId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

        Ruta ruta = Ruta.builder()
                .fecha(request.fecha())
                .chofer(chofer)
                .vehiculo(vehiculo)
                .build();
        Ruta saved = rutaRepository.save(ruta);

        if (request.puntoIds() != null) {
            for (Long puntoId : request.puntoIds()) {
                PuntoRecoleccion punto = puntoRepository.findById(puntoId)
                        .orElseThrow(() -> new IllegalArgumentException("Punto no encontrado: " + puntoId));
                OrdenServicio orden = OrdenServicio.builder()
                        .ruta(saved)
                        .punto(punto)
                        .build();
                ordenServicioRepository.save(orden);
            }
        }
        // Asociar órdenes existentes (creadas previamente) si se envían en la solicitud
        if (request.ordenIds() != null) {
            for (Long ordenId : request.ordenIds()) {
                OrdenServicio orden = ordenServicioRepository.findById(ordenId)
                        .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + ordenId));
                if (orden.getRuta() != null) {
                    throw new IllegalArgumentException("La orden " + ordenId + " ya está asignada a una ruta");
                }
                orden.setRuta(saved);
                ordenServicioRepository.save(orden);
            }
        }

        return saved;
    }
}
