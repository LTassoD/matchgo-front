package com.example.appecologica.web.dto;

import com.example.appecologica.domain.Cliente;
import lombok.Data;

@Data
public class ClienteDto {
    private Integer idCliente;
    private String nombreEmpresa;
    private String rut;
    private String direccion;
    private String telefono;
    private String correo;
    private String material;
    private String frecuencia;
    private String fotoUri;

    public static ClienteDto fromEntity(Cliente c) {
        ClienteDto dto = new ClienteDto();
        dto.setIdCliente(c.getId());
        dto.setNombreEmpresa(c.getNombreEmpresa());
        dto.setRut(c.getRut());
        dto.setDireccion(c.getDireccion());
        dto.setTelefono(c.getTelefono());
        dto.setCorreo(c.getCorreo());
        dto.setMaterial(c.getMaterial());
        dto.setFrecuencia(c.getFrecuencia());
        dto.setFotoUri(c.getFotoUri());
        return dto;
    }
}
