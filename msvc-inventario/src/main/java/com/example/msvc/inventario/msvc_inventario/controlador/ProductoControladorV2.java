package com.example.msvc.inventario.msvc_inventario.controlador;

import com.example.msvc.inventario.msvc_inventario.dto.ProductoDTO;
import com.example.msvc.inventario.msvc_inventario.dto.ErrorDTO;
import com.example.msvc.inventario.msvc_inventario.servicio.ProductoServicio;
import com.example.msvc.inventario.msvc_inventario.assembler.ProductoModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v2/productos")
@Validated
@Tag(name = "Productos V2", description = "Operaciones CRUD HATEOAS de productos (Perfumes)")
public class ProductoControladorV2 {

    private final ProductoServicio servicio;
    private final ProductoModelAssembler assembler;

    public ProductoControladorV2(ProductoServicio servicio, ProductoModelAssembler assembler) {
        this.servicio = servicio;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Lista todos los productos", description = "Devuelve todos los perfumes disponibles en inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = ProductoDTO.class)))
    })
    public ResponseEntity<CollectionModel<EntityModel<ProductoDTO>>> listar() {
        List<EntityModel<ProductoDTO>> productos = servicio.listar()
                .stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(productos,
                        linkTo(methodOn(ProductoControladorV2.class).listar()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un producto por ID", description = "Devuelve un perfume específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters({
            @Parameter(name = "id", description = "ID único del producto", required = true)
    })
    public ResponseEntity<EntityModel<ProductoDTO>> obtenerPorId(@PathVariable Long id) {
        ProductoDTO producto = servicio.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo producto", description = "Registra un nuevo perfume")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo perfume",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductoDTO.class))
    )
    public ResponseEntity<EntityModel<ProductoDTO>> crear(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO creado = servicio.guardar(dto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControladorV2.class).obtenerPorId(creado.getId())).toUri())
                .body(assembler.toModel(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un producto", description = "Modifica los datos de un perfume")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<EntityModel<ProductoDTO>> actualizar(@PathVariable Long id,
                                                               @Valid @RequestBody ProductoDTO dto) {
        ProductoDTO actualizado = servicio.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un producto", description = "Elimina un perfume por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminar-todos")
    @Operation(summary = "Elimina todos los productos", description = "Borra todos los perfumes del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Productos eliminados exitosamente")
    })
    public ResponseEntity<Void> eliminarTodos() {
        servicio.eliminarTodos();
        return ResponseEntity.noContent().build();
    }
}
