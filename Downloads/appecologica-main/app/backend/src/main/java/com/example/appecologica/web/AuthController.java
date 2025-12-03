package com.example.appecologica.web;

import com.example.appecologica.domain.Empleado;
import com.example.appecologica.repository.EmpleadoRepository;
import com.example.appecologica.security.JwtUtil;
import com.example.appecologica.web.dto.AuthDtos;
import com.example.appecologica.web.dto.EmpleadoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(EmpleadoRepository empleadoRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.LoginResponse> login(@Validated @RequestBody AuthDtos.LoginRequest request) {
        Empleado empleado = empleadoRepository.findByRut(request.getRut()).orElse(null);
        if (empleado == null || empleado.getPasswordHash() == null ||
            !passwordEncoder.matches(request.getPassword(), empleado.getPasswordHash())) {
            return ResponseEntity.status(401).build();
        }
        String token = jwtUtil.createToken(empleado.getRut());
        return ResponseEntity.ok(new AuthDtos.LoginResponse(EmpleadoDto.fromEntity(empleado), token));
    }
}
