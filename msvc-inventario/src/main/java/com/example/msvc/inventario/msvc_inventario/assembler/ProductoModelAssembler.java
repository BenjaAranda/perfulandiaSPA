package com.example.msvc.inventario.msvc_inventario.assembler;

import com.example.msvc.inventario.msvc_inventario.controlador.ProductoControladorV2;
import com.example.msvc.inventario.msvc_inventario.dto.ProductoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>> {

    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(ProductoControladorV2.class).obtenerPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControladorV2.class).listar()).withRel("productos")
        );
    }
}
