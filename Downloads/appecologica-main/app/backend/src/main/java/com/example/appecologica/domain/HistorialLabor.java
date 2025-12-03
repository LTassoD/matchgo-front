package com.example.appecologica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "historial_labor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialLabor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    private LocalDateTime fecha;
    private Integer puntosCompletados;
    private String observacion;
}
