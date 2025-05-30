package pds.controlador;

import pds.dominio.*;
import java.io.File;
import pds.dao.RepositorioUsuariosJPA;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Controlador {

    private static Controlador instancia = null;

    private final RepositorioUsuariosJPA repositorioUsuarios;
    private Usuario usuarioActual;

    private Controlador() {
        this.repositorioUsuarios = new RepositorioUsuariosJPA();
        this.usuarioActual = null;
    }

    public static Controlador getInstancia() {
        if (instancia == null) {
            instancia = new Controlador();
        }
        return instancia;
    }

    // --- Gesti�n de usuarios ---

    public boolean registrarUsuario(String nombre, String apellidos, String telefono, String correo, String contrasena, String tipoUsuario) {
        if (repositorioUsuarios.existeUsuario(correo)) return false;

        Usuario nuevoUsuario;
        if (tipoUsuario.equalsIgnoreCase("Alumno")) {
            nuevoUsuario = new Alumno();
        } else if (tipoUsuario.equalsIgnoreCase("Creador de cursos")) {
            nuevoUsuario = new CreadorCurso();
        } else {
            return false;
        }

        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena(contrasena);

        repositorioUsuarios.registrarUsuario(nuevoUsuario);
        this.usuarioActual = nuevoUsuario;

        return true;
    }

    public boolean loginUsuario(String correo, String contrasena) {
        Usuario usuario = repositorioUsuarios.buscarUsuarioPorCorreoYContrasena(correo, contrasena);
        if (usuario != null) {
            this.usuarioActual = usuario;
            return true;
        }
        return false;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean estaLogueado() {
        return usuarioActual != null;
    }

    public boolean esAlumno() {
        return usuarioActual instanceof Alumno;
    }

    public boolean esCreador() {
        return usuarioActual instanceof CreadorCurso;
    }

    // --- L�gica para otros objetos persistentes en el futuro ---
    // public void crearCurso(...)
    // public List<Curso> getCursosDelCreador()
    
    public boolean importarCurso(File archivo) {
        if (!estaLogueado() || !(usuarioActual instanceof Alumno)) return false;

        try {
            ObjectMapper mapper = new ObjectMapper();
            Curso curso = mapper.readValue(archivo, Curso.class);

            if (curso.getNombre() == null || curso.getNombre().isEmpty()) {
                System.err.println("Nombre de curso inválido");
                return false;
            }

            Alumno alumno = (Alumno) usuarioActual;
            alumno.agregarCursoImportado(curso);

            repositorioUsuarios.actualizarUsuario(alumno); // guarda alumno y curso

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
