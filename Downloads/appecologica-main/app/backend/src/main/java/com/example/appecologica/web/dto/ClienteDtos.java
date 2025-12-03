package com.example.appecologica.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class ClienteDtos {
    @Data
    public static class CrearClienteRequest {
        @NotBlank
        private String nombreEmpresa;
        @NotBlank
        private String rut;
        @NotBlank
        private String direccion;
        @NotBlank
        private String telefono;
        @NotBlank
        private String correo;
        private String material;
        private String frecuencia;
        private String fotoUri;
    }
}
