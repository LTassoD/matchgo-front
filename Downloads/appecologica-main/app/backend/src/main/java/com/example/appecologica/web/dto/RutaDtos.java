package com.example.appecologica.web.dto;

import com.example.appecologica.domain.Punto;
import com.example.appecologica.domain.OrdenServicio;
import com.example.appecologica.domain.Ruta;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

public class RutaDtos {
    @Data
    public static class CrearRutaRequest {
        private String nombre;
        private String fecha;
        private String estado;
        private Integer idChoferAsignado;
        private Integer idVehiculo;
    }

    @Data
    public static class PuntoResponse {
        private Integer id;
        private Integer clienteId;
        private String nombreCliente;
        private String direccion;
        private String material;
        private String contenedor;
        private String estado;
        private String observaciones;
    }

    @Data
    public static class RutaResponse {
        private Integer id;
        private String nombre;
        private String fecha;
        private String estado;
        private Integer choferId;
        private List<PuntoResponse> puntos;
        private List<OrdenDtos.OrdenResponse> ordenes;

        public static RutaResponse fromEntity(Ruta r) {
            RutaResponse dto = new RutaResponse();
            dto.setId(r.getId());
            dto.setNombre(r.getNombre());
            dto.setFecha(r.getFecha() != null ? r.getFecha().toString() : null);
            dto.setEstado(r.getEstado());
            dto.setChoferId(r.getChofer() != null ? r.getChofer().getId() : null);
            dto.setPuntos(r.getPuntos().stream().map(RutaDtos::mapPunto).toList());
            dto.setOrdenes(r.getOrdenes().stream().map(OrdenDtos.OrdenResponse::fromEntity).toList());
            return dto;
        }
    }

    public static PuntoResponse mapPunto(Punto p) {
        PuntoResponse dto = new PuntoResponse();
        dto.setId(p.getId());
        dto.setClienteId(p.getCliente() != null ? p.getCliente().getId() : null);
        dto.setNombreCliente(p.getCliente() != null ? p.getCliente().getNombreEmpresa() : null);
        dto.setDireccion(p.getCliente() != null ? p.getCliente().getDireccion() : null);
        dto.setMaterial(p.getCliente() != null ? p.getCliente().getMaterial() : null);
        dto.setContenedor(p.getContenedor());
        dto.setEstado(p.getEstado());
        dto.setObservaciones(p.getObservaciones());
        return dto;
    }

    public static LocalDate parseFecha(String fecha) {
        return fecha != null ? LocalDate.parse(fecha) : null;
    }
}
