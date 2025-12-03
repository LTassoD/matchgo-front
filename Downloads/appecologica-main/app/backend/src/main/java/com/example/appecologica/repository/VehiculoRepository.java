package com.example.appecologica.repository;

import com.example.appecologica.domain.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
}
