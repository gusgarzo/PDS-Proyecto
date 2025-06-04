package pds.controlador;


import pds.dao.FactoriaDAO;
import pds.dao.IAdaptadorAlumnoDAO;
import pds.dao.IAdaptadorCreadorCursosDAO;
import pds.dao.IAdaptadorCursoDAO;
import pds.dao.IAdaptadorEstadisticasDAO;
import pds.dao.IAdaptadorUsuarioDAO;
import pds.dominio.*;

public enum Controlador {
	INSTANCE;
 
    private Usuario usuarioActual;

    private FactoriaDAO factoria;
    private IAdaptadorUsuarioDAO adaptadorUsuario;
    private IAdaptadorEstadisticasDAO adaptadorEstadisticas;
    private IAdaptadorAlumnoDAO adaptadorAlumno;
    private IAdaptadorCreadorCursosDAO adaptadorCreadorCursos;
    private IAdaptadorCursoDAO adaptadorCursos;
    private Controlador() {
    	 usuarioActual = null;
         try {
             factoria = FactoriaDAO.getInstancia();
         } catch (Exception e) {
             e.printStackTrace();
         }
        adaptadorAlumno = factoria.getAdaptadorAlumnoDAO();
        adaptadorCreadorCursos = factoria.getAdaptadorCreadorCursosDAO();
        adaptadorCursos = factoria.getAdaptadorCursoDAO();
        adaptadorUsuario = factoria.getAdaptadorUsuarioDAO();
        adaptadorEstadisticas = factoria.getAdaptadorEstadisticasDAO();

    }

   
    public boolean registrarUsuario(String nombre, String apellidos, String telefono, String correo, String contrasena, String tipoUsuario) {
       // if (adaptadorUsuario.existeUsuario(correo)) return false;

        Usuario nuevoUsuario = null;
        if (tipoUsuario.equalsIgnoreCase("Alumno")) {

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

        //repositorioUsuarios.registrarUsuario(nuevoUsuario);
        this.usuarioActual = nuevoUsuario;

        return true;
    }

    public boolean loginUsuario(String correo, String contrasena) {
        Usuario usuario = null;//repositorioUsuarios.buscarUsuarioPorCorreoYContrasena(correo, contrasena);
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
    public void setUsuario(Usuario usu) {
    	usuarioActual = usu;    }

  
}
