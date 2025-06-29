package msvc_perfulandia_benja.msvc_boleta.servicio;

import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.dto.ItemBoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.excepciones.RecursoNoEncontradoException;
import msvc_perfulandia_benja.msvc_boleta.modelo.Boleta;
import msvc_perfulandia_benja.msvc_boleta.modelo.BoletaDetalle;
import msvc_perfulandia_benja.msvc_boleta.repositorio.BoletaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoletaServicioTest {

    @Mock
    private BoletaRepositorio repositorio;

    @InjectMocks
    private BoletaServicioImpl servicio;

    private Boleta boleta;
    private BoletaDTO boletaDTO;

    @BeforeEach
    void setUp() {
        ItemBoletaDTO itemDTO = ItemBoletaDTO.builder()
                .productoId(1L)
                .cantidad(2)
                .precioUnitario(50.0)
                .build();

        boletaDTO = BoletaDTO.builder()
                .clienteId(1L)
                .fecha(LocalDateTime.now())
                .items(List.of(itemDTO))
                .build();

        BoletaDetalle detalle = BoletaDetalle.builder()
                .id(1L)
                .productoId(1L)
                .cantidad(2)
                .precioUnitario(50.0)
                .build();

        boleta = Boleta.builder()
                .id(1L)
                .clienteId(1L)
                .fecha(boletaDTO.getFecha())
                .total(100.0)
                .items(List.of(detalle))
                .build();

        detalle.setBoleta(boleta);
    }

    @Test
    @DisplayName("Listar todas las boletas")
    void testListar() {
        when(repositorio.findAll()).thenReturn(List.of(boleta));

        List<BoletaDTO> resultado = servicio.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getClienteId()).isEqualTo(1L);
        verify(repositorio).findAll();
    }

    @Test
    @DisplayName("Obtener boleta por ID existente")
    void testObtenerPorId() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(boleta));

        BoletaDTO resultado = servicio.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTotal()).isEqualTo(100.0);
        verify(repositorio).findById(1L);
    }

    @Test
    @DisplayName("Obtener boleta por ID no existente lanza excepción")
    void testObtenerPorIdNoExistente() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.obtenerPorId(99L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Boleta no encontrada");
    }

    @Test
    @DisplayName("Guardar boleta correctamente")
    void testGuardar() {
        when(repositorio.save(any(Boleta.class))).thenAnswer(invoc -> {
            Boleta b = invoc.getArgument(0);
            b.setId(2L);
            return b;
        });

        BoletaDTO guardada = servicio.guardar(boletaDTO);

        assertThat(guardada).isNotNull();
        assertThat(guardada.getId()).isEqualTo(2L);
        assertThat(guardada.getItems()).hasSize(1);
        verify(repositorio).save(any());
    }

    @Test
    @DisplayName("Eliminar boleta existente")
    void testEliminarExistente() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(boleta));
        doNothing().when(repositorio).deleteById(1L);

        servicio.eliminar(1L);

        verify(repositorio).deleteById(1L);
    }

    @Test
    @DisplayName("Eliminar boleta inexistente lanza excepción")
    void testEliminarInexistente() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.eliminar(99L))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    @DisplayName("Conversión de Boleta a DTO")
    void testToDTO() {
        BoletaDTO dto = servicio.listar().isEmpty() ? servicio.toDTO(boleta) : null;

        assertThat(dto).isNotNull();
        assertThat(dto.getItems()).hasSize(1);
    }

    @Test
    @DisplayName("Conversión de DTO a Boleta")
    void testToEntity() {
        Boleta entidad = servicio.toEntity(boletaDTO);

        assertThat(entidad).isNotNull();
        assertThat(entidad.getItems()).hasSize(1);
        assertThat(entidad.getItems().get(0).getBoleta()).isEqualTo(entidad);
    }

    @Test
    @DisplayName("Cálculo correcto del total")
    void testCalculoTotal() {
        Boleta b = servicio.toEntity(boletaDTO);
        b.calcularTotal();

        assertThat(b.getTotal()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Conversión y persistencia mantienen consistencia")
    void testGuardarYLeerDTO() {
        when(repositorio.save(any(Boleta.class))).thenReturn(boleta);
        when(repositorio.findById(1L)).thenReturn(Optional.of(boleta));

        BoletaDTO guardada = servicio.guardar(boletaDTO);
        BoletaDTO consultada = servicio.obtenerPorId(1L);

        assertThat(guardada.getClienteId()).isEqualTo(consultada.getClienteId());
        assertThat(guardada.getItems().size()).isEqualTo(consultada.getItems().size());
    }
}
