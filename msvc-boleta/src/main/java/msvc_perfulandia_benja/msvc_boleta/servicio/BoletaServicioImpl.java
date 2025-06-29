package msvc_perfulandia_benja.msvc_boleta.servicio;

import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.dto.ItemBoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.excepciones.RecursoNoEncontradoException;
import msvc_perfulandia_benja.msvc_boleta.modelo.Boleta;
import msvc_perfulandia_benja.msvc_boleta.modelo.BoletaDetalle;
import msvc_perfulandia_benja.msvc_boleta.repositorio.BoletaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoletaServicioImpl implements BoletaServicio {

    private final BoletaRepositorio repositorio;

    public BoletaServicioImpl(BoletaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<BoletaDTO> listar() {
        return repositorio.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BoletaDTO obtenerPorId(Long id) {
        Boleta boleta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Boleta no encontrada con id: " + id));
        return toDTO(boleta);
    }

    @Override
    public BoletaDTO guardar(BoletaDTO dto) {
        Boleta entidad = toEntity(dto);
        entidad.calcularTotal(); // Se asegura que el total esté bien calculado antes de guardar
        return toDTO(repositorio.save(entidad));
    }

    @Override
    public void eliminar(Long id) {
        Boleta boleta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Boleta no encontrada con id: " + id));
        repositorio.deleteById(id);
    }

    protected BoletaDTO toDTO(Boleta boleta) {
        return BoletaDTO.builder()
                .id(boleta.getId())
                .clienteId(boleta.getClienteId())
                .fecha(boleta.getFecha())
                .total(boleta.getTotal())
                .items(boleta.getItems().stream()
                        .map(item -> ItemBoletaDTO.builder()
                                .productoId(item.getProductoId())
                                .cantidad(item.getCantidad())
                                .precioUnitario(item.getPrecioUnitario())
                                .build())
                        .toList())
                .build();
    }

    protected Boleta toEntity(BoletaDTO dto) {
        Boleta boleta = new Boleta();
        boleta.setClienteId(dto.getClienteId());
        boleta.setFecha(dto.getFecha());

        List<BoletaDetalle> items = dto.getItems().stream()
                .map(dtoItem -> {
                    BoletaDetalle item = new BoletaDetalle();
                    item.setProductoId(dtoItem.getProductoId());
                    item.setCantidad(dtoItem.getCantidad());
                    item.setPrecioUnitario(dtoItem.getPrecioUnitario());
                    item.setBoleta(boleta);
                    return item;
                })
                .toList();

        boleta.setItems(items);
        boleta.calcularTotal(); // Se asegura que el total se calcule correctamente
        return boleta;
    }
}
