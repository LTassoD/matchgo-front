package com.appecologica.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MaterialRequest(
        @NotBlank String nombre,
        String descripcion,
        @NotBlank String unidadMedida,
        @Min(0) double precioReferencia
) {
}
