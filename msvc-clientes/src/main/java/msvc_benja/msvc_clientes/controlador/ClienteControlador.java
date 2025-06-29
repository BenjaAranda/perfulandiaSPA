package msvc_benja.msvc_clientes.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msvc_benja.msvc_clientes.dto.ClienteDTO;
import msvc_benja.msvc_clientes.dto.ErrorDTO;
import msvc_benja.msvc_clientes.servicio.ClienteServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "CRUD para gestión de clientes")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteServicio.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<ClienteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteServicio.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    public ResponseEntity<ClienteDTO> guardar(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.status(201).body(clienteServicio.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente existente")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteServicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
