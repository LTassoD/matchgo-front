package com.example.appecologica.web.dto;

import com.example.appecologica.domain.Material;
import lombok.Data;

public class MaterialDtos {
    @Data
    public static class CrearMaterialRequest {
        private String nombre;
        private String descripcion;
    }

    @Data
    public static class MaterialResponse {
        private Integer id;
        private String nombre;
        private String descripcion;

        public static MaterialResponse fromEntity(Material m) {
            MaterialResponse dto = new MaterialResponse();
            dto.setId(m.getId());
            dto.setNombre(m.getNombre());
            dto.setDescripcion(m.getDescripcion());
            return dto;
        }
    }
}
