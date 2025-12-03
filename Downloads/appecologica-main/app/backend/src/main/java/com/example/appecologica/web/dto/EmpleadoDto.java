package com.example.appecologica.web.dto;

import com.example.appecologica.domain.Empleado;
import lombok.Data;

@Data
public class EmpleadoDto {
    private Integer id;
    private String nombre;
    private String rut;
    private String correo;
    private String telefono;
    private String rol;
    private String fotoUri;

    public static EmpleadoDto fromEntity(Empleado e) {
        EmpleadoDto dto = new EmpleadoDto();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setRut(e.getRut());
        dto.setCorreo(e.getCorreo());
        dto.setTelefono(e.getTelefono());
        dto.setRol(e.getRol());
        dto.setFotoUri(e.getFotoUri());
        return dto;
    }
}
