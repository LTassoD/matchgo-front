package com.appecologica.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record VehiculoRequest(
        @NotBlank String patente,
        String modelo,
        @Min(100) int capacidadKg,
        Boolean habilitado
) {
}
