package com.appecologica.backend.dto;

import com.appecologica.backend.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String nombre,
        @Email @NotBlank String email,
        @NotBlank String rut,
        @Size(min = 6) String password,
        Role role
) {
}
