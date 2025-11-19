package com.appecologica.backend.repository;

import com.appecologica.backend.model.PuntoRecoleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuntoRecoleccionRepository extends JpaRepository<PuntoRecoleccion, Long> {
    List<PuntoRecoleccion> findByClienteId(Long clienteId);
}
