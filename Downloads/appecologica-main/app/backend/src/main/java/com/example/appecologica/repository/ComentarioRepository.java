package com.example.appecologica.repository;

import com.example.appecologica.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
