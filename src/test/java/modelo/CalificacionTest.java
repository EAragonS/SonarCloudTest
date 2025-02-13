package modelo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalificacionTest {

    private Calificacion calificacion;
    private final String ASIGNATURA = "Matemáticas";
    private final int CALIFICACION = 90;

    @Before
    public void setUp() {
        calificacion = new Calificacion(ASIGNATURA, CALIFICACION);
    }

    @Test
    public void testConstructor() {
        assertEquals(ASIGNATURA, calificacion.getAsignatura());
        assertEquals(CALIFICACION, calificacion.getCalificacion());
    }

    @Test
    public void testSetAsignatura() {
        String nuevaAsignatura = "Física";
        calificacion.setAsignatura(nuevaAsignatura);
        assertEquals(nuevaAsignatura, calificacion.getAsignatura());
    }

    @Test
    public void testSetCalificacion() {
        int nuevaCalificacion = 85;
        calificacion.setCalificacion(nuevaCalificacion);
        assertEquals(nuevaCalificacion, calificacion.getCalificacion());
    }
}