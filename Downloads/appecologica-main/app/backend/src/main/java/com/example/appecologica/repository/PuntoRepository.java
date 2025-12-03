package com.example.appecologica.repository;

import com.example.appecologica.domain.Punto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntoRepository extends JpaRepository<Punto, Integer> {
}
