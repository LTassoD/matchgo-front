package com.appecologica.backend.service;

import com.appecologica.backend.dto.ClienteRequest;
import com.appecologica.backend.model.Cliente;
import com.appecologica.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    @Transactional
    public Cliente create(ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .nombre(request.nombre())
                .direccion(request.direccion())
                .contactoEmail(request.contactoEmail())
                .contactoTelefono(request.contactoTelefono())
                .build();
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente update(Long id, ClienteRequest request) {
        Cliente cliente = findById(id);
        cliente.setNombre(request.nombre());
        cliente.setDireccion(request.direccion());
        cliente.setContactoEmail(request.contactoEmail());
        cliente.setContactoTelefono(request.contactoTelefono());
        return clienteRepository.save(cliente);
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}
