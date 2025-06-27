package msvc_perfulandia_benja.msvc_boleta.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.servicio.BoletaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boletas")
@Tag(name = "Boletas", description = "Operaciones para generar y consultar boletas")
public class BoletaControlador {

    private final BoletaServicio boletaServicio;

    public BoletaControlador(BoletaServicio boletaServicio) {
        this.boletaServicio = boletaServicio;
    }

    @Operation(summary = "Listar todas las boletas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boletas listadas exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<BoletaDTO>> listar() {
        return ResponseEntity.ok(boletaServicio.listar());
    }

    @Operation(summary = "Obtener boleta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleta encontrada"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BoletaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(boletaServicio.obtenerPorId(id));
    }

    @Operation(summary = "Guardar nueva boleta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<BoletaDTO> crear(@Valid @RequestBody(description = "Boleta a crear", required = true)
                                           BoletaDTO dto) {
        return ResponseEntity.ok(boletaServicio.guardar(dto));
    }

    @Operation(summary = "Eliminar boleta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Boleta eliminada"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boletaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
