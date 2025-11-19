package com.appecologica.backend.dto;

import com.appecologica.backend.model.Role;

public record UserResponse(
        Long id,
        String nombre,
        String email,
        String rut,
        Role role
) {
}
