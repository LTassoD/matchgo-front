package com.example.appecologica.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class EmpleadoDtos {
    @Data
    public static class CrearEmpleadoRequest {
        @NotBlank
        private String nombre;
        @NotBlank
        private String rut;
        @NotBlank
        private String correo;
        @NotBlank
        private String telefono;
        @NotBlank
        private String rol;
        private String fotoUri;
        // Puede venir nulo desde el cliente; se asignará un password por defecto.
        private String password;
    }
}
