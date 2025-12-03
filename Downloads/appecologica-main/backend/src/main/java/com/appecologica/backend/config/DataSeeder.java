package com.appecologica.backend.config;

import com.appecologica.backend.dto.RutaRequest;
import com.appecologica.backend.model.Cliente;
import com.appecologica.backend.model.Material;
import com.appecologica.backend.model.Role;
import com.appecologica.backend.model.User;
import com.appecologica.backend.model.Vehiculo;
import com.appecologica.backend.repository.ClienteRepository;
import com.appecologica.backend.repository.MaterialRepository;
import com.appecologica.backend.repository.PuntoRecoleccionRepository;
import com.appecologica.backend.repository.RutaRepository;
import com.appecologica.backend.repository.UserRepository;
import com.appecologica.backend.repository.VehiculoRepository;
import com.appecologica.backend.security.SecurityUser;
import com.appecologica.backend.service.RutaService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private final MaterialRepository materialRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PuntoRecoleccionRepository puntoRepository;
    private final RutaRepository rutaRepository;
    private final RutaService rutaService;

    public DataSeeder(UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      ClienteRepository clienteRepository,
                      MaterialRepository materialRepository,
                      VehiculoRepository vehiculoRepository,
                      PuntoRecoleccionRepository puntoRepository,
                      RutaRepository rutaRepository,
                      RutaService rutaService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
        this.materialRepository = materialRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.puntoRepository = puntoRepository;
        this.rutaRepository = rutaRepository;
        this.rutaService = rutaService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .nombre("Admin General")
                    .email("admin@appecologica.com")
                    .rut("11111111-1")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();
            User chofer = User.builder()
                    .nombre("Chofer Demo")
                    .email("chofer@appecologica.com")
                    .rut("22222222-2")
                    .password(passwordEncoder.encode("chofer123"))
                    .role(Role.CHOFER)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            userRepository.save(chofer);
        }

        if (materialRepository.count() == 0) {
            materialRepository.save(Material.builder()
                    .nombre("Cartón")
                    .descripcion("Cartón prensado en fardos de 120kg")
                    .unidadMedida("Kg")
                    .precioReferencia(120.0)
                    .build());

            materialRepository.save(Material.builder()
                    .nombre("Vidrio")
                    .descripcion("Botellas de vidrio limpias")
                    .unidadMedida("Kg")
                    .precioReferencia(200.0)
                    .build());
        }

        if (clienteRepository.count() == 0) {
            Cliente universidad = clienteRepository.save(Cliente.builder()
                    .nombre("Universidad Ecológica")
                    .direccion("Av. Principal 123")
                    .contactoEmail("contacto@uniecologica.cl")
                    .contactoTelefono("+56 9 1111 2222")
                    .build());

            Cliente supermercado = clienteRepository.save(Cliente.builder()
                    .nombre("Supermercado Verde")
                    .direccion("Mall Plaza Local 34")
                    .contactoEmail("admin@verde.cl")
                    .contactoTelefono("+56 9 3333 4444")
                    .build());

            puntoRepository.saveAll(List.of(
                    com.appecologica.backend.model.PuntoRecoleccion.builder()
                            .cliente(universidad)
                            .direccion("Campus Norte")
                            .material("Cartón")
                            .contenedor("240L")
                            .observaciones("Retirar antes de las 10am")
                            .build(),
                    com.appecologica.backend.model.PuntoRecoleccion.builder()
                            .cliente(universidad)
                            .direccion("Campus Sur")
                            .material("Vidrio")
                            .contenedor("Contenedor fijo")
                            .observaciones("Necesita firma del administrador")
                            .build(),
                    com.appecologica.backend.model.PuntoRecoleccion.builder()
                            .cliente(supermercado)
                            .direccion("Carga de estacionamiento")
                            .material("Orgánico")
                            .contenedor("120L")
                            .observaciones("Coordinar con seguridad")
                            .build()
            ));
        }

        if (vehiculoRepository.count() == 0) {
            vehiculoRepository.save(Vehiculo.builder()
                    .patente("AA-AA-11")
                    .modelo("Camión Eléctrico")
                    .capacidadKg(1000)
                    .habilitado(true)
                    .build());
        }

        if (rutaRepository.count() == 0) {
            User chofer = userRepository.findByEmail("chofer@appecologica.com")
                    .orElseThrow();
            Long choferId = chofer.getId();
            Long vehiculoId = vehiculoRepository.findAll().stream()
                    .findFirst()
                    .map(Vehiculo::getId)
                    .orElseThrow();
            List<Long> puntos = puntoRepository.findAll()
                    .stream()
                    .map(com.appecologica.backend.model.PuntoRecoleccion::getId)
                    .collect(Collectors.toList());

            rutaService.create(new RutaRequest(LocalDate.now(), choferId, vehiculoId, puntos, null));
        }
    }
}
