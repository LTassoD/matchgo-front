package com.appecologica.backend.repository;

import com.appecologica.backend.model.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {
    List<OrdenServicio> findByRutaId(Long rutaId);
}
