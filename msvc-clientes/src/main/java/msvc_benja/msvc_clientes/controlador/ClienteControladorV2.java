package msvc_benja.msvc_clientes.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import msvc_benja.msvc_clientes.dto.ClienteDTO;
import msvc_benja.msvc_clientes.dto.ErrorDTO;
import msvc_benja.msvc_clientes.servicio.ClienteServicio;
import msvc_benja.msvc_clientes.assembler.ClienteModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/clientes")
@Validated
@Tag(name = "Clientes V2", description = "CRUD para gestión de clientes con HATEOAS")
public class ClienteControladorV2 {

    private final ClienteServicio clienteServicio;
    private final ClienteModelAssembler assembler;

    public ClienteControladorV2(ClienteServicio clienteServicio, ClienteModelAssembler assembler) {
        this.clienteServicio = clienteServicio;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de clientes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteDTO.class)
                    )
            )
    })
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> listar() {
        List<EntityModel<ClienteDTO>> clientes = clienteServicio.listar()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<ClienteDTO>> collection = CollectionModel.of(
                clientes,
                linkTo(methodOn(ClienteControladorV2.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @Parameters({
            @Parameter(name = "id", description = "ID del cliente", required = true)
    })
    public ResponseEntity<EntityModel<ClienteDTO>> obtenerPorId(@PathVariable Long id) {
        ClienteDTO dto = clienteServicio.obtenerPorId(id);
        EntityModel<ClienteDTO> model = assembler.toModel(dto);
        return ResponseEntity.ok(model);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteDTO.class)
                    )
            )
    })
    public ResponseEntity<EntityModel<ClienteDTO>> guardar(@Valid @RequestBody ClienteDTO dto) {
        ClienteDTO nuevo = clienteServicio.guardar(dto);
        EntityModel<ClienteDTO> model = assembler.toModel(nuevo);
        return ResponseEntity
                .created(linkTo(methodOn(ClienteControladorV2.class).obtenerPorId(nuevo.getId())).toUri())
                .body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar cliente existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @Parameters({
            @Parameter(name = "id", description = "ID del cliente", required = true)
    })
    public ResponseEntity<EntityModel<ClienteDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        ClienteDTO actualizado = clienteServicio.actualizar(id, dto);
        EntityModel<ClienteDTO> model = assembler.toModel(actualizado);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
