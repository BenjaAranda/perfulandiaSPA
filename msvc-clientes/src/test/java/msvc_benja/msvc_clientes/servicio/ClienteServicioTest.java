package msvc_benja.msvc_clientes.servicio;

import msvc_benja.msvc_clientes.dto.ClienteDTO;
import msvc_benja.msvc_clientes.excepciones.RecursoNoEncontradoException;
import msvc_benja.msvc_clientes.modelo.Cliente;
import msvc_benja.msvc_clientes.repositorio.ClienteRepositorio;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServicioTest {

    @Mock
    private ClienteRepositorio clienteRepositorio;

    @InjectMocks
    private ClienteServicioImpl clienteServicio;

    private Cliente clienteEjemplo;

    private List<Cliente> clientes = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Faker faker = new Faker(new Locale("es", "CL"));

        clienteEjemplo = Cliente.builder()
                .id(1L)
                .rut("11111111-1")
                .nombre("Juan Pérez")
                .correo("juan@mail.com")
                .telefono("912345678")
                .build();

        for (int i = 0; i < 10; i++) {
            clientes.add(Cliente.builder()
                    .id((long) i + 2)
                    .rut(faker.idNumber().valid())
                    .nombre(faker.name().fullName())
                    .correo(faker.internet().emailAddress())
                    .telefono(faker.phoneNumber().cellPhone())
                    .build());
        }
    }

    @Test
    @DisplayName("Debe listar todos los clientes")
    void testListarClientes() {
        List<Cliente> todos = new ArrayList<>(clientes);
        todos.add(clienteEjemplo);
        when(clienteRepositorio.findAll()).thenReturn(todos);

        List<ClienteDTO> resultado = clienteServicio.listar();

        assertThat(resultado).hasSize(11);
        assertThat(resultado).extracting("nombre").contains("Juan Pérez");
        verify(clienteRepositorio, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe obtener cliente por ID")
    void testObtenerClientePorId() {
        when(clienteRepositorio.findById(1L)).thenReturn(Optional.of(clienteEjemplo));

        ClienteDTO resultado = clienteServicio.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCorreo()).isEqualTo("juan@mail.com");
        verify(clienteRepositorio).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si cliente no existe")
    void testObtenerClienteNoExistente() {
        when(clienteRepositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteServicio.obtenerPorId(99L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Cliente no encontrado");
    }

    @Test
    @DisplayName("Debe guardar un nuevo cliente")
    void testGuardarCliente() {
        ClienteDTO nuevoDTO = ClienteDTO.builder()
                .rut("22222222-2")
                .nombre("Pedro Castillo")
                .correo("pedro@mail.com")
                .telefono("987654321")
                .build();

        Cliente nuevoEntidad = Cliente.builder()
                .id(2L)
                .rut(nuevoDTO.getRut())
                .nombre(nuevoDTO.getNombre())
                .correo(nuevoDTO.getCorreo())
                .telefono(nuevoDTO.getTelefono())
                .build();

        when(clienteRepositorio.save(any(Cliente.class))).thenReturn(nuevoEntidad);

        ClienteDTO guardado = clienteServicio.guardar(nuevoDTO);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isEqualTo(2L);
        assertThat(guardado.getNombre()).isEqualTo("Pedro Castillo");
    }

    @Test
    @DisplayName("Debe actualizar cliente existente")
    void testActualizarCliente() {
        Cliente existente = clienteEjemplo;

        ClienteDTO dtoActualizado = ClienteDTO.builder()
                .rut(existente.getRut())
                .nombre("Nombre Actualizado")
                .correo("actualizado@mail.com")
                .telefono("987654321")
                .build();

        when(clienteRepositorio.findById(1L)).thenReturn(Optional.of(existente));
        when(clienteRepositorio.save(any(Cliente.class))).thenReturn(existente);

        ClienteDTO resultado = clienteServicio.actualizar(1L, dtoActualizado);

        assertThat(resultado.getNombre()).isEqualTo("Nombre Actualizado");
        assertThat(resultado.getCorreo()).isEqualTo("actualizado@mail.com");
        verify(clienteRepositorio).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar cliente inexistente")
    void testActualizarClienteNoExistente() {
        ClienteDTO dto = ClienteDTO.builder()
                .rut("99999999-9")
                .nombre("Fantasma")
                .correo("ghost@mail.com")
                .telefono("000000000")
                .build();

        when(clienteRepositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteServicio.actualizar(99L, dto))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    @DisplayName("Debe eliminar cliente existente")
    void testEliminarCliente() {
        when(clienteRepositorio.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepositorio).deleteById(1L);

        clienteServicio.eliminar(1L);

        verify(clienteRepositorio).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar cliente no existente")
    void testEliminarClienteNoExistente() {
        when(clienteRepositorio.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> clienteServicio.eliminar(99L))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }
    @Test
    @DisplayName("Debe lanzar excepción si se intenta guardar cliente nulo")
    void testGuardarClienteNulo() {
        assertThatThrownBy(() -> clienteServicio.guardar(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Debe lanzar excepción si se intenta actualizar con DTO nulo")
    void testActualizarClienteConDtoNulo() {
        when(clienteRepositorio.findById(1L)).thenReturn(Optional.of(clienteEjemplo));

        assertThatThrownBy(() -> clienteServicio.actualizar(1L, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Guardar cliente: campos obligatorios vacíos o nulos")
    void testGuardarClienteConCamposInvalidos() {
        ClienteDTO dtoInvalido = ClienteDTO.builder()
                .rut("")  // inválido
                .nombre(null)  // inválido
                .correo("   ")  // inválido
                .telefono("")  // inválido
                .build();

        when(clienteRepositorio.save(any())).thenReturn(clienteEjemplo); // aunque no debería llegar aquí

        assertThatThrownBy(() -> clienteServicio.guardar(dtoInvalido))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Guardar cliente: cuando repositorio retorna null")
    void testGuardarClienteRepositorioRetornaNull() {
        ClienteDTO dto = ClienteDTO.builder()
                .rut("12345678-9")
                .nombre("Cliente Falla")
                .correo("fallo@mail.com")
                .telefono("911223344")
                .build();

        when(clienteRepositorio.save(any())).thenReturn(null);

        assertThatThrownBy(() -> clienteServicio.guardar(dto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Error al guardar el cliente");
    }

    @Test
    @DisplayName("Verificar mapeo exacto de DTO -> Entidad y viceversa")
    void testMapeoDtoEntidad() {
        ClienteDTO dto = ClienteDTO.builder()
                .id(99L)
                .rut("99999999-9")
                .nombre("Test Mapper")
                .correo("mapper@mail.com")
                .telefono("999999999")
                .build();

        Cliente entidad = Cliente.builder()
                .id(dto.getId())
                .rut(dto.getRut())
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .telefono(dto.getTelefono())
                .build();

        assertThat(entidad.getNombre()).isEqualTo(dto.getNombre());
        assertThat(entidad.getCorreo()).isEqualTo(dto.getCorreo());
        assertThat(entidad.getTelefono()).isEqualTo(dto.getTelefono());
    }
    @Test
    @DisplayName("No debe permitir guardar cliente con rut ya existente")
    void testGuardarClienteRutDuplicado() {
        when(clienteRepositorio.existsByRut("11111111-1")).thenReturn(true);

        ClienteDTO dto = ClienteDTO.builder()
                .rut("11111111-1")
                .nombre("Duplicado")
                .correo("dup@mail.com")
                .telefono("123456789")
                .build();

        assertThatThrownBy(() -> clienteServicio.guardar(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ya existe");
    }


}
