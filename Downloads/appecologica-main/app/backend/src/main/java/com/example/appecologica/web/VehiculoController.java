package com.example.appecologica.web;

import com.example.appecologica.domain.Vehiculo;
import com.example.appecologica.repository.VehiculoRepository;
import com.example.appecologica.web.dto.VehiculoDtos;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoController(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @PostMapping
    public VehiculoDtos.VehiculoResponse crear(@RequestBody VehiculoDtos.CrearVehiculoRequest request) {
        Vehiculo vehiculo = Vehiculo.builder()
            .marca(request.getMarca())
            .modelo(request.getModelo())
            .patente(request.getPatente())
            .anio(request.getAnio())
            .capacidad(request.getCapacidad())
            .build();
        return VehiculoDtos.VehiculoResponse.fromEntity(vehiculoRepository.save(vehiculo));
    }

    @GetMapping
    public List<VehiculoDtos.VehiculoResponse> listar() {
        return vehiculoRepository.findAll().stream().map(VehiculoDtos.VehiculoResponse::fromEntity).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody VehiculoDtos.CrearVehiculoRequest request) {
        return vehiculoRepository.findById(id)
            .map(v -> {
                v.setMarca(request.getMarca());
                v.setModelo(request.getModelo());
                v.setPatente(request.getPatente());
                v.setAnio(request.getAnio());
                v.setCapacidad(request.getCapacidad());
                vehiculoRepository.save(v);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!vehiculoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vehiculoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
