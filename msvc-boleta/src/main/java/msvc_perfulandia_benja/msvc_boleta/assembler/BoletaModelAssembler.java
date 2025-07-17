package msvc_perfulandia_benja.msvc_boleta.assembler;

import msvc_perfulandia_benja.msvc_boleta.controlador.BoletaControladorV2;
import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BoletaModelAssembler extends RepresentationModelAssemblerSupport<BoletaDTO, EntityModel<BoletaDTO>> {

    public BoletaModelAssembler() {
        super(BoletaControladorV2.class, (Class<EntityModel<BoletaDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<BoletaDTO> toModel(BoletaDTO boletaDTO) {
        return EntityModel.of(boletaDTO,
                linkTo(methodOn(BoletaControladorV2.class).obtenerPorId(boletaDTO.getId())).withSelfRel(),
                linkTo(methodOn(BoletaControladorV2.class).listar()).withRel("boletas")
        );
    }
}
