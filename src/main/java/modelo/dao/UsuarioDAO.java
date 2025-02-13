package modelo.dao;

import modelo.Usuario;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar el acceso a datos de los usuarios, con soporte para encriptación MD5.
 */
public class UsuarioDAO {

    private List<Usuario> listaUsuarios = new ArrayList<>();

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario a buscar.
     * @return El Usuario si se encuentra, null en caso contrario.
     */
    public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }
    
    public List<Usuario> getListaUsuarios() {
    return listaUsuarios;
}


    /**
     * Carga usuarios desde un archivo CSV.
     *
     * @param rutaArchivo Ruta del archivo de entrada.
     */
    public void cargarUsuarios(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    String nombreUsuario = datos[0];
                    String contrasenaEncriptada = datos[1];
                    Usuario usuario = new Usuario(nombreUsuario, contrasenaEncriptada);
                    listaUsuarios.add(usuario);
                }
            }
            System.out.println("Usuarios cargados exitosamente desde: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    /**
     * Exporta los usuarios a un archivo CSV.
     *
     * @param rutaArchivo Ruta del archivo de salida.
     */
    public void exportarUsuarios(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Usuario usuario : listaUsuarios) {
                bw.write(usuario.getNombreUsuario() + "," + usuario.getContrasena());
                bw.newLine();
            }
            System.out.println("Usuarios exportados exitosamente a: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al exportar usuarios: " + e.getMessage());
        }
    }

    /**
     * Agrega un usuario a la lista. La contraseña se encripta usando MD5 antes de agregarla.
     *
     * @param usuario Usuario a agregar.
     */
    public void agregarUsuario(Usuario usuario) {
        String contrasenaEncriptada = encriptarMD5(usuario.getContrasena());
        usuario.setContrasena(contrasenaEncriptada);
        listaUsuarios.add(usuario);
    }

    /**
     * Método para encriptar una cadena usando el algoritmo MD5.
     *
     * @param entrada Cadena de texto a encriptar.
     * @return Cadena encriptada en formato hexadecimal.
     */
    private String encriptarMD5(String entrada) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(entrada.getBytes());

            // Convertir los bytes en una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña en MD5", e);
        }
    }

    /**
     * Valida si una contraseña ingresada coincide con la contraseña encriptada de un usuario.
     *
     * @param nombreUsuario Nombre del usuario.
     * @param contrasena    Contraseña sin encriptar a validar.
     * @return true si la contraseña coincide, false en caso contrario.
     */
    public boolean validarContrasena(String nombreUsuario, String contrasena) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                String contrasenaEncriptada = encriptarMD5(contrasena);
                return usuario.getContrasena().equals(contrasenaEncriptada);
            }
        }
        return false;
    }
}