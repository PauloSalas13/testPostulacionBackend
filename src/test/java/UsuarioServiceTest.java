

import com.evaluacion.apiusuarios.model.Usuario;
import com.evaluacion.apiusuarios.repository.UsuarioRepository;
import com.evaluacion.apiusuarios.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaCrearUsuarioExitosamente() {
        // Datos de entrada
        Usuario usuario = new Usuario();
        usuario.setEmail("nuevo@correo.com");
        usuario.setName("Juan Perez");
        usuario.setPassword("Hunter2!");

        // Configuración del mock
        when(usuarioRepository.findByEmail("nuevo@correo.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecución
        Usuario resultado = usuarioService.crearUsuario(usuario);

        // Validaciones
        assertNotNull(resultado.getId());
        assertEquals("nuevo@correo.com", resultado.getEmail());
        assertNotNull(resultado.getToken());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void deberiaLanzarExcepcionCuandoElCorreoYaExiste() {
        // Datos de entrada
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("existente@correo.com");

        // Configuración del mock
        when(usuarioRepository.findByEmail("existente@correo.com")).thenReturn(Optional.of(usuarioExistente));

        // Ejecución y validación
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail("existente@correo.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.crearUsuario(nuevoUsuario);
        });

        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
