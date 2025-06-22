package msvc_benja.msvc_clientes.servicio;

import msvc_benja.msvc_clientes.dto.ClienteDTO;
import msvc_benja.msvc_clientes.excepciones.RecursoNoEncontradoException;
import msvc_benja.msvc_clientes.modelo.Cliente;
import msvc_benja.msvc_clientes.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicioImpl implements ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteServicioImpl(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public List<ClienteDTO> listar() {
        return clienteRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ClienteDTO obtenerPorId(Long id) {
        Cliente cliente = clienteRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id));
        return toDTO(cliente);
    }

    @Override
    public ClienteDTO guardar(ClienteDTO dto) {
        Cliente cliente = toEntity(dto);
        return toDTO(clienteRepositorio.save(cliente));
    }

    @Override
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente existente = clienteRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id));

        existente.setNombre(dto.getNombre());
        existente.setCorreo(dto.getCorreo());
        existente.setTelefono(dto.getTelefono());
        return toDTO(clienteRepositorio.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!clienteRepositorio.existsById(id)) {
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id);
        }
        clienteRepositorio.deleteById(id);
    }

    private ClienteDTO toDTO(Cliente c) {
        return ClienteDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .correo(c.getCorreo())
                .telefono(c.getTelefono())
                .build();
    }

    private Cliente toEntity(ClienteDTO dto) {
        return Cliente.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .telefono(dto.getTelefono())
                .build();
    }
}
