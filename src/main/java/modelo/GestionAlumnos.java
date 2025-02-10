package modelo;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;
import modelo.dao.AlumnoDAO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

/**
 * Clase para gestionar la lógica de negocio relacionada con alumnos.
 */
public class GestionAlumnos {
    private AlumnoDAO alumnoDAO;

    /**
     * Constructor principal que inicializa el DAO.
     */
    public GestionAlumnos() {
        this.alumnoDAO = new AlumnoDAO();
    }

    /**
     * Agrega un nuevo alumno a la lista.
     *
     * @param alumno Alumno a agregar.
     */
    public boolean agregarAlumno(Alumno alumno) {
        // Verificar si ya existe un alumno con la misma matrícula y asignatura
        for (Alumno alumnoExistente : alumnoDAO.getListaAlumnos()) {
            if (alumnoExistente.getMatricula().equals(alumno.getMatricula())) {
                for (Calificacion calificacion : alumnoExistente.getCalificaciones()) {
                    for (Calificacion nuevaCalificacion : alumno.getCalificaciones()) {
                        if (calificacion.getAsignatura().equals(nuevaCalificacion.getAsignatura())) {
                            // Ya existe un alumno con la misma matrícula y asignatura
                            return false;
                        }
                    }
                }
            }
        }
        
        // Si no se encontró un duplicado, agregar el alumno
        alumnoDAO.getListaAlumnos().add(alumno);
        return true;
    }

    /**
     * Elimina un alumno de la lista.
     *
     * @param alumno Alumno a eliminar.
     */
    public void eliminarAlumno(Alumno alumno) {
        alumnoDAO.getListaAlumnos().remove(alumno);
    }

    /**
     * Obtiene la lista de alumnos.
     *
     * @return Lista de alumnos.
     */
    public List<Alumno> getListaAlumnos() {
        return alumnoDAO.getListaAlumnos();
    }

    /**
     * Busca un alumno por su matrícula y asignatura.
     *
     * @param matricula Matrícula del alumno a buscar.
     * @param asignatura Asignatura asociada al alumno.
     * @return Alumno encontrado o null si no existe.
     */
    public Alumno buscarAlumno(String matricula, String asignatura) {
        List<Alumno> alumnos = alumnoDAO.getListaAlumnos();
        for (Alumno alumno : alumnos) {
            if (alumno.getMatricula().equals(matricula)) {
                for (Calificacion calificacion : alumno.getCalificaciones()) {
                    if (calificacion.getAsignatura().equals(asignatura)) {
                        return alumno;
                    }
                }
            }
        }
        return null; // Retorna null si no se encuentra el alumno
    }

    /**
     * Verifica si existe un alumno con una matrícula y asignatura específicas.
     *
     * @param matricula Matrícula del alumno.
     * @param asignatura Asignatura a verificar.
     * @return true si el alumno existe, false en caso contrario.
     */
    public boolean existeAlumnoConMatriculaYAsignatura(String matricula, String asignatura) {
        List<Alumno> alumnos = alumnoDAO.getListaAlumnos();
        for (Alumno alumno : alumnos) {
            if (alumno.getMatricula().equals(matricula)) {
                for (Calificacion calificacion : alumno.getCalificaciones()) {
                    if (calificacion.getAsignatura().equals(asignatura)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Carga un archivo con datos de alumnos.
     *
     * @param rutaArchivo Ruta del archivo CSV.
     * @return Lista de alumnos cargada desde el archivo.
     */
    public List<Alumno> cargarArchivo(String rutaArchivo) {
        return alumnoDAO.cargarAlumnos(rutaArchivo);
    }

    /**
     * Guarda la lista de alumnos en un archivo CSV.
     *
     * @param rutaArchivo Ruta donde se guardará el archivo CSV.
     */
    public void guardarListaDeAlumnos(String rutaArchivo) {
        alumnoDAO.exportarAlumnos(rutaArchivo);
    }

    /**
     * Valida si una matrícula cumple con el formato correcto.
     *
     * @param matricula Matrícula a validar.
     * @return true si la matrícula es válida, false en caso contrario.
     */
    public static boolean esMatriculaValida(String matricula) {
        // La expresión regular se refiere a que la matrícula tiene que ser de 8 dígitos exactamente.
        return Pattern.matches("^\\d{8}$", matricula);
    }
}