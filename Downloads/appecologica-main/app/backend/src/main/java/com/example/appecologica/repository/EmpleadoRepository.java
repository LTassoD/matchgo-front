package com.example.appecologica.repository;

import com.example.appecologica.domain.Empleado;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByRut(String rut);
}
