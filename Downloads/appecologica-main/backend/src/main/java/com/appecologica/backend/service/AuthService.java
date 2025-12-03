package com.appecologica.backend.service;

import com.appecologica.backend.dto.AuthResponse;
import com.appecologica.backend.dto.LoginRequest;
import com.appecologica.backend.dto.RegisterRequest;
import com.appecologica.backend.model.Role;
import com.appecologica.backend.model.User;
import com.appecologica.backend.repository.UserRepository;
import com.appecologica.backend.security.JwtService;
import com.appecologica.backend.security.SecurityUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        Role role = request.role() != null ? request.role() : Role.USER;

        String normalizedEmail = request.email().trim().toLowerCase();
        userRepository.findByEmail(normalizedEmail).ifPresent(user -> {
            throw new IllegalArgumentException("El correo ya está registrado");
        });

        String sanitizedRut = request.rut().trim().replace(".", "").toUpperCase();
        userRepository.findByRut(sanitizedRut).ifPresent(user -> {
            throw new IllegalArgumentException("El RUT ya está registrado");
        });

        User user = User.builder()
                .nombre(request.nombre())
                .email(normalizedEmail)
                .rut(sanitizedRut)
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .enabled(true)
                .build();

        userRepository.save(user);
        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        String identifier = request.identifier().trim();
        String rutIdentifier = identifier.replace(".", "").toUpperCase();

        User user = userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByRut(rutIdentifier))
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales inválidas"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), request.password())
        );

        return buildAuthResponse(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        SecurityUser securityUser = new SecurityUser(user);
        String token = jwtService.generateToken(
                securityUser,
                Map.of(
                        "role", user.getRole().name(),
                        "nombre", user.getNombre(),
                        "userId", user.getId()
                )
        );
        return new AuthResponse(token, user.getId(), user.getNombre(), user.getRut(), user.getRole());
    }
}
