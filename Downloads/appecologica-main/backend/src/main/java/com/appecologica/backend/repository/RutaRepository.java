package com.appecologica.backend.repository;

import com.appecologica.backend.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Long> {
    List<Ruta> findByChoferId(Long choferId);
    List<Ruta> findByFecha(LocalDate fecha);
}
