package benja.msvc_ventas.hateoas;

import benja.msvc_ventas.controlador.VentaControladorV2;
import benja.msvc_ventas.dto.VentaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<VentaDTO, EntityModel<VentaDTO>> {

    @Override
    public EntityModel<VentaDTO> toModel(VentaDTO venta) {
        return EntityModel.of(venta,
                linkTo(methodOn(VentaControladorV2.class).obtenerPorId(venta.getId())).withSelfRel(),
                linkTo(methodOn(VentaControladorV2.class).listar()).withRel("ventas"));
    }
}
