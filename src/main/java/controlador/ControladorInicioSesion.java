package controlador;

import modelo.dao.UsuarioDAO;
import vista.VistaInicioSesion;
import vista.VistaPrincipal;
import modelo.Usuario;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * Controlador para manejar la lógica de inicio de sesión.
 */
public class ControladorInicioSesion {

    /**
     * Vista de inicio de sesión asociada a este controlador.
     */
    private VistaInicioSesion vista;

    /**
     * Modelo de acceso a datos para los usuarios.
     */
    private UsuarioDAO modeloUsuario;

    /**
     * Ruta del archivo CSV que contiene los datos de los usuarios.
     */
    private static final String RUTA_ARCHIVO_CSV = "/Users/ellysxd/NetBeansProjects/RefactorizacionSistemaCalificaciones/datos/usuarios.csv";

    /**
     * Constructor que inicializa el controlador con la vista de inicio de sesión.
     * @param vista es la interfaz gráfica de inicio de sesión.
     */
    public ControladorInicioSesion(VistaInicioSesion vista) {
        this.vista = vista;
        this.modeloUsuario = new UsuarioDAO();
        this.modeloUsuario.cargarUsuarios(RUTA_ARCHIVO_CSV);
        this.vista.escucaharIniciarSesion(this::manejarBotonIniciarSesion);
    }

    // ==========================
    // Métodos principales
    // ==========================

    /**
     * Maneja el evento de clic en el botón "Iniciar Sesión".
     * @param e evento de acción generado por el botón.
     */
    private void manejarBotonIniciarSesion(ActionEvent e) {
        String nombreUsuario = vista.obtenerNombreUsuario();
        String contrasena = vista.obtenerContrasena();

        if (autenticarUsuario(nombreUsuario, contrasena)) {
            JOptionPane.showMessageDialog(vista, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            vista.setVisible(false);
            vista.dispose();

            VistaPrincipal nuevaVista = new VistaPrincipal();
            nuevaVista.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Autentica las credenciales del usuario verificándolas en el modelo.
     * @param nombreUsuario nombre del usuario.
     * @param contrasena contraseña del usuario.
     * @return true si las credenciales son correctas, false de lo contrario.
     */
    boolean autenticarUsuario(String nombreUsuario, String contrasena) {
        return modeloUsuario.validarContrasena(nombreUsuario, contrasena);
    }
}