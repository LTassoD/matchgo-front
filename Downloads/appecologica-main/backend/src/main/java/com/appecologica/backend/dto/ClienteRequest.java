package com.appecologica.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank String nombre,
        @NotBlank String direccion,
        String contactoEmail,
        String contactoTelefono
) {
}
