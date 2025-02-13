package modelo;

import modelo.Alumno;
import modelo.Calificacion;
import modelo.GestionAlumnos;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GestionAlumnosTest {

    private GestionAlumnos gestionAlumnos;

    @Before
    public void setUp() {
        gestionAlumnos = new GestionAlumnos();
    }

   @Test
    public void testAgregarAlumno() {
        // Caso: Agregar un alumno
        Alumno alumno = new Alumno("12345678", "Pérez", "García", "Juan");
        alumno.agregarCalificacion(new Calificacion("Matemáticas", 85));
        gestionAlumnos.agregarAlumno(alumno);

        assertEquals(1, gestionAlumnos.getListaAlumnos().size());
        assertTrue(gestionAlumnos.getListaAlumnos().contains(alumno));

        // Caso: Intentar agregar un alumno duplicado
        Alumno alumnoDuplicado = new Alumno("12345678", "Pérez", "García", "Juan");
        alumnoDuplicado.agregarCalificacion(new Calificacion("Matemáticas", 85));
        gestionAlumnos.agregarAlumno(alumnoDuplicado);

        assertEquals(1, gestionAlumnos.getListaAlumnos().size()); // Verifica que el tamaño no ha cambiado
    }



    @Test
    public void testEliminarAlumno() {
        Alumno alumno = new Alumno("12345678", "Pérez", "García", "Juan");
        gestionAlumnos.agregarAlumno(alumno);
        gestionAlumnos.eliminarAlumno(alumno);
        
        assertEquals(0, gestionAlumnos.getListaAlumnos().size());
        assertFalse(gestionAlumnos.getListaAlumnos().contains(alumno));
    }

    @Test
    public void testEliminarAlumnoNoExistente() {
        Alumno alumno1 = new Alumno("12345678", "Pérez", "García", "Juan");
        Alumno alumno2 = new Alumno("87654321", "Gómez", "López", "María");
        gestionAlumnos.agregarAlumno(alumno1);
        
        gestionAlumnos.eliminarAlumno(alumno2);
        
        assertEquals(1, gestionAlumnos.getListaAlumnos().size());
        assertTrue(gestionAlumnos.getListaAlumnos().contains(alumno1));
    }

    @Test
    public void testBuscarAlumno() {
        // Caso: Alumno encontrado
        Alumno alumno = new Alumno("12345678", "Pérez", "García", "Juan");
        alumno.agregarCalificacion(new Calificacion("Matemáticas", 85));
        gestionAlumnos.agregarAlumno(alumno);

        Alumno encontrado = gestionAlumnos.buscarAlumno("12345678", "Matemáticas");
        assertNotNull(encontrado);
        assertEquals(alumno, encontrado);

        // Caso: Alumno no encontrado
        Alumno noEncontrado = gestionAlumnos.buscarAlumno("99999999", "Física");
        assertNull(noEncontrado);

        // Caso: Alumno con asignatura no existente
        Alumno asignaturaNoEncontrada = gestionAlumnos.buscarAlumno("12345678", "Física");
        assertNull(asignaturaNoEncontrada);
    }

    @Test
    public void testExisteAlumnoConMatriculaYAsignatura() {
        Alumno alumno = new Alumno("12345678", "Pérez", "García", "Juan");
        alumno.agregarCalificacion(new Calificacion("Matemáticas", 85));
        gestionAlumnos.agregarAlumno(alumno);
        
        assertTrue(gestionAlumnos.existeAlumnoConMatriculaYAsignatura("12345678", "Matemáticas"));
        assertFalse(gestionAlumnos.existeAlumnoConMatriculaYAsignatura("12345678", "Física"));
        assertFalse(gestionAlumnos.existeAlumnoConMatriculaYAsignatura("87654321", "Matemáticas"));
    }

    @Test
    public void testEsMatriculaValida() {
        assertTrue(GestionAlumnos.esMatriculaValida("12345678")); // Contiene exactamente 8 carácteres numéricos
        assertFalse(GestionAlumnos.esMatriculaValida("1234567")); // Muy corta
        assertFalse(GestionAlumnos.esMatriculaValida("123456789")); // Muy larga
        assertFalse(GestionAlumnos.esMatriculaValida("1234567a")); // Contiene letra
        assertFalse(GestionAlumnos.esMatriculaValida("")); // Vacía
        assertFalse(GestionAlumnos.esMatriculaValida("12 34567")); // Contiene espacio
        assertFalse(GestionAlumnos.esMatriculaValida("Prueba")); // Solo contiene caracteres
        assertFalse(GestionAlumnos.esMatriculaValida("1Prueba2")); // Contiene valores alfanuméricos
        assertFalse(GestionAlumnos.esMatriculaValida("qwertyui")); // Contiene exactamente 8 carácteres no numéricos
    }
    
    @Test
    public void testAgregarAlumnoDiferenteAsignatura() {
        // Caso: Agregar un alumno con la misma matrícula pero distinta asignatura
        Alumno alumno = new Alumno("12345678", "Pérez", "García", "Juan");
        alumno.agregarCalificacion(new Calificacion("Matemáticas", 85));
        gestionAlumnos.agregarAlumno(alumno);

        Alumno alumnoDiferenteAsignatura = new Alumno("12345678", "Pérez", "García", "Juan");
        alumnoDiferenteAsignatura.agregarCalificacion(new Calificacion("Física", 90));

        boolean agregado = gestionAlumnos.agregarAlumno(alumnoDiferenteAsignatura);

        assertTrue(agregado);
        assertEquals(1, gestionAlumnos.getListaAlumnos().size()); // Misma matrícula, pero nueva asignatura
        assertEquals(2, gestionAlumnos.getListaAlumnos().get(0).getCalificaciones().size()); // Debe tener dos asignaturas
    }

    @Test
    public void testEliminarAlumnoConVariasCalificaciones() {
        Alumno alumno = new Alumno("12345678", "Pérez", "García", "Juan");
        alumno.agregarCalificacion(new Calificacion("Matemáticas", 85));
        alumno.agregarCalificacion(new Calificacion("Física", 90));
        gestionAlumnos.agregarAlumno(alumno);

        gestionAlumnos.eliminarAlumno(alumno);

        assertEquals(0, gestionAlumnos.getListaAlumnos().size());
        assertFalse(gestionAlumnos.getListaAlumnos().contains(alumno));
    }

    @Test
    public void testBuscarAlumnoConMultiplesAsignaturas() {
        Alumno alumno = new Alumno("12345678", "Pérez", "García", "Juan");
        alumno.agregarCalificacion(new Calificacion("Matemáticas", 85));
        alumno.agregarCalificacion(new Calificacion("Física", 90));
        gestionAlumnos.agregarAlumno(alumno);

        Alumno encontrado = gestionAlumnos.buscarAlumno("12345678", "Física");
        assertNotNull(encontrado);
        assertEquals("12345678", encontrado.getMatricula());
    }

}