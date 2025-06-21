package pds.controlador;

import java.util.List;

import jakarta.persistence.EntityManager;
import pds.dao.RepositorioCurso;
import pds.dominio.*;


public enum ControladorCurso {
	INSTANCE;
	
	private Usuario usuarioActual;
	
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
	public void setUsuarioActual(Usuario usuario) {
		usuarioActual=usuario;
	}

	public PreguntaTipoTest creaPreguntaTest(String enunciado, List<String> respuestas, String respuestaCorrecta) {
		return new PreguntaTipoTest(enunciado, respuestas, respuestaCorrecta);
	}

	public PreguntaHuecos creaPreguntaHuecos(String enunciado, String respuestaCorrecta) {
		return new PreguntaHuecos(enunciado,respuestaCorrecta);
	}

	public PreguntaFlashCard creaPreguntaFC(String enunciado, String respuesta) {
		return new PreguntaFlashCard(enunciado,respuesta);
	}

    /*public Curso crearCurso(String nombre, String descripcion, Dificultad dificultad) {
        if (usuarioActual instanceof CreadorCurso) {
            return ((CreadorCurso) usuarioActual).crearCurso(nombre, dificultad, descripcion);
        } else {
            throw new IllegalStateException("El usuario actual no es un creador de cursos.");
        }
    }*/
	
	public Curso crearCurso(String nombre, String descripcion, Dificultad dificultad) {
	    if (!(usuarioActual instanceof CreadorCurso)) {
	        throw new IllegalStateException("El usuario actual no es un creador de cursos.");
	    }

	    CreadorCurso creador = (CreadorCurso) usuarioActual;

	    // Recuperamos el CreadorCurso gestionado por JPA
	    EntityManager em = RepositorioCurso.getInstancia().getEntityManager();
	    Curso curso = null;
	    try {
	        em.getTransaction().begin();

	        CreadorCurso creadorGestionado = em.createQuery(
	            "SELECT c FROM CreadorCurso c WHERE c.correo = :correo", CreadorCurso.class)
	            .setParameter("correo", creador.getCorreo())
	            .getSingleResult();

	        curso = creadorGestionado.crearCurso(nombre, dificultad, descripcion);
	        em.persist(curso);

	        em.getTransaction().commit();
	    } catch (Exception ex) {
	        em.getTransaction().rollback();
	        ex.printStackTrace();
	    } finally {
	        em.close();
	    }

	    return curso;
	}


    public void agregarBloqueCurso(Curso curso, String nombre) {
        if (curso == null || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre y tema del bloque no pueden estar vacíos.");
        }

        curso.crearYAgregarBloque(nombre);
    }

    public void agregarPreguntaBloque(Curso curso, String nombreBloque, Pregunta pregunta) {
        if (curso == null || nombreBloque == null || pregunta == null) {
            throw new IllegalArgumentException("Datos inválidos para agregar pregunta");
        }

        curso.agregarPreguntaABloque(nombreBloque, pregunta);
    }
    
    public void guardarCurso(Curso curso) {
        RepositorioCurso.getInstancia().guardarCurso(curso);
    }
    
    public List<Curso> obtenerMisCursos() {
        if (!(usuarioActual instanceof CreadorCurso)) return List.of();
        return RepositorioCurso.getInstancia().obtenerPorCreador(usuarioActual.getCorreo());
    }


}