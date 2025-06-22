package pds.controlador;



import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import pds.dao.*;
import pds.dominio.*;

public enum Controlador {
	INSTANCE;
 
    private Usuario usuarioActual;
	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioCurso repositorioCursos;
	private RepositorioRealizarCurso repositorioRealizarCurso;
    private Controlador() {
		usuarioActual = null;
		repositorioUsuarios = new RepositorioUsuarios();
		repositorioCursos = new RepositorioCurso();
		repositorioRealizarCurso = new RepositorioRealizarCurso();
   
    }

   
    public boolean registrarUsuario(String nombre, String apellidos, String telefono, String correo, String contrasena, String tipoUsuario) {
       // if (adaptadorUsuario.existeUsuario(correo)) return false;

        Usuario nuevoUsuario = null;
        if (tipoUsuario.equalsIgnoreCase("Alumno")) {
        	nuevoUsuario = new Alumno(nombre, apellidos, telefono, correo, contrasena);
        	repositorioUsuarios.registrarUsuario(nuevoUsuario);
        } else if (tipoUsuario.equalsIgnoreCase("Creador de cursos")) {
            nuevoUsuario = new CreadorCurso(nombre, apellidos, telefono, correo, contrasena);
            repositorioUsuarios.registrarUsuario(nuevoUsuario);
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

    public Usuario loginUsuario(String nombre, String contrasena) {
        Usuario usuario = repositorioUsuarios.autenticar(nombre, contrasena);


        if (usuario != null) {
            this.usuarioActual = usuario;
            ControladorCurso.INSTANCE.setUsuarioActual(usuario);
        }
		return usuario;
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
    	usuarioActual = usu;    
    }
    
	public Curso importarCurso(File archivo) {
	    if (!(usuarioActual instanceof Alumno)) {
	        return null;
	    }
	
	    Alumno alumno = (Alumno) usuarioActual;
	    ObjectMapper mapper = new ObjectMapper();
	
	    try {
	        Curso curso = mapper.readValue(archivo, Curso.class);
	
	        curso = repositorioCursos.guardarCurso(curso); // IMPORTANTE: que devuelva el objeto persistido con ID
	
	        alumno.agregarCursoImportado(curso);
	
	        repositorioUsuarios.actualizarUsuario(alumno);
	
	        return curso;
	
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

    
    public RealizarCurso iniciarCurso(Curso curso, String estrategiaNombre, Usuario usuario) {
    	System.out.println(estrategiaNombre);
        RealizarCurso realCurso =  ((Alumno)usuario).iniciarCurso(curso, estrategiaNombre);
        repositorioRealizarCurso.guardarRealizarCurso(realCurso);
        return realCurso;
    }
    
    public List<Curso> getCursosImportadosDelAlumno() {
        if (!(usuarioActual instanceof Alumno)) return new ArrayList<>();
        return ((Alumno) usuarioActual).getCursosImportados();
    }

    public List<Curso> obtenerTodosLosCursos() {
        return RepositorioCurso.getInstancia().obtenerTodos();
    }

   


    public boolean compartirCurso(Curso cursoSeleccionado, File archivo) {
        if (cursoSeleccionado == null || archivo == null) {
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, cursoSeleccionado);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void guardarEstadoCurso(RealizarCurso realCurso) {
    	repositorioRealizarCurso.actualizarRealizarCurso(realCurso);
    }
    public List<RealizarCurso> getCursosComenzados(){
    	List<RealizarCurso> cursos = repositorioRealizarCurso.recuperarCursosEmpezados((Alumno)usuarioActual);
    	System.out.println(cursos.size());
    	return cursos;
    }

}
