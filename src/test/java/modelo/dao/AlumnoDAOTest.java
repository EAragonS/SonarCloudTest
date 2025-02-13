package modelo.dao;

import modelo.Alumno;
import modelo.Calificacion;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

public class AlumnoDAOTest {

    private AlumnoDAO alumnoDAO;
    private File archivoTemporal;

    @Before
    public void setUp() throws IOException {
        alumnoDAO = new AlumnoDAO();
        // Crear un archivo temporal para pruebas
        archivoTemporal = File.createTempFile("alumnos_test", ".csv");
        archivoTemporal.deleteOnExit();
    }

    @Test
    public void testCargarAlumnosDesdeCSV() throws IOException {
        // Escribir datos simulados en el archivo temporal
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
            writer.write("12345678;Perez;Garcia;Juan;Matemáticas,85;Física,90\n");
            writer.write("87654321;Gomez;Lopez;Maria;Química,95\n");
        }

        // Cargar alumnos desde el archivo
        List<Alumno> alumnos = alumnoDAO.cargarAlumnos(archivoTemporal.getAbsolutePath());

        assertEquals(2, alumnos.size());

        // Verificar el primer alumno
        Alumno alumno1 = alumnos.get(0);
        assertEquals("12345678", alumno1.getMatricula());
        assertEquals("Perez", alumno1.getApellidoPaterno());
        assertEquals(2, alumno1.getCalificaciones().size());

        // Verificar el segundo alumno
        Alumno alumno2 = alumnos.get(1);
        assertEquals("87654321", alumno2.getMatricula());
        assertEquals("Gomez", alumno2.getApellidoPaterno());
        assertEquals(1, alumno2.getCalificaciones().size());
    }

    @Test
    public void testExportarAlumnosACSV() throws IOException {
        // Crear lista de alumnos y agregarlos al DAO
        Alumno alumno = new Alumno("12345678", "Perez", "Garcia", "Juan");
        alumno.agregarCalificacion(new Calificacion("Matemáticas", 85));
        alumno.agregarCalificacion(new Calificacion("Física", 90));

        alumnoDAO.getListaAlumnos().add(alumno);

        // Exportar a un archivo temporal
        alumnoDAO.exportarAlumnos(archivoTemporal.getAbsolutePath());

        // Leer el archivo y verificar su contenido
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoTemporal))) {
            String linea = reader.readLine();
            String esperado = "12345678;Perez;Garcia;Juan;Matemáticas,85;Física,90";
            assertEquals(esperado, linea);
        }
    }

    @Test
    public void testListaAlumnosVaciaInicialmente() {
        assertTrue(alumnoDAO.getListaAlumnos().isEmpty());
    }
}
