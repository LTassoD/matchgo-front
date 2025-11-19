package com.appecologica.backend.repository;

import com.appecologica.backend.model.HistorialLabor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialLaborRepository extends JpaRepository<HistorialLabor, Long> {
    List<HistorialLabor> findByChoferId(Long choferId);
}
