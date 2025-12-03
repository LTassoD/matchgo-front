package com.example.appecologica.web;

import com.example.appecologica.domain.OrdenServicio;
import com.example.appecologica.domain.Punto;
import com.example.appecologica.domain.Ruta;
import com.example.appecologica.repository.OrdenServicioRepository;
import com.example.appecologica.repository.ClienteRepository;
import com.example.appecologica.repository.MaterialRepository;
import com.example.appecologica.repository.EmpleadoRepository;
import com.example.appecologica.repository.VehiculoRepository;
import com.example.appecologica.repository.PuntoRepository;
import com.example.appecologica.repository.RutaRepository;
import com.example.appecologica.web.dto.ComentarioDto;
import com.example.appecologica.web.dto.HistorialDtos;
import com.example.appecologica.web.dto.RankingDtos;
import com.example.appecologica.web.dto.OrdenDtos;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class OrdenesController {

    private final PuntoRepository puntoRepository;
    private final OrdenServicioRepository ordenServicioRepository;
    private final RutaRepository rutaRepository;
    private final com.example.appecologica.repository.HistorialLaborRepository historialLaborRepository;
    private final com.example.appecologica.repository.ComentarioRepository comentarioRepository;
    private final com.example.appecologica.repository.EmpleadoRepository empleadoRepository;
    private final ClienteRepository clienteRepository;
    private final MaterialRepository materialRepository;
    private final EmpleadoRepository empleadoRepo;
    private final VehiculoRepository vehiculoRepository;

    public OrdenesController(PuntoRepository puntoRepository,
                             OrdenServicioRepository ordenServicioRepository,
                             RutaRepository rutaRepository,
                             com.example.appecologica.repository.HistorialLaborRepository historialLaborRepository,
                             com.example.appecologica.repository.ComentarioRepository comentarioRepository,
                             com.example.appecologica.repository.EmpleadoRepository empleadoRepository,
                             ClienteRepository clienteRepository,
                             MaterialRepository materialRepository,
                             EmpleadoRepository empleadoRepo,
                             VehiculoRepository vehiculoRepository) {
        this.puntoRepository = puntoRepository;
        this.ordenServicioRepository = ordenServicioRepository;
        this.rutaRepository = rutaRepository;
        this.historialLaborRepository = historialLaborRepository;
        this.comentarioRepository = comentarioRepository;
        this.empleadoRepository = empleadoRepository;
        this.clienteRepository = clienteRepository;
        this.materialRepository = materialRepository;
        this.empleadoRepo = empleadoRepo;
        this.vehiculoRepository = vehiculoRepository;
    }

    @PostMapping("/puntos/{id}/iniciar")
    public ResponseEntity<Void> iniciar(@PathVariable Integer id) {
        Punto punto = puntoRepository.findById(id).orElse(null);
        if (punto == null) {
            return ResponseEntity.notFound().build();
        }
        punto.setEstado("EN_PROCESO");
        puntoRepository.save(punto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/puntos/{id}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable Integer id, @RequestBody com.example.appecologica.web.dto.FinalizarOrdenRequestDto request) {
        Punto punto = puntoRepository.findById(id).orElse(null);
        if (punto == null) {
            return ResponseEntity.notFound().build();
        }
        punto.setEstado("COMPLETADA");
        punto.setObservaciones(request.getObservacion());
        puntoRepository.save(punto);

        OrdenServicio os = OrdenServicio.builder()
            .punto(punto)
            .ruta(punto.getRuta())
            .estado("COMPLETADA")
            .observacion(request.getObservacion())
            .build();
        ordenServicioRepository.save(os);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rutas/{rutaId}/ordenes")
    public ResponseEntity<OrdenDtos.OrdenResponse> crearOrden(@PathVariable Integer rutaId,
                                                              @RequestBody OrdenDtos.CrearOrdenRequest request) {
        Ruta ruta = rutaRepository.findById(rutaId).orElse(null);
        if (ruta == null) {
            return ResponseEntity.notFound().build();
        }
        var cliente = request.getClienteId() != null ? clienteRepository.findById(request.getClienteId()).orElse(null) : null;
        var material = request.getMaterialId() != null ? materialRepository.findById(request.getMaterialId()).orElse(null) : null;
        var chofer = request.getChoferId() != null ? empleadoRepo.findById(request.getChoferId()).orElse(null) : null;
        var vehiculo = request.getVehiculoId() != null ? vehiculoRepository.findById(request.getVehiculoId()).orElse(null) : null;

        var orden = OrdenServicio.builder()
            .ruta(ruta)
            .cliente(cliente)
            .material(material)
            .chofer(chofer)
            .vehiculo(vehiculo)
            .direccionEnvio(request.getDireccionEnvio())
            .estado(request.getEstado() != null ? request.getEstado() : "PENDIENTE")
            .observacion(request.getObservacion())
            .creadaEn(LocalDateTime.now())
            .build();
        orden = ordenServicioRepository.save(orden);
        return ResponseEntity.ok(OrdenDtos.OrdenResponse.fromEntity(orden));
    }

    @GetMapping("/rutas/{rutaId}/ordenes")
    public ResponseEntity<List<OrdenDtos.OrdenResponse>> ordenesPorRuta(@PathVariable Integer rutaId) {
        Ruta ruta = rutaRepository.findById(rutaId).orElse(null);
        if (ruta == null) {
            return ResponseEntity.notFound().build();
        }
        var ordenes = ordenServicioRepository.findAll().stream()
            .filter(o -> o.getRuta() != null && o.getRuta().getId().equals(rutaId))
            .map(OrdenDtos.OrdenResponse::fromEntity)
            .toList();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/empleados/{id}/historial")
    public ResponseEntity<List<HistorialDtos.HistorialResponse>> historial(@PathVariable Integer id) {
        var empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }
        var list = historialLaborRepository.findByEmpleado(empleado).stream()
            .map(HistorialDtos.HistorialResponse::fromEntity)
            .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/empleados/{id}/comentarios")
    public ResponseEntity<Void> comentar(@PathVariable Integer id, @RequestBody ComentarioDto request) {
        var empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }
        var comentario = com.example.appecologica.domain.Comentario.builder()
            .empleado(empleado)
            .comentario(request.getComentario())
            .creadoEn(LocalDateTime.now())
            .build();
        comentarioRepository.save(comentario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reportes/ranking-empleados")
    public RankingDtos.RankingResponse ranking() {
        var empleados = empleadoRepository.findAll();
        // Simple: cumplimiento aleatorio fijo para demo.
        var rankingList = empleados.stream()
            .map(e -> RankingDtos.fromEmpleado(e, 0.95f))
            .toList();
        RankingDtos.RankingResponse resp = new RankingDtos.RankingResponse();
        resp.setRanking(rankingList);
        return resp;
    }
}
