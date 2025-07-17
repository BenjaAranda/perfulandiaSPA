package msvc_perfulandia_benja.msvc_boleta.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.dto.CrearBoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.dto.ErrorDTO;
import msvc_perfulandia_benja.msvc_boleta.servicio.BoletaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "Boletas", description = "Operaciones para generar y consultar boletas")
@Validated
public class BoletaControlador {

    private final BoletaServicio boletaServicio;

    public BoletaControlador(BoletaServicio boletaServicio) {
        this.boletaServicio = boletaServicio;
    }

    @Operation(summary = "Crear boleta desde cliente y venta", description = "Crea boleta automática con datos obtenidos desde venta y cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Boleta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o recursos no encontrados")
    })
    @PostMapping("/generar")
    public ResponseEntity<BoletaDTO> crearBoleta(@Valid @RequestBody CrearBoletaDTO crearBoletaDTO) {
        BoletaDTO boletaCreada = boletaServicio.generarBoletaDesdeClienteYVenta(crearBoletaDTO.getClienteId(), crearBoletaDTO.getVentaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(boletaCreada);
    }

    @Operation(summary = "Listar todas las boletas", description = "Devuelve una lista con todas las boletas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boletas listadas exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BoletaDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<BoletaDTO>> listar() {
        return ResponseEntity.ok(boletaServicio.listar());
    }

    @Operation(summary = "Obtener boleta por ID", description = "Devuelve una boleta específica según el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleta encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BoletaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID único de la boleta", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BoletaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(boletaServicio.obtenerPorId(id));
    }

    @Operation(summary = "Guardar nueva boleta", description = "Crea una nueva boleta con los datos enviados (precios se obtienen de ventas, nombre de cliente se obtiene por ID)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Boleta creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BoletaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Boleta a crear",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoletaDTO.class))
    )
    @PostMapping
    public ResponseEntity<BoletaDTO> crear(@Valid @RequestBody BoletaDTO dto) {
        BoletaDTO boletaCreada = boletaServicio.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(boletaCreada);
    }

    @Operation(summary = "Eliminar boleta por ID", description = "Elimina una boleta según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Boleta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID único de la boleta", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boletaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Generar boleta desde cliente y venta", description = "Genera una boleta vinculada al cliente y a los productos de la venta correspondiente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Boleta generada exitosamente", content = @Content(schema = @Schema(implementation = BoletaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
            @ApiResponse(responseCode = "404", description = "Cliente o venta no encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/cliente/{clienteId}/venta/{ventaId}")
    public ResponseEntity<BoletaDTO> generarDesdeClienteYVenta(@PathVariable Long clienteId, @PathVariable Long ventaId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boletaServicio.generarBoletaDesdeClienteYVenta(clienteId, ventaId));
    }



}
