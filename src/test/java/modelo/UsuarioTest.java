package modelo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuarioTest {

    private Usuario usuario;
    private final String NOMBRE_USUARIO = "juan123";
    private final String CONTRASENA = "secreto123";

    @Before
    public void setUp() {
        usuario = new Usuario(NOMBRE_USUARIO, CONTRASENA);
    }

    @Test
    public void testConstructor() {
        assertEquals(NOMBRE_USUARIO, usuario.getNombreUsuario());
        assertEquals(CONTRASENA, usuario.getContrasena());
    }

    @Test
    public void testSetNombreUsuario() {
        String nuevoNombreUsuario = "pedro456";
        usuario.setNombreUsuario(nuevoNombreUsuario);
        assertEquals(nuevoNombreUsuario, usuario.getNombreUsuario());
    }

    @Test
    public void testSetContrasena() {
        String nuevaContrasena = "nuevaContrasena456";
        usuario.setContrasena(nuevaContrasena);
        assertEquals(nuevaContrasena, usuario.getContrasena());
    }
}