package com.example.appecologica.config;

import com.example.appecologica.domain.Empleado;
import com.example.appecologica.repository.EmpleadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seedAdmin(EmpleadoRepository empleadoRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (empleadoRepository.findByRut("11111111-1").isEmpty()) {
                Empleado admin = Empleado.builder()
                    .nombre("Admin Demo")
                    .rut("11111111-1")
                    .correo("admin@example.com")
                    .telefono("123456789")
                    .rol("ADMIN")
                    .fotoUri(null)
                    .passwordHash(passwordEncoder.encode("admin"))
                    .build();
                empleadoRepository.save(admin);
            }
        };
    }
}
