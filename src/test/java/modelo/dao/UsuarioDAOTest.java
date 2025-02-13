package modelo.dao;

import modelo.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

public class UsuarioDAOTest {

    private UsuarioDAO usuarioDAO;
    private File archivoTemporal;
    
    

    @Before
    public void setUp() throws IOException {
        usuarioDAO = new UsuarioDAO();
        // Crear un archivo temporal para pruebas
        archivoTemporal = File.createTempFile("usuarios_test", ".csv");
        archivoTemporal.deleteOnExit();
        
    }

    @Test
    public void testCargarUsuariosDesdeCSV() throws IOException {
        // Escribir datos simulados en el archivo temporal
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
            writer.write("usuario1,5f4dcc3b5aa765d61d8327deb882cf99\n"); // "password" en MD5
            writer.write("usuario2,e99a18c428cb38d5f260853678922e03\n"); // "abc123" en MD5
        }

        // Cargar usuarios desde el archivo
        usuarioDAO.cargarUsuarios(archivoTemporal.getAbsolutePath());

        List<Usuario> usuarios = usuarioDAO.getListaUsuarios();
        assertEquals(2, usuarios.size());

        // Verificar el primer usuario
        Usuario usuario1 = usuarios.get(0);
        assertEquals("usuario1", usuario1.getNombreUsuario());
        assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", usuario1.getContrasena());

        // Verificar el segundo usuario
        Usuario usuario2 = usuarios.get(1);
        assertEquals("usuario2", usuario2.getNombreUsuario());
        assertEquals("e99a18c428cb38d5f260853678922e03", usuario2.getContrasena());
    }

    @Test
    public void testExportarUsuariosACSV() throws IOException {
        // Crear lista de usuarios y agregarlos al DAO
        Usuario usuario = new Usuario("usuario1", "password"); // La contraseña será encriptada
        usuarioDAO.agregarUsuario(usuario);

        // Exportar a un archivo temporal
        usuarioDAO.exportarUsuarios(archivoTemporal.getAbsolutePath());

        // Leer el archivo y verificar su contenido
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoTemporal))) {
            String linea = reader.readLine();
            String esperado = "usuario1,5f4dcc3b5aa765d61d8327deb882cf99"; // "password" en MD5
            assertEquals(esperado, linea);
        }
    }

    @Test
    public void testAgregarUsuarioEncriptaContraseña() {
        Usuario usuario = new Usuario("usuarioTest", "password");
        usuarioDAO.agregarUsuario(usuario);

        Usuario encontrado = usuarioDAO.buscarUsuarioPorNombre("usuarioTest");
        assertNotNull(encontrado);
        assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", encontrado.getContrasena()); // MD5 de "password"
    }

    @Test
    public void testValidarContrasenaCorrecta() {
        Usuario usuario = new Usuario("usuario1", "password");
        usuarioDAO.agregarUsuario(usuario);

        assertTrue(usuarioDAO.validarContrasena("usuario1", "password"));
    }

    @Test
    public void testValidarContrasenaIncorrecta() {
        Usuario usuario = new Usuario("usuario1", "password");
        usuarioDAO.agregarUsuario(usuario);

        assertFalse(usuarioDAO.validarContrasena("usuario1", "wrongpassword"));
    }

    @Test
    public void testBuscarUsuarioNoExistente() {
        assertNull(usuarioDAO.buscarUsuarioPorNombre("inexistente"));
    }
}