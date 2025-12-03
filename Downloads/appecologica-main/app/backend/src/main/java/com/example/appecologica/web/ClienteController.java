package com.example.appecologica.web;

import com.example.appecologica.domain.Cliente;
import com.example.appecologica.repository.ClienteRepository;
import com.example.appecologica.web.dto.ClienteDto;
import com.example.appecologica.web.dto.ClienteDtos;
import java.util.List;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public ResponseEntity<ClienteDto> crear(@Validated @RequestBody ClienteDtos.CrearClienteRequest request) {
        Cliente cliente = Cliente.builder()
            .nombreEmpresa(request.getNombreEmpresa())
            .rut(request.getRut())
            .direccion(request.getDireccion())
            .telefono(request.getTelefono())
            .correo(request.getCorreo())
            .material(request.getMaterial())
            .frecuencia(request.getFrecuencia())
            .fotoUri(request.getFotoUri())
            .build();
        cliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(ClienteDto.fromEntity(cliente));
    }

    @GetMapping
    public List<ClienteDto> listar() {
        return clienteRepository.findAll().stream().map(ClienteDto::fromEntity).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizar(@PathVariable Integer id, @RequestBody ClienteDtos.CrearClienteRequest request) {
        return clienteRepository.findById(id)
            .map(c -> {
                c.setNombreEmpresa(request.getNombreEmpresa());
                c.setRut(request.getRut());
                c.setDireccion(request.getDireccion());
                c.setTelefono(request.getTelefono());
                c.setCorreo(request.getCorreo());
                c.setMaterial(request.getMaterial());
                c.setFrecuencia(request.getFrecuencia());
                c.setFotoUri(request.getFotoUri());
                clienteRepository.save(c);
                return ResponseEntity.ok(ClienteDto.fromEntity(c));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
