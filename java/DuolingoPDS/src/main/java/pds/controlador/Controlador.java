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
	private RepositorioRealizarCurso repoRealizarCurso;
    private Controlador() {
		usuarioActual = null;
		repositorioUsuarios = new RepositorioUsuarios();
		repositorioCursos = new RepositorioCurso();
		repoRealizarCurso = new RepositorioRealizarCurso();
   
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

    public boolean loginUsuario(String correo, String contrasena) {
        Usuario usuario = null;

        // Simulación: si se usa este correo, se crea un creador
        if (correo.equals("2") && contrasena.equals("2")) {
            usuario = new CreadorCurso();
        }

        if (usuario != null) {
            this.usuarioActual = usuario;
            ControladorCurso.INSTANCE.setUsuarioActual(usuario);
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
    	usuarioActual = usu;    
    }
    
    
    public Curso importarCurso(File archivo) {
        /*if (!estaLogueado() || !(usuarioActual instanceof Alumno)) {
            return null; 
        }*/

        ObjectMapper mapper = new ObjectMapper();
        try {
            Curso curso = mapper.readValue(archivo, Curso.class);
            repositorioCursos.guardarCurso(curso);
            
            return curso;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public RealizarCurso iniciarCurso(Curso curso, String estrategiaNombre, Usuario usuario) {
       /* if (usuarioActual instanceof Alumno alumno) {
	        // Comprobar si ya ha realizado el curso
	        List<Curso> yaRealizados = repoRealizarCurso.obtenerCursosRealizadosPor((Alumno) usuario);
	        for (Curso realizado : yaRealizados) {
	            if (realizado.equals(curso)) {
	                // Ya lo ha hecho
	                return null;
	            }
            }*/

            // Si no lo ha empezado, se crea una nueva instancia
            RealizarCurso nuevo = ((Alumno) usuario).iniciarCurso(curso, estrategiaNombre);
            repoRealizarCurso.registrarCursoRealizado((Alumno) usuario, curso); // o guardar RealizarCurso si se persistiera
            return nuevo;
    //    }
    }
    
    public List<Curso> obtenerTodosLosCursos() {
        return RepositorioCurso.getInstancia().obtenerTodos();
    }

    public void registrarCursoRealizado(Curso curso) {
        if (!(usuarioActual instanceof Alumno)) return;
        repoRealizarCurso.registrarCursoRealizado((Alumno)usuarioActual, curso);
    }

}
