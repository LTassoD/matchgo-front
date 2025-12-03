package com.example.appecologica.web.dto;

import com.example.appecologica.domain.Vehiculo;
import lombok.Data;

public class VehiculoDtos {
    @Data
    public static class CrearVehiculoRequest {
        private String marca;
        private String modelo;
        private String patente;
        private Integer anio;
        private Integer capacidad;
    }

    @Data
    public static class VehiculoResponse {
        private Integer id;
        private String marca;
        private String modelo;
        private String patente;
        private Integer anio;
        private Integer capacidad;

        public static VehiculoResponse fromEntity(Vehiculo v) {
            VehiculoResponse dto = new VehiculoResponse();
            dto.setId(v.getId());
            dto.setMarca(v.getMarca());
            dto.setModelo(v.getModelo());
            dto.setPatente(v.getPatente());
            dto.setAnio(v.getAnio());
            dto.setCapacidad(v.getCapacidad());
            return dto;
        }
    }
}
