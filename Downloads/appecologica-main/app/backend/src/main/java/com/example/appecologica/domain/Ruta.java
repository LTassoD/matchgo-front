package com.example.appecologica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rutas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private LocalDate fecha;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "chofer_id")
    private Empleado chofer;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToMany(mappedBy = "ruta")
    @Builder.Default
    private List<Punto> puntos = new ArrayList<>();

    @OneToMany(mappedBy = "ruta")
    @Builder.Default
    private List<OrdenServicio> ordenes = new ArrayList<>();
}
