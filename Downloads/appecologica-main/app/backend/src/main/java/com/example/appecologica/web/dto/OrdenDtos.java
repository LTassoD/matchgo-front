package com.example.appecologica.web.dto;

import com.example.appecologica.domain.OrdenServicio;
import lombok.Data;

public class OrdenDtos {

    @Data
    public static class CrearOrdenRequest {
        private Integer clienteId;
        private Integer materialId;
        private Integer choferId;
        private Integer vehiculoId;
        private String direccionEnvio;
        private String estado;
        private String observacion;
    }

    @Data
    public static class OrdenResponse {
        private Integer id;
        private Integer rutaId;
        private Integer clienteId;
        private Integer materialId;
        private Integer choferId;
        private Integer vehiculoId;
        private String direccionEnvio;
        private String estado;
        private String observacion;
        private String creadaEn;

        public static OrdenResponse fromEntity(OrdenServicio os) {
            OrdenResponse dto = new OrdenResponse();
            dto.setId(os.getId());
            dto.setRutaId(os.getRuta() != null ? os.getRuta().getId() : null);
            dto.setClienteId(os.getCliente() != null ? os.getCliente().getId() : null);
            dto.setMaterialId(os.getMaterial() != null ? os.getMaterial().getId() : null);
            dto.setChoferId(os.getChofer() != null ? os.getChofer().getId() : null);
            dto.setVehiculoId(os.getVehiculo() != null ? os.getVehiculo().getId() : null);
            dto.setDireccionEnvio(os.getDireccionEnvio());
            dto.setEstado(os.getEstado());
            dto.setObservacion(os.getObservacion());
            dto.setCreadaEn(os.getCreadaEn() != null ? os.getCreadaEn().toString() : null);
            return dto;
        }
    }
}
