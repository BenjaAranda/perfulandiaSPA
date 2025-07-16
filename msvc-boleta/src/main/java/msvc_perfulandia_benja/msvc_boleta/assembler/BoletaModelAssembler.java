package msvc_perfulandia_benja.msvc_boleta.assembler;

import msvc_perfulandia_benja.msvc_boleta.controlador.BoletaControladorV2;
import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BoletaModelAssembler implements RepresentationModelAssembler<BoletaDTO, EntityModel<BoletaDTO>> {

    @Override
    public EntityModel<BoletaDTO> toModel(BoletaDTO boleta) {
        return EntityModel.of(boleta,
                linkTo(methodOn(BoletaControladorV2.class).obtenerPorId(boleta.getId())).withSelfRel(),
                linkTo(methodOn(BoletaControladorV2.class).listar()).withRel("boletas")
        );
    }
}
