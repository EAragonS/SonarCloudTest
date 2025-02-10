package iniciador;

import javax.swing.SwingUtilities;
import vista.VistaInicioSesion;
import controlador.ControladorInicioSesion;

/**
 * Clase principal para iniciar la aplicación. Se encarga de crear la vista de inicio de sesión
 * y asociarla con su respectivo controlador.
 * <p>
 * Utiliza el método {@link SwingUtilities#invokeLater(Runnable)} para garantizar que la 
 * inicialización de la interfaz gráfica se ejecute en el Event Dispatch Thread (EDT).
 * </p>
 */
public class IniciarAplicacion {

    /**
     * Método principal de la aplicación. Inicia el flujo principal del sistema creando la vista
     * de inicio de sesión y su controlador asociado.
     *
     * @param args los argumentos de línea de comandos (no se utilizan en esta implementación).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear la vista de inicio de sesión
            VistaInicioSesion vistaInicio = new VistaInicioSesion();

            // Crear el controlador de inicio de sesión, que gestionará las transiciones
            new ControladorInicioSesion(vistaInicio);

            // Mostrar la ventana de inicio de sesión
            vistaInicio.setVisible(true);
        });
    }
}