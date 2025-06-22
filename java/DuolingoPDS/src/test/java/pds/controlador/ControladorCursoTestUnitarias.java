package pds.controlador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import pds.dominio.*;

class ControladorCursoTestUnitarias {

    private final ControladorCurso controlador = ControladorCurso.INSTANCE;

    @AfterEach
    void limpiarEstadoCompartido() {
        controlador.setUsuarioActual(null);
    }
    
    @Test
    void testCrearPreguntaFlashCard() {
        PreguntaFlashCard p = controlador.creaPreguntaFC("¿Hola?", "Hello");
        assertEquals("¿Hola?", p.getEnunciado());
        assertEquals("Hello", p.getRespuesta());
    }

    @Test
    void testCrearPreguntaHuecos() {
        PreguntaHuecos p = controlador.creaPreguntaHuecos("Completa: El ___ es azul", "cielo");
        assertEquals("Completa: El ___ es azul", p.getEnunciado());
        assertEquals("cielo", p.getRespuestaCorrecta());
    }

    @Test
    void testCrearPreguntaTipoTest() {
        List<String> opciones = List.of("2", "3", "4");
        PreguntaTipoTest p = controlador.creaPreguntaTest("¿Cuánto es 2+2?", opciones, "4");

        assertEquals("¿Cuánto es 2+2?", p.getEnunciado());
        assertEquals("4", p.getRespuestaCorrecta());
        assertTrue(p.getRespuestas().contains("3"));
    }

    @Test
    void testAgregarBloqueCurso() {
        Curso curso = new Curso("Test Curso", new CreadorCurso(), List.of(), Dificultad.FACIL, "desc");

        controlador.agregarBloqueCurso(curso, "Bloque 1");

        assertEquals(1, curso.getBloques().size());
        assertEquals("Bloque 1", curso.getBloques().get(0).getNombre());
    }

    @Test
    void testAgregarPreguntaABloque() {
        Curso curso = new Curso("Test Curso", new CreadorCurso(), List.of(), Dificultad.FACIL, "desc");
        controlador.agregarBloqueCurso(curso, "Intro");

        Pregunta pregunta = new PreguntaFlashCard("¿Hola?", "Hello");
        controlador.agregarPreguntaBloque(curso, "Intro", pregunta);

        List<Pregunta> preguntas = curso.getBloques().get(0).getPreguntas();
        assertEquals(1, preguntas.size());
        assertEquals(pregunta, preguntas.get(0));
    }

    @Test
    void testAgregarBloqueCursoConNombreNuloOLimpioLanzaExcepcion() {
        Curso curso = new Curso("Curso", new CreadorCurso(), List.of(), Dificultad.FACIL, "desc");

        assertThrows(IllegalArgumentException.class, () -> controlador.agregarBloqueCurso(curso, ""));
        assertThrows(IllegalArgumentException.class, () -> controlador.agregarBloqueCurso(curso, null));
    }

    @Test
    void testAgregarPreguntaBloqueDatosInvalidosLanzaExcepcion() {
        Curso curso = new Curso("Curso", new CreadorCurso(), List.of(), Dificultad.FACIL, "desc");
        Pregunta pregunta = new PreguntaHuecos("¿Hola ___?", "mundo");

        assertThrows(IllegalArgumentException.class, () -> controlador.agregarPreguntaBloque(null, "Bloque", pregunta));
        assertThrows(IllegalArgumentException.class, () -> controlador.agregarPreguntaBloque(curso, null, pregunta));
        assertThrows(IllegalArgumentException.class, () -> controlador.agregarPreguntaBloque(curso, "Bloque", null));
    }
}
