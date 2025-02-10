package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un alumno con datos personales y una lista de calificaciones asociadas.
 * Proporciona métodos para gestionar los datos del alumno y sus calificaciones.
 */
public class Alumno {

    private String matricula;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private List<Calificacion> calificaciones;

    /**
     * Crea una instancia de la clase Alumno con los datos personales proporcionados.
     *
     * @param matricula        la matrícula del alumno, que debe ser única.
     * @param apellidoPaterno  el apellido paterno del alumno.
     * @param apellidoMaterno  el apellido materno del alumno.
     * @param nombre           el nombre del alumno.
     */
    public Alumno(String matricula, String apellidoPaterno, String apellidoMaterno, String nombre) {
        this.matricula = matricula;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombre = nombre;
        this.calificaciones = new ArrayList<>();
    }

    /**
     * Obtiene la matrícula del alumno.
     *
     * @return la matrícula del alumno.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del alumno.
     *
     * @param matricula la nueva matrícula del alumno.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene el apellido paterno del alumno.
     *
     * @return el apellido paterno del alumno.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del alumno.
     *
     * @param apellidoPaterno el nuevo apellido paterno del alumno.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del alumno.
     *
     * @return el apellido materno del alumno.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del alumno.
     *
     * @param apellidoMaterno el nuevo apellido materno del alumno.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el nombre del alumno.
     *
     * @return el nombre del alumno.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del alumno.
     *
     * @param nombre el nuevo nombre del alumno.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene una copia de la lista de calificaciones del alumno.
     *
     * @return una lista de calificaciones asociadas al alumno.
     */
    public List<Calificacion> getCalificaciones() {
        return new ArrayList<>(calificaciones);
    }

    /**
     * Agrega una calificación a la lista de calificaciones del alumno.
     *
     * @param calificacion la calificación a agregar.
     */
    public void agregarCalificacion(Calificacion calificacion) {
        this.calificaciones.add(calificacion);
    }
}