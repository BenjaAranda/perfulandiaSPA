package msvc_perfulandia_benja.msvc_boleta.assembler;

import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import msvc_perfulandia_benja.msvc_boleta.controlador.BoletaControladorV2;

@Component
public class BoletaModelAssembler implements RepresentationModelAssembler<BoletaDTO, EntityModel<BoletaDTO>> {

    @Override
    public EntityModel<BoletaDTO> toModel(BoletaDTO boletaDTO) {
        return EntityModel.of(
                boletaDTO,
                linkTo(methodOn(BoletaControladorV2.class).obtenerPorId(boletaDTO.getId())).withSelfRel(),
                linkTo(methodOn(BoletaControladorV2.class).listar()).withRel("boletas")
        );
    }
}
