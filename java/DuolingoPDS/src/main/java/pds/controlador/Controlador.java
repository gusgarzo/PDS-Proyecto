package pds.controlador;



import java.util.ArrayList;
import java.util.List;

import java.io.File;

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
            Alumno alumno = new Alumno(nombre, apellidos, telefono, correo, contrasena);
            Estadisticas estadisticas = new Estadisticas();
            estadisticas.setAlumno(alumno);     // ← Este método debe llamarse setUsuario o setAlumno según tu diseño final
            alumno.setEstadisticas(estadisticas);
            nuevoUsuario = alumno;
            repositorioUsuarios.registrarUsuario(alumno);
        }
        else if (tipoUsuario.equalsIgnoreCase("Creador de cursos")) {
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

            if (usuario instanceof Alumno) {
                Estadisticas estad = ((Alumno) usuario).getEstadisticas();
                if (estad == null) {
                    estad = new Estadisticas();
                    estad.setAlumno((Alumno) usuario);
                    ((Alumno) usuario).setEstadisticas(estad);

                    // Guardamos el alumno y, por cascade, se guarda también la estadística
                    repositorioUsuarios.actualizarUsuario(usuario);
                }

                estad.iniciarTiempo(); // ⏱ Inicia el cronómetro

            }
        }
        return usuario;
    }

	public void cerrarSesion() {
		if (usuarioActual instanceof Alumno) {
			Alumno alu = (Alumno) usuarioActual;
			Estadisticas estadisticas = alu.getEstadisticas();
			if (estadisticas != null) {
				estadisticas.finalizarTiempo();
				repositorioUsuarios.actualizarUsuario(alu);
			}
		}
		usuarioActual = null;
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
	        if (!alumno.getCursosImportados().contains(curso)) {
	            alumno.agregarCursoImportado(curso);
	        }
	        repositorioUsuarios.actualizarUsuario(alumno);
	        return curso;
	
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

    
    public RealizarCurso iniciarCurso(Curso curso, String estrategiaNombre, Usuario usuario) {
    	System.out.println(estrategiaNombre);
        return ((Alumno)usuario).iniciarCurso(curso, estrategiaNombre);
    }
    
    public List<Curso> getCursosImportadosDelAlumno() {
        if (!(usuarioActual instanceof Alumno)) return new ArrayList<>();
        return ((Alumno) usuarioActual).getCursosImportados();
    }

    public List<Curso> obtenerTodosLosCursos() {
        return RepositorioCurso.getInstancia().obtenerTodos();
    }

    public void registrarCursoRealizado(Curso curso) {
        if (!(usuarioActual instanceof Alumno)) return;
        repoRealizarCurso.registrarCursoRealizado((Alumno)usuarioActual, curso);
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


    public Estadisticas obtenerEstadisticas() {
        if (!(usuarioActual instanceof Alumno)) {
            throw new IllegalStateException("Solo los alumnos tienen estadísticas.");
        }

        Alumno alumno = (Alumno) usuarioActual;
        Estadisticas estad = alumno.getEstadisticas();

        if (estad == null) {
            estad = new Estadisticas();
            estad.setAlumno(alumno);
            alumno.setEstadisticas(estad);
            repositorioUsuarios.actualizarUsuario(alumno);
        }

        return estad;
    }

  /*  public void marcarCursoCompletado(Curso curso) {
        if (!(usuarioActual instanceof Alumno)) return;

        Alumno alumno = (Alumno) usuarioActual;
        Estadisticas estad = alumno.getEstadisticas();

        if (estad == null) {
            estad = new Estadisticas();
            estad.setAlumno(alumno);              // 🔥 ENLACE BIDIRECCIONAL
            alumno.setEstadisticas(estad);
        }

        estad.incrementarCursosCompletados();
        
        try {
            repositorioUsuarios.actualizarUsuario(alumno);
        } catch (Exception e) {
            System.err.println("Error al actualizar alumno:");
            e.printStackTrace();
            Throwable cause = e.getCause();
            while (cause != null) {
                System.err.println("Causa: " + cause.getMessage());
                cause = cause.getCause();
            }
        }
  
       }*/

}
