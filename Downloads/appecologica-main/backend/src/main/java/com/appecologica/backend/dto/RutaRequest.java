package com.appecologica.backend.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record RutaRequest(
        @NotNull LocalDate fecha,
        @NotNull Long choferId,
        @NotNull Long vehiculoId,
        List<Long> puntoIds
) {
}
