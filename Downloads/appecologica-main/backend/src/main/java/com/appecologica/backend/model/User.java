package com.appecologica.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String rut;

    @NotBlank
    @Size(min = 60) // hash length to prevent storing texto plano
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default

    private boolean enabled = true;
}
