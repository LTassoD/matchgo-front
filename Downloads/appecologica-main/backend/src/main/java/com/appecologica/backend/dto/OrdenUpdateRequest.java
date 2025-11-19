package com.appecologica.backend.dto;

import com.appecologica.backend.model.EstadoOrden;

public record OrdenUpdateRequest(
        EstadoOrden estado,
        String observacion
) {
}
