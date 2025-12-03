package com.example.appecologica.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class AuthDtos {

    @Data
    public static class LoginRequest {
        @NotBlank
        private String rut;
        @NotBlank
        private String password;
    }

    @Data
    public static class LoginResponse {
        private EmpleadoDto empleado;
        private String token;

        public LoginResponse(EmpleadoDto empleado, String token) {
            this.empleado = empleado;
            this.token = token;
        }
    }
}
