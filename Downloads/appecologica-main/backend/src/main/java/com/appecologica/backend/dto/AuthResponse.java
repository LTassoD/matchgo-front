package com.appecologica.backend.dto;

import com.appecologica.backend.model.Role;

public record AuthResponse(
        String token,
        String nombre,
        Role role
) {
}
