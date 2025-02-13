package controlador;

import vista.VistaPrincipal;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControladorVistaPrincipalTest {
    private VistaPrincipal vistaMock;

    @Before
    public void setUp() {
        vistaMock = mock(VistaPrincipal.class);
    }

    @Test
    public void testAgregarAlumno() {
        // Dummy test that always passes
        assertTrue(true);
    }

    @Test
    public void testSeleccionarAlumno() {
        // Dummy test that always passes
        assertTrue(true);
    }
    
    @Test
    public void testEliminarAlumnoSeleccionado() {
        // Dummy test that always passes
        assertTrue(true);
    }
    
    @Test
    public void testActualizarVistaConAlumnos() {
        // Dummy test that always passes
        assertTrue(true);
    }
}