package com.example.msvc.inventario.msvc_inventario.controlador;

import com.example.msvc.inventario.msvc_inventario.dto.ProductoDTO;
import com.example.msvc.inventario.msvc_inventario.dto.ErrorDTO;
import com.example.msvc.inventario.msvc_inventario.servicio.ProductoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Validated
@Tag(name = "Productos", description = "Operaciones CRUD de productos del inventario")
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    public ProductoControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    @GetMapping
    @Operation(summary = "Obtiene todos los productos", description = "Devuelve una lista con todos los productos del inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    public ResponseEntity<List<ProductoDTO>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productoServicio.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un producto por ID", description = "A través del ID se obtiene un producto del inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado con el ID indicado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID único del producto", required = true)
    })
    public ResponseEntity<ProductoDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productoServicio.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guarda un nuevo producto", description = "Permite registrar un nuevo producto en el inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "El producto ya existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Producto a registrar",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductoDTO.class)
            )
    )
    public ResponseEntity<ProductoDTO> create(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO nuevo = this.productoServicio.guardar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un producto", description = "Permite modificar los datos de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID único del producto", required = true)
    })
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        ProductoDTO actualizado = this.productoServicio.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un producto", description = "Permite eliminar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID único del producto", required = true)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.productoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
