package com.example.appecologica.repository;

import com.example.appecologica.domain.Empleado;
import com.example.appecologica.domain.HistorialLabor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialLaborRepository extends JpaRepository<HistorialLabor, Integer> {
    List<HistorialLabor> findByEmpleado(Empleado empleado);
}
