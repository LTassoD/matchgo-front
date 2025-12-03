package com.example.appecologica.web;

import com.example.appecologica.domain.Empleado;
import com.example.appecologica.domain.Punto;
import com.example.appecologica.domain.Ruta;
import com.example.appecologica.repository.ClienteRepository;
import com.example.appecologica.repository.EmpleadoRepository;
import com.example.appecologica.repository.PuntoRepository;
import com.example.appecologica.repository.RutaRepository;
import com.example.appecologica.repository.VehiculoRepository;
import com.example.appecologica.web.dto.AsignacionDto;
import com.example.appecologica.web.dto.RutaDtos;
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
@RequestMapping
public class RutaController {

    private final RutaRepository rutaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PuntoRepository puntoRepository;
    private final ClienteRepository clienteRepository;

    public RutaController(RutaRepository rutaRepository,
                          EmpleadoRepository empleadoRepository,
                          VehiculoRepository vehiculoRepository,
                          PuntoRepository puntoRepository,
                          ClienteRepository clienteRepository) {
        this.rutaRepository = rutaRepository;
        this.empleadoRepository = empleadoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.puntoRepository = puntoRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/rutas")
    public List<RutaDtos.RutaResponse> listarRutas() {
        return rutaRepository.findAll().stream().map(RutaDtos.RutaResponse::fromEntity).toList();
    }

    @PostMapping("/rutas")
    public RutaDtos.RutaResponse crearRuta(@RequestBody RutaDtos.CrearRutaRequest request) {
        var chofer = request.getIdChoferAsignado() != null
            ? empleadoRepository.findById(request.getIdChoferAsignado()).orElse(null)
            : null;
        var vehiculo = request.getIdVehiculo() != null
            ? vehiculoRepository.findById(request.getIdVehiculo()).orElse(null)
            : null;
        Ruta ruta = Ruta.builder()
            .nombre(request.getNombre())
            .fecha(RutaDtos.parseFecha(request.getFecha()))
            .estado(request.getEstado())
            .chofer(chofer)
            .vehiculo(vehiculo)
            .build();
        ruta = rutaRepository.save(ruta);
        return RutaDtos.RutaResponse.fromEntity(ruta);
    }

    @PutMapping("/rutas/{id}")
    public ResponseEntity<RutaDtos.RutaResponse> actualizarRuta(@PathVariable Integer id, @RequestBody RutaDtos.CrearRutaRequest request) {
        return rutaRepository.findById(id)
            .map(r -> {
                r.setNombre(request.getNombre());
                r.setEstado(request.getEstado());
                r.setFecha(RutaDtos.parseFecha(request.getFecha()));
                r.setChofer(request.getIdChoferAsignado() != null ? empleadoRepository.findById(request.getIdChoferAsignado()).orElse(null) : null);
                r.setVehiculo(request.getIdVehiculo() != null ? vehiculoRepository.findById(request.getIdVehiculo()).orElse(null) : null);
                rutaRepository.save(r);
                return ResponseEntity.ok(RutaDtos.RutaResponse.fromEntity(r));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/rutas/{id}")
    public ResponseEntity<Void> eliminarRuta(@PathVariable Integer id) {
        if (!rutaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rutaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rutas/chofer/{id}")
    public List<RutaDtos.RutaResponse> rutasPorChofer(@PathVariable Integer id) {
        Empleado chofer = empleadoRepository.findById(id).orElse(null);
        return rutaRepository.findByChofer(chofer).stream().map(RutaDtos.RutaResponse::fromEntity).toList();
    }

    @GetMapping("/rutas/{id}")
    public ResponseEntity<RutaDtos.RutaResponse> rutaPorId(@PathVariable Integer id) {
        return rutaRepository.findById(id)
            .map(r -> ResponseEntity.ok(RutaDtos.RutaResponse.fromEntity(r)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/clientes/{id}/asignar")
    public ResponseEntity<Void> asignarRuta(@PathVariable Integer id, @RequestBody AsignacionDto request) {
        // Simplificado: se usa Ruta y Punto ficticios; adaptar a tu modelo real.
        var cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        var chofer = empleadoRepository.findById(request.getIdChofer()).orElse(null);
        var vehiculo = vehiculoRepository.findById(request.getIdVehiculo()).orElse(null);
        Ruta ruta = Ruta.builder()
            .nombre("Asignacion cliente " + cliente.getNombreEmpresa())
            .estado("PENDIENTE")
            .chofer(chofer)
            .vehiculo(vehiculo)
            .build();
        ruta = rutaRepository.save(ruta);
        Punto punto = Punto.builder()
            .cliente(cliente)
            .ruta(ruta)
            .contenedor("DESCONOCIDO")
            .estado("PENDIENTE")
            .observaciones(null)
            .build();
        puntoRepository.save(punto);
        return ResponseEntity.ok().build();
    }
}
