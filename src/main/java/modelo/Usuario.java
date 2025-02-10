package modelo;

/**
 * Representa un usuario del sistema con un nombre de usuario y una contraseña.
 */
public class Usuario {

    private String nombreUsuario;
    private String contrasena;

    /**
     * Crea una instancia de la clase Usuario con un nombre de usuario y una contraseña proporcionados.
     *
     * @param nombreUsuario el nombre de usuario.
     * @param contrasena    la contraseña del usuario.
     */
    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return el nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece un nuevo nombre de usuario.
     *
     * @param nombreUsuario el nuevo nombre de usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece una nueva contraseña para el usuario.
     *
     * @param contrasena la nueva contraseña.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}