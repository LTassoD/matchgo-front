package com.appecologica.backend.repository;

import com.appecologica.backend.model.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {
    List<OrdenServicio> findByRutaId(Long rutaId);

    Optional<OrdenServicio> findByRutaIdAndPuntoId(Long rutaId, Long puntoId);

    Optional<OrdenServicio> findFirstByPuntoId(Long puntoId);

    List<OrdenServicio> findByRutaIsNull();
}
