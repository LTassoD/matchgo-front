package com.example.appecologica.repository;

import com.example.appecologica.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
}
