package pds.controlador;

import java.util.List;

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

    public Curso crearCurso(String nombre, String descripcion, Dificultad dificultad) {
        if (usuarioActual instanceof CreadorCurso) {
            return ((CreadorCurso) usuarioActual).crearCurso(nombre, dificultad, descripcion);
        } else {
            throw new IllegalStateException("El usuario actual no es un creador de cursos.");
        }
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

}