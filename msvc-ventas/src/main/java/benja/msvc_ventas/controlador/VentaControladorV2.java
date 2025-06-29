package benja.msvc_ventas.controlador;

import benja.msvc_ventas.dto.VentaDTO;
import benja.msvc_ventas.dto.ErrorDTO;
import benja.msvc_ventas.hateoas.VentaModelAssembler;
import benja.msvc_ventas.servicio.VentaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/ventas")
@Tag(name = "Ventas V2", description = "Operaciones CRUD de ventas con HATEOAS")
public class VentaControladorV2 {

    private final VentaServicio servicio;
    private final VentaModelAssembler assembler;

    public VentaControladorV2(VentaServicio servicio, VentaModelAssembler assembler) {
        this.servicio = servicio;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Obtiene todas las ventas", description = "Devuelve una lista con enlaces HATEOAS")
    @ApiResponse(
            responseCode = "200",
            description = "Operación exitosa",
            content = @Content(mediaType = "application/hal+json", schema = @Schema(implementation = VentaDTO.class))
    )
    public ResponseEntity<CollectionModel<EntityModel<VentaDTO>>> listar() {
        List<EntityModel<VentaDTO>> ventas = servicio.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(ventas, linkTo(methodOn(VentaControladorV2.class).listar()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una venta", description = "Devuelve una venta con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Venta encontrada",
                    content = @Content(mediaType = "application/hal+json", schema = @Schema(implementation = VentaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
            )
    })
    public ResponseEntity<EntityModel<VentaDTO>> obtenerPorId(@PathVariable Long id) {
        VentaDTO venta = servicio.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(venta));
    }

    @PostMapping
    @Operation(summary = "Guarda una venta", description = "Crea una venta con body y retorna con enlaces")
    @ApiResponse(
            responseCode = "201",
            description = "Venta creada",
            content = @Content(mediaType = "application/hal+json", schema = @Schema(implementation = VentaDTO.class))
    )
    public ResponseEntity<EntityModel<VentaDTO>> guardar(@Valid @RequestBody VentaDTO dto) {
        VentaDTO saved = servicio.guardar(dto);
        return ResponseEntity
                .created(linkTo(methodOn(VentaControladorV2.class).obtenerPorId(saved.getId())).toUri())
                .body(assembler.toModel(saved));
    }
}
