package benja.msvc_ventas.controlador;

import benja.msvc_ventas.dto.VentaDTO;
import benja.msvc_ventas.dto.ErrorDTO;
import benja.msvc_ventas.servicio.VentaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Operaciones CRUD de ventas")
public class VentaControlador {

    private final VentaServicio servicio;

    public VentaControlador(VentaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    @Operation(summary = "Obtiene todas las ventas", description = "Devuelve una lista de ventas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    public ResponseEntity<List<VentaDTO>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una venta", description = "Devuelve una venta por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
            )
    })
    @Parameters({
            @Parameter(name = "id", description = "ID único de la venta", required = true)
    })
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guarda una venta", description = "Crea una nueva venta con datos del body")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venta creada correctamente")
    })
    public ResponseEntity<VentaDTO> guardar(@Valid @RequestBody VentaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una venta", description = "Modifica una venta existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta actualizada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<VentaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody VentaDTO dto) {
        return ResponseEntity.ok(servicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una venta", description = "Elimina una venta por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venta eliminada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
