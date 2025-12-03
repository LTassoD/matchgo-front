package com.example.appecologica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordenes_servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    // Mantiene compatibilidad con puntos existentes.
    @ManyToOne
    @JoinColumn(name = "punto_id")
    private Punto punto;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "chofer_id")
    private Empleado chofer;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    private String estado;
    private String observacion;

    // Dirección específica de servicio/entrega para esta orden.
    private String direccionEnvio;

    private LocalDateTime creadaEn;
}
