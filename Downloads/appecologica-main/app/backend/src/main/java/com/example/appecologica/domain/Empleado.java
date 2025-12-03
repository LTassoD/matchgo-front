package com.example.appecologica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String rut;
    private String correo;
    private String telefono;
    private String rol;
    private String fotoUri;
    private String passwordHash;
}
