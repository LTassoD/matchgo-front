package com.example.appecologica.web;

import com.example.appecologica.domain.Empleado;
import com.example.appecologica.repository.EmpleadoRepository;
import com.example.appecologica.web.dto.EmpleadoDto;
import com.example.appecologica.web.dto.EmpleadoDtos;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoController(EmpleadoRepository empleadoRepository, PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<EmpleadoDto> crear(@Validated @RequestBody EmpleadoDtos.CrearEmpleadoRequest request) {
        String rawPassword = (request.getPassword() == null || request.getPassword().isBlank())
            ? request.getRut() // por defecto usa el rut como contraseña si no viene
            : request.getPassword();
        Empleado empleado = Empleado.builder()
            .nombre(request.getNombre())
            .rut(request.getRut())
            .correo(request.getCorreo())
            .telefono(request.getTelefono())
            .rol(request.getRol())
            .fotoUri(request.getFotoUri())
            .passwordHash(passwordEncoder.encode(rawPassword))
            .build();
        empleado = empleadoRepository.save(empleado);
        return ResponseEntity.ok(EmpleadoDto.fromEntity(empleado));
    }

    @GetMapping
    public List<EmpleadoDto> listar() {
        return empleadoRepository.findAll().stream().map(EmpleadoDto::fromEntity).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody EmpleadoDto dto) {
        return empleadoRepository.findById(id)
            .map(e -> {
                e.setNombre(dto.getNombre());
                e.setRut(dto.getRut());
                e.setCorreo(dto.getCorreo());
                e.setTelefono(dto.getTelefono());
                e.setRol(dto.getRol());
                e.setFotoUri(dto.getFotoUri());
                empleadoRepository.save(e);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!empleadoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empleadoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
