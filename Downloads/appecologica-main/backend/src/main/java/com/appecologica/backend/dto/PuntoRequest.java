package com.appecologica.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PuntoRequest(
        @NotNull Long clienteId,
        @NotBlank String direccion,
        @NotBlank String material,
        String contenedor,
        String observaciones
) {
}
