package controlador;

import modelo.Alumno;
import modelo.Calificacion;
import modelo.GestionAlumnos;
import vista.VistaPrincipal;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Controlador para manejar la interacción entre la vista principal y el modelo de gestión de alumnos.
 */
public class ControladorVistaPrincipal {
    private GestionAlumnos gestionAlumnos;
    private VistaPrincipal vista;
    private Alumno alumnoSeleccionado;

    /**
     * Constructor que inicializa el controlador con la vista principal.
     * @param vista la vista principal de la aplicación.
     */
    public ControladorVistaPrincipal(VistaPrincipal vista) {
        this.gestionAlumnos = new GestionAlumnos();
        this.vista = vista;
        this.alumnoSeleccionado = null;
        configurarListeners();
    }

    // ==========================
    // Configuración de Listeners
    // ==========================

    /**
     * Configura los listeners para los botones y componentes de la vista principal.
     */
    private void configurarListeners() {
        vista.getBotonAgregarAlumno().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAlumno();
            }
        });

        vista.getBotonEliminarAlumno().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarAlumnoSeleccionado();
            }
        });

        vista.getTablaAlumnos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    seleccionarAlumno();
                }
            }
        });

        vista.getBotonCargarArchivo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivoAlumnos();
            }
        });

        vista.getBotonGuardarListaAlumnos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArchivoAlumnos();
            }
        });

        vista.getBotonCrearPDF().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPDF();
            }
        });
    }

    // ==========================
    // Métodos principales
    // ==========================

    /**
     * Agrega un alumno a la lista gestionada.
     */
    private void agregarAlumno() {
        if (camposEstanVacios()) {
            mostrarMensaje("Todos los campos deben estar llenos para registrar al alumno", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = vista.getCampoMatricula().getText();
        if (!GestionAlumnos.esMatriculaValida(matricula)) {
            mostrarMensaje("La matrícula debe contener constar de solamente 8 dígitos", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String apellidoPaterno = vista.getCampoApellidoPaterno().getText();
        String apellidoMaterno = vista.getCampoApellidoMaterno().getText();
        String nombre = vista.getCampoNombre().getText();
        String asignatura = (String) vista.getCampoAsignatura().getSelectedItem();
        int calificacion = (int) vista.getCampoCalificacion().getValue();

        Alumno alumno = new Alumno(matricula, apellidoPaterno, apellidoMaterno, nombre);
        alumno.agregarCalificacion(new Calificacion(asignatura, calificacion));

        if (gestionAlumnos.agregarAlumno(alumno)) {
            actualizarVistaConAlumnos(gestionAlumnos.getListaAlumnos());
            limpiarCampos();
            alumnoSeleccionado = null;
        } else {
            mostrarMensaje("Ya existe un alumno con la misma matrícula y asignatura", "Error de duplicado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Selecciona un alumno de la tabla y actualiza los campos de la vista con sus datos.
     */
    private void seleccionarAlumno() {
        int filaSeleccionada = vista.getTablaAlumnos().getSelectedRow();
        if (filaSeleccionada != -1) {
            DefaultTableModel modelo = (DefaultTableModel) vista.getTablaAlumnos().getModel();
            String matricula = (String) modelo.getValueAt(filaSeleccionada, 0);
            String apellidoPaterno = (String) modelo.getValueAt(filaSeleccionada, 1);
            String apellidoMaterno = (String) modelo.getValueAt(filaSeleccionada, 2);
            String nombre = (String) modelo.getValueAt(filaSeleccionada, 3);
            String asignatura = (String) modelo.getValueAt(filaSeleccionada, 4);
            int calificacion = (int) modelo.getValueAt(filaSeleccionada, 5);

            vista.getCampoMatricula().setText(matricula);
            vista.getCampoApellidoPaterno().setText(apellidoPaterno);
            vista.getCampoApellidoMaterno().setText(apellidoMaterno);
            vista.getCampoNombre().setText(nombre);
            vista.getCampoAsignatura().setSelectedItem(asignatura);
            vista.getCampoCalificacion().setValue(calificacion);

            alumnoSeleccionado = gestionAlumnos.buscarAlumno(matricula, asignatura);
        }
    }

    /**
     * Elimina al alumno seleccionado de la lista gestionada.
     */
    private void eliminarAlumnoSeleccionado() {
        if (alumnoSeleccionado != null) {
            int opcion = JOptionPane.showConfirmDialog(vista,
                "¿Desea eliminar al alumno con matrícula " + alumnoSeleccionado.getMatricula() + 
                "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                gestionAlumnos.eliminarAlumno(alumnoSeleccionado);
                actualizarVistaConAlumnos(gestionAlumnos.getListaAlumnos());
                limpiarCampos();
                alumnoSeleccionado = null;
            }
        } else {
            mostrarMensaje("No hay ningún alumno seleccionado para eliminar", "Ningún alumno seleccionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Actualiza la tabla de la vista principal con la lista de alumnos.
     * @param alumnos lista de alumnos a mostrar.
     */
    private void actualizarVistaConAlumnos(List<Alumno> alumnos) {
        String[] encabezados = {"Matrícula", "Apellido Paterno", "Apellido Materno", "Nombre", "Asignatura", "Calificación"};
        DefaultTableModel modeloTabla = new DefaultTableModel(encabezados, 0);

        for (Alumno alumno : alumnos) {
            for (Calificacion calificacion : alumno.getCalificaciones()) {
                modeloTabla.addRow(new Object[]{
                    alumno.getMatricula(),
                    alumno.getApellidoPaterno(),
                    alumno.getApellidoMaterno(),
                    alumno.getNombre(),
                    calificacion.getAsignatura(),
                    calificacion.getCalificacion()
                });
            }
        }

        vista.getTablaAlumnos().setModel(modeloTabla);
    }

    // ==========================
    // Operaciones de Archivos
    // ==========================

    /**
     * Carga un archivo CSV de alumnos y actualiza la vista con los datos cargados.
     */
    public void cargarArchivoAlumnos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        int seleccion = fileChooser.showOpenDialog(vista);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            List<Alumno> alumnos = gestionAlumnos.cargarArchivo(archivoSeleccionado.getAbsolutePath());
            actualizarVistaConAlumnos(alumnos);
        }
    }

    /**
     * Guarda la lista de alumnos en un archivo CSV.
     */
    public void guardarArchivoAlumnos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        fileChooser.setSelectedFile(new File("alumnos.csv"));

        int seleccion = fileChooser.showSaveDialog(vista);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            if (!archivoSeleccionado.getAbsolutePath().toLowerCase().endsWith(".csv")) {
                archivoSeleccionado = new File(archivoSeleccionado.getAbsolutePath() + ".csv");
            }
            gestionAlumnos.guardarListaDeAlumnos(archivoSeleccionado.getAbsolutePath());
            mostrarMensaje("Archivo guardado satisfactoriamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Crea un archivo PDF con la lista de alumnos.
     */
    private void crearPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF", "pdf"));
        if (fileChooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            if (!archivoSeleccionado.getAbsolutePath().toLowerCase().endsWith(".pdf")) {
                archivoSeleccionado = new File(archivoSeleccionado.getAbsolutePath() + ".pdf");
            }

            try {
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(archivoSeleccionado));
                documento.open();

                documento.add(new Paragraph("Lista de Alumnos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD)));
                documento.add(new Paragraph(" "));
                
                PdfPTable tabla = new PdfPTable(6);
                tabla.addCell("Matrícula");
                tabla.addCell("Apellido Paterno");
                tabla.addCell("Apellido Materno");
                tabla.addCell("Nombre");
                tabla.addCell("Asignatura");
                tabla.addCell("Calificación");

                for (Alumno alumno : gestionAlumnos.getListaAlumnos()) {
                    for (Calificacion calificacion : alumno.getCalificaciones()) {
                        tabla.addCell(alumno.getMatricula());
                        tabla.addCell(alumno.getApellidoPaterno());
                        tabla.addCell(alumno.getApellidoMaterno());
                        tabla.addCell(alumno.getNombre());
                        tabla.addCell(calificacion.getAsignatura());
                        tabla.addCell(String.valueOf(calificacion.getCalificacion()));
                    }
                }

                documento.add(tabla);
                documento.close();
                mostrarMensaje("Archivo PDF creado satisfactoriamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                mostrarMensaje("Error al crear el archivo PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ==========================
    // Utilidades
    // ==========================

    /**
     * Limpia los campos de texto y resetea los valores en la vista.
     */
    private void limpiarCampos() {
        vista.getCampoMatricula().setText("");
        vista.getCampoApellidoPaterno().setText("");
        vista.getCampoApellidoMaterno().setText("");
        vista.getCampoNombre().setText("");
        vista.getCampoAsignatura().setSelectedIndex(0);
        vista.getCampoCalificacion().setValue(0);
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * @param mensaje el texto a mostrar.
     * @param titulo el título de la ventana.
     * @param tipoMensaje el tipo de mensaje (INFORMATION_MESSAGE, WARNING_MESSAGE, ERROR_MESSAGE).
     */
    private void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, titulo, tipoMensaje);
    }

    /**
     * Verifica si los campos están vacíos.
     * @return true si algún campo está vacío, false en caso contrario.
     */
    private boolean camposEstanVacios() {
        return vista.getCampoMatricula().getText().isEmpty() ||
               vista.getCampoApellidoPaterno().getText().isEmpty() ||
               vista.getCampoApellidoMaterno().getText().isEmpty() ||
               vista.getCampoNombre().getText().isEmpty();
    }

    /**
     * Valida si ya existe un alumno con la misma matrícula y asignatura.
     * @param matricula la matrícula a verificar.
     * @param asignatura la asignatura a verificar.
     * @return true si hay un duplicado, false en caso contrario.
     */
    private boolean validarDuplicado(String matricula, String asignatura) {
        if (gestionAlumnos.existeAlumnoConMatriculaYAsignatura(matricula, asignatura)) {
            mostrarMensaje("Ya existe un alumno con la misma matrícula y asignatura", "Duplicado encontrado", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }
}