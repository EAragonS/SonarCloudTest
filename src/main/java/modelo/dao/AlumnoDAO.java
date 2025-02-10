package modelo.dao;

import modelo.Alumno;
import modelo.Calificacion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar el acceso a datos de los alumnos.
 */
public class AlumnoDAO {

    private List<Alumno> listaAlumnos;

    /**
     * Constructor que inicializa la lista de alumnos.
     */
    public AlumnoDAO() {
        this.listaAlumnos = new ArrayList<>();
    }

    /**
     * Carga alumnos desde un archivo CSV y los devuelve como una lista.
     *
     * @param rutaArchivo Ruta del archivo de entrada.
     * @return Lista de alumnos cargados.
     */
    public List<Alumno> cargarAlumnos(String rutaArchivo) {
        List<Alumno> alumnosCargados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 4) { // Validar que al menos están los datos del alumno
                    String matricula = datos[0];
                    String apellidoPaterno = datos[1];
                    String apellidoMaterno = datos[2];
                    String nombre = datos[3];

                    // Crear el alumno
                    Alumno alumno = new Alumno(matricula, apellidoPaterno, apellidoMaterno, nombre);

                    // Cargar calificaciones asociadas (si existen)
                    for (int i = 4; i < datos.length; i++) {
                        String[] calificacionDatos = datos[i].split(",");
                        if (calificacionDatos.length == 2) {
                            String asignatura = calificacionDatos[0];
                            int calificacion = Integer.parseInt(calificacionDatos[1]);
                            alumno.agregarCalificacion(new Calificacion(asignatura, calificacion));
                        }
                    }

                    alumnosCargados.add(alumno);
                }
            }
            listaAlumnos = alumnosCargados; // Actualizar la lista interna
            System.out.println("Alumnos cargados exitosamente desde: " + rutaArchivo);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar alumnos: " + e.getMessage());
        }
        return alumnosCargados; // Devolver la lista cargada
    }

    /**
     * Exporta los alumnos a un archivo CSV.
     *
     * @param rutaArchivo Ruta del archivo de salida.
     */
    public void exportarAlumnos(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Alumno alumno : listaAlumnos) {
                StringBuilder sb = new StringBuilder();
                sb.append(alumno.getMatricula()).append(";");
                sb.append(alumno.getApellidoPaterno()).append(";");
                sb.append(alumno.getApellidoMaterno()).append(";");
                sb.append(alumno.getNombre());

                // Agregar calificaciones
                for (Calificacion calificacion : alumno.getCalificaciones()) {
                    sb.append(";").append(calificacion.getAsignatura()).append(",").append(calificacion.getCalificacion());
                }

                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Alumnos exportados exitosamente a: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al exportar alumnos: " + e.getMessage());
        }
    }

    /**
     * Devuelve la lista interna de alumnos.
     *
     * @return Lista de alumnos.
     */
    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }
}