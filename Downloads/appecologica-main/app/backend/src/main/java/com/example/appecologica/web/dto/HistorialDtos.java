package com.example.appecologica.web.dto;

import com.example.appecologica.domain.HistorialLabor;
import java.time.ZoneId;
import java.util.Date;
import lombok.Data;

public class HistorialDtos {
    @Data
    public static class HistorialResponse {
        private Integer rutaId;
        private Date fecha;
        private Integer puntosCompletados;
        private String observacion;
        private Integer userId;

        public static HistorialResponse fromEntity(HistorialLabor h) {
            HistorialResponse dto = new HistorialResponse();
            dto.setRutaId(h.getRuta() != null ? h.getRuta().getId() : null);
            dto.setFecha(h.getFecha() != null ? Date.from(h.getFecha().atZone(ZoneId.systemDefault()).toInstant()) : null);
            dto.setPuntosCompletados(h.getPuntosCompletados());
            dto.setObservacion(h.getObservacion());
            dto.setUserId(h.getEmpleado() != null ? h.getEmpleado().getId() : null);
            return dto;
        }
    }
}
