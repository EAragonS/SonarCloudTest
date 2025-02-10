package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Representa la interfaz gráfica de usuario para el inicio de sesión.
 * Proporciona campos para ingresar el nombre de usuario y la contraseña,
 * así como botones para iniciar sesión o cancelar.
 */
public class VistaInicioSesion extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonIniciarSesion;
    private JButton botonCancelar;

    /**
     * Constructor que inicializa la ventana de inicio de sesión con su diseño y componentes.
     */
    public VistaInicioSesion() {
        setTitle("Iniciar Sesión");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.insets = new Insets(10, 10, 10, 10);

        // Etiqueta y campo de texto para el usuario
        JLabel etiquetaUsuario = new JLabel("Usuario:");
        etiquetaUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        restricciones.gridx = 0;
        restricciones.gridy = 0;
        panel.add(etiquetaUsuario, restricciones);

        campoUsuario = new JTextField(20);
        campoUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        restricciones.gridx = 1;
        restricciones.gridy = 0;
        panel.add(campoUsuario, restricciones);
        campoUsuario.setPreferredSize(new Dimension(250, 30));

        // Etiqueta y campo de texto para la contraseña
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        etiquetaContrasena.setFont(new Font("Arial", Font.BOLD, 14));
        restricciones.gridx = 0;
        restricciones.gridy = 1;
        panel.add(etiquetaContrasena, restricciones);

        campoContrasena = new JPasswordField(20);
        campoContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        restricciones.gridx = 1;
        restricciones.gridy = 1;
        panel.add(campoContrasena, restricciones);
        campoContrasena.setPreferredSize(new Dimension(250, 30));

        // Panel de botones para iniciar sesión o cancelar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        botonIniciarSesion = new JButton("Iniciar Sesión");
        botonIniciarSesion.setFont(new Font("Arial", Font.BOLD, 14));
        botonIniciarSesion.setPreferredSize(new Dimension(150, 40));
        panelBotones.add(botonIniciarSesion);

        botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        botonCancelar.setPreferredSize(new Dimension(150, 40));
        panelBotones.add(botonCancelar);

        restricciones.gridx = 0;
        restricciones.gridy = 2;
        restricciones.gridwidth = 2;
        restricciones.fill = GridBagConstraints.NONE;
        restricciones.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, restricciones);

        // Acción para el botón de cancelar
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(panel);
    }

    /**
     * Registra un ActionListener para el botón de iniciar sesión.
     *
     * @param listener el ActionListener que se ejecutará al presionar el botón.
     */
    public void escucaharIniciarSesion(ActionListener listener) {
        botonIniciarSesion.addActionListener(listener);
    }

    /**
     * Obtiene el nombre de usuario ingresado en el campo de texto.
     *
     * @return el nombre de usuario como una cadena de texto.
     */
    public String obtenerNombreUsuario() {
        return campoUsuario.getText();
    }

    /**
     * Obtiene la contraseña ingresada en el campo de contraseña.
     *
     * @return la contraseña como una cadena de texto.
     */
    public String obtenerContrasena() {
        return new String(campoContrasena.getPassword());
    }

    /**
     * Método principal para ejecutar la ventana de inicio de sesión de forma independiente.
     *
     * @param args argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VistaInicioSesion interfaz = new VistaInicioSesion();
                interfaz.setVisible(true);
            }
        });
    }
}