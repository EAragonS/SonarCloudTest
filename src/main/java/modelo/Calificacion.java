package modelo;

/**
 * Representa una calificación obtenida por un alumno en una asignatura específica.
 */
public class Calificacion {

    private String asignatura;
    private int calificacion;

    /**
     * Crea una instancia de la clase Calificacion con la asignatura y la calificación proporcionadas.
     *
     * @param asignatura  el nombre de la asignatura.
     * @param calificacion la calificación obtenida en la asignatura.
     */
    public Calificacion(String asignatura, int calificacion) {
        this.asignatura = asignatura;
        this.calificacion = calificacion;
    }

    /**
     * Obtiene el nombre de la asignatura asociada a esta calificación.
     *
     * @return el nombre de la asignatura.
     */
    public String getAsignatura() {
        return asignatura;
    }

    /**
     * Establece el nombre de la asignatura asociada a esta calificación.
     *
     * @param asignatura el nuevo nombre de la asignatura.
     */
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * Obtiene la calificación obtenida en la asignatura.
     *
     * @return la calificación obtenida.
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Establece una nueva calificación para la asignatura.
     *
     * @param calificacion la nueva calificación obtenida.
     */
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}