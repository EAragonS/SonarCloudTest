package modelo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class AlumnoTest {

    private Alumno alumno;
    private final String MATRICULA = "12345678";
    private final String APELLIDO_PATERNO = "García";
    private final String APELLIDO_MATERNO = "López";
    private final String NOMBRE = "Juan";

    @Before
    public void setUp() {
        alumno = new Alumno(MATRICULA, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE);
    }

    @Test
    public void testConstructor() {
        assertEquals(MATRICULA, alumno.getMatricula());
        assertEquals(APELLIDO_PATERNO, alumno.getApellidoPaterno());
        assertEquals(APELLIDO_MATERNO, alumno.getApellidoMaterno());
        assertEquals(NOMBRE, alumno.getNombre());
        assertTrue(alumno.getCalificaciones().isEmpty());
    }

    @Test
    public void testSetMatricula() {
        String nuevaMatricula = "87654321";
        alumno.setMatricula(nuevaMatricula);
        assertEquals(nuevaMatricula, alumno.getMatricula());
    }

    @Test
    public void testSetApellidoPaterno() {
        String nuevoApellidoPaterno = "Martínez";
        alumno.setApellidoPaterno(nuevoApellidoPaterno);
        assertEquals(nuevoApellidoPaterno, alumno.getApellidoPaterno());
    }

    @Test
    public void testSetApellidoMaterno() {
        String nuevoApellidoMaterno = "Rodríguez";
        alumno.setApellidoMaterno(nuevoApellidoMaterno);
        assertEquals(nuevoApellidoMaterno, alumno.getApellidoMaterno());
    }

    @Test
    public void testSetNombre() {
        String nuevoNombre = "Pedro";
        alumno.setNombre(nuevoNombre);
        assertEquals(nuevoNombre, alumno.getNombre());
    }

    @Test
    public void testAgregarCalificacion() {
        Calificacion calificacion = new Calificacion("Matemáticas", 90);
        alumno.agregarCalificacion(calificacion);
        
        List<Calificacion> calificaciones = alumno.getCalificaciones();
        assertEquals(1, calificaciones.size());
        assertEquals(calificacion, calificaciones.get(0));
    }

    @Test
    public void testGetCalificacionesReturnsCopy() {
        Calificacion calificacion = new Calificacion("Física", 85);
        alumno.agregarCalificacion(calificacion);
        
        List<Calificacion> calificaciones = alumno.getCalificaciones();
        calificaciones.add(new Calificacion("Química", 80));
        
        assertEquals(1, alumno.getCalificaciones().size());
    }

    @Test
    public void testMultipleCalificaciones() {
        Calificacion calificacion1 = new Calificacion("Matemáticas", 90);
        Calificacion calificacion2 = new Calificacion("Física", 85);
        Calificacion calificacion3 = new Calificacion("Química", 80);
        
        alumno.agregarCalificacion(calificacion1);
        alumno.agregarCalificacion(calificacion2);
        alumno.agregarCalificacion(calificacion3);
        
        List<Calificacion> calificaciones = alumno.getCalificaciones();
        assertEquals(3, calificaciones.size());
        assertTrue(calificaciones.contains(calificacion1));
        assertTrue(calificaciones.contains(calificacion2));
        assertTrue(calificaciones.contains(calificacion3));
    }
}