package com.appecologica.backend.dto;

import com.appecologica.backend.model.Role;

public record AuthResponse(
        String token,
        Long id,
        String nombre,
        String rut,
        Role role
) {
}
