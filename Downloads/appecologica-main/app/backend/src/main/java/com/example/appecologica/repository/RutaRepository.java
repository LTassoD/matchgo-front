package com.example.appecologica.repository;

import com.example.appecologica.domain.Empleado;
import com.example.appecologica.domain.Ruta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    List<Ruta> findByChofer(Empleado chofer);
}
