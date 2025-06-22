package pds.controlador;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import pds.dominio.*;

class ControladorTestUnitarias {

    private Controlador controlador;

    @Test
    void testEsAlumnoYEsCreador() {
        Alumno alumno = new Alumno("Pepe", "López", "123", "pepe@pds.com", "clave");
        CreadorCurso creador = new CreadorCurso("Ana", "Gómez", "456", "ana@pds.com", "clave");

        controlador.setUsuario(alumno);
        assertTrue(controlador.esAlumno());
        assertFalse(controlador.esCreador());

        controlador.setUsuario(creador);
        assertFalse(controlador.esAlumno());
        assertTrue(controlador.esCreador());
    }

    @Test
    void testEstaLogueado() {
        controlador.setUsuario(null);
        assertFalse(controlador.estaLogueado());

        Alumno alumno = new Alumno("Carlos", "Ruiz", "000", "carlos@pds.com", "123");
        controlador.setUsuario(alumno);
        assertTrue(controlador.estaLogueado());
    }

    @Test
    void testGetCursosImportadosDelAlumno() {
        Alumno alumno = new Alumno("Luis", "Martínez", "600000000", "luis@pds.com", "pass");
        Curso curso1 = new Curso("Java", new CreadorCurso(), new LinkedList<>(), Dificultad.FACIL, "Curso básico");
        Curso curso2 = new Curso("Python", new CreadorCurso(), new LinkedList<>(), Dificultad.NORMAL, "Curso intermedio");
        alumno.agregarCursoImportado(curso1);
        alumno.agregarCursoImportado(curso2);

        controlador.setUsuario(alumno);
        List<Curso> cursos = controlador.getCursosImportadosDelAlumno();
        assertEquals(2, cursos.size());
        assertTrue(cursos.contains(curso1));
        assertTrue(cursos.contains(curso2));
    }

    @Test
    void testIniciarCursoConEstrategiaSecuencial() {
        Alumno alumno = new Alumno("Test", "Alumno", "999", "test@pds.com", "1234");
        controlador.setUsuario(alumno);

        Pregunta p1 = new PreguntaFlashCard("Hola", "Hello");
        BloqueContenido bloque = new BloqueContenido("Bloque 1");
        bloque.agregarPregunta(p1);

        Curso curso = new Curso("Curso de Prueba", new CreadorCurso(), List.of(bloque), Dificultad.FACIL, "Descripción");

        RealizarCurso realizar = new RealizarCurso(curso, bloque, new EstrategiaSecuencial(), alumno);

        assertEquals(curso, realizar.getCurso());
        assertEquals(bloque, realizar.getBloque());
        assertEquals(alumno, realizar.getAlumno());
        assertNotNull(realizar.getEstrategia());
    }

    @Test
    void testIniciarCursoConEstrategiaAleatoria() {
        Alumno alumno = new Alumno("Test", "Alumno", "999", "test@pds.com", "1234");
        controlador.setUsuario(alumno);

        Pregunta p = new PreguntaFlashCard("¿Hola?", "Hello");
        BloqueContenido bloque = new BloqueContenido("Bloque");
        bloque.agregarPregunta(p);

        Curso curso = new Curso("Curso Aleatorio", new CreadorCurso(), List.of(bloque), Dificultad.NORMAL, "Desc");
        RealizarCurso realizar = new RealizarCurso(curso, bloque, new EstrategiaAleatoria(), alumno);

        assertNotNull(realizar.getEstrategia());
        assertTrue(realizar.getEstrategia() instanceof EstrategiaAleatoria);
    }

    @Test
    void testIniciarCursoConEstrategiaRepeticion() {
        Alumno alumno = new Alumno("Test", "Alumno", "999", "test@pds.com", "1234");
        controlador.setUsuario(alumno);

        Pregunta p = new PreguntaFlashCard("¿Hola?", "Hello");
        BloqueContenido bloque = new BloqueContenido("Bloque");
        bloque.agregarPregunta(p);

        Curso curso = new Curso("Curso Repetición", new CreadorCurso(), List.of(bloque), Dificultad.DIFICIL, "Desc");
        RealizarCurso realizar = new RealizarCurso(curso, bloque, new EstrategiaRepeticionEspaciada(), alumno);

        assertNotNull(realizar.getEstrategia());
        assertTrue(realizar.getEstrategia() instanceof EstrategiaRepeticionEspaciada);
    }

    @Test
    void testCompartirCurso(@TempDir File tempDir) throws Exception {
        Curso curso = new Curso("Compartir", new CreadorCurso(), new LinkedList<>(), Dificultad.FACIL, "Descripción");
        File archivo = new File(tempDir, "curso_exportado.json");

        boolean resultado = controlador.compartirCurso(curso, archivo);

        assertTrue(resultado);
        assertTrue(archivo.exists());
        assertTrue(Files.readString(archivo.toPath()).contains("Compartir"));
    }

    @Test
    void testFinalizarCursoMarcaCompletado() {
        Alumno alumno = new Alumno("Test", "Alumno", "999", "test@pds.com", "1234");
        controlador.setUsuario(alumno);

        Pregunta p1 = new PreguntaFlashCard("Hola", "Hello");
        BloqueContenido bloque = new BloqueContenido("Bloque 1");
        bloque.agregarPregunta(p1);

        Curso curso = new Curso("Curso 1", new CreadorCurso(), List.of(bloque), Dificultad.FACIL, "Descripción");

        RealizarCurso realizar = new RealizarCurso(curso, bloque, new EstrategiaSecuencial(), alumno);

        realizar.setCompletado(true);
        assertTrue(realizar.isCompletado());
    }

    @Test
    void testObtenerEstadisticasDeAlumno() {
        Alumno alumno = new Alumno("Carmen", "Soto", "111", "carmen@pds.com", "clave");
        controlador.setUsuario(alumno);

        Estadisticas estad = controlador.obtenerEstadisticas();

        assertNotNull(estad);
        assertEquals(alumno, estad.getAlumno());
    }

}
