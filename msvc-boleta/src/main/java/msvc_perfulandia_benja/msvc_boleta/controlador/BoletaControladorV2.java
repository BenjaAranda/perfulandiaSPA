package msvc_perfulandia_benja.msvc_boleta.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msvc_perfulandia_benja.msvc_boleta.assembler.BoletaModelAssembler;
import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.dto.ErrorDTO;
import msvc_perfulandia_benja.msvc_boleta.servicio.BoletaServicio;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/boletas")
@Tag(name = "Boletas V2", description = "Operaciones para gestionar boletas con HATEOAS")
@Validated
public class BoletaControladorV2 {

    private final BoletaServicio boletaServicio;
    private final BoletaModelAssembler boletaModelAssembler;

    public BoletaControladorV2(BoletaServicio boletaServicio, BoletaModelAssembler boletaModelAssembler) {
        this.boletaServicio = boletaServicio;
        this.boletaModelAssembler = boletaModelAssembler;
    }

    @Operation(summary = "Listar todas las boletas", description = "Devuelve una lista de boletas con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Boletas listadas exitosamente",
            content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                    schema = @Schema(implementation = BoletaDTO.class)))
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<BoletaDTO>>> listar() {
        List<EntityModel<BoletaDTO>> boletas = boletaServicio.listar().stream()
                .map(boletaModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<BoletaDTO>> collectionModel = CollectionModel.of(
                boletas,
                linkTo(methodOn(BoletaControladorV2.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Obtener boleta por ID", description = "Devuelve la boleta con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Boleta encontrada",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = BoletaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<BoletaDTO>> obtenerPorId(
            @Parameter(description = "ID de la boleta", required = true) @PathVariable Long id) {
        BoletaDTO boleta = boletaServicio.obtenerPorId(id);
        EntityModel<BoletaDTO> entityModel = boletaModelAssembler.toModel(boleta);
        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "Guardar nueva boleta", description = "Crea una nueva boleta con representación HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Boleta creada exitosamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = BoletaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Boleta a crear (clienteId e items con productoId y cantidad)",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoletaDTO.class))
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<BoletaDTO>> crear(@Valid @RequestBody BoletaDTO dto) {
        BoletaDTO boletaCreada = boletaServicio.guardar(dto);
        EntityModel<BoletaDTO> entityModel = boletaModelAssembler.toModel(boletaCreada);
        return ResponseEntity.created(
                        linkTo(methodOn(BoletaControladorV2.class).obtenerPorId(boletaCreada.getId())).toUri())
                .body(entityModel);
    }

    @Operation(summary = "Eliminar boleta por ID", description = "Elimina una boleta específica")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Boleta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la boleta a eliminar", required = true) @PathVariable Long id) {
        boletaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Generar boleta desde una venta", description = "Crea automáticamente una boleta tomando los datos de la venta y el cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Boleta generada exitosamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = BoletaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Venta o cliente no encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping("/generar/venta/{ventaId}")
    public ResponseEntity<EntityModel<BoletaDTO>> generarDesdeVenta(
            @Parameter(description = "ID de la venta", required = true)
            @PathVariable Long ventaId) {

        BoletaDTO boleta = boletaServicio.generarBoletaDesdeVenta(ventaId);
        EntityModel<BoletaDTO> entityModel = boletaModelAssembler.toModel(boleta);
        return ResponseEntity.created(
                        linkTo(methodOn(BoletaControladorV2.class).obtenerPorId(boleta.getId())).toUri())
                .body(entityModel);
    }
    @Operation(summary = "Generar boleta desde cliente y venta", description = "Genera una boleta vinculada al cliente y a los productos de la venta correspondiente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Boleta generada exitosamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = BoletaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
            @ApiResponse(responseCode = "404", description = "Cliente o venta no encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "/cliente/{clienteId}/venta/{ventaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<BoletaDTO>> generarDesdeClienteYVenta(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId,
            @Parameter(description = "ID de la venta", required = true) @PathVariable Long ventaId) {

        BoletaDTO boletaGenerada = boletaServicio.generarBoletaDesdeClienteYVenta(clienteId, ventaId);
        EntityModel<BoletaDTO> entityModel = boletaModelAssembler.toModel(boletaGenerada);

        return ResponseEntity.created(
                        linkTo(methodOn(BoletaControladorV2.class).obtenerPorId(boletaGenerada.getId())).toUri())
                .body(entityModel);
    }

}
