package msvc_benja.msvc_clientes.assembler;

import msvc_benja.msvc_clientes.controlador.ClienteControladorV2;
import msvc_benja.msvc_clientes.dto.ClienteDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDTO, EntityModel<ClienteDTO>> {

    @Override
    public EntityModel<ClienteDTO> toModel(ClienteDTO cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteControladorV2.class).obtenerPorId(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteControladorV2.class).listar()).withRel("clientes")
        );
    }
}
