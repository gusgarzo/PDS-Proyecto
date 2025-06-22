
package pds.controlador;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.databind.ObjectMapper;

import pds.dao.RepositorioCurso;
import pds.dao.RepositorioRealizarCurso;
import pds.dao.RepositorioUsuarios;
import pds.dominio.*;

public class ControladorTestIntegracion {

    private Controlador controlador;

    @BeforeEach
    public void setUp() {
        controlador = Controlador.INSTANCE;
        controlador.setUsuario(null);
    }

	@AfterEach
	public void borrarTodo() {
		RepositorioRealizarCurso.getInstancia().eliminarTodos();
	    RepositorioCurso.getInstancia().eliminarTodos();
	    RepositorioUsuarios.getInstancia().eliminarTodos();
	    controlador.setUsuario(null);
	}
    
    @Test
    public void testLoginYRegistro() {
        boolean registrado = controlador.registrarUsuario("Juan", "Pérez", "123", "juan@correo.com", "clave", "Alumno");
        assertTrue(registrado);
        Usuario u = controlador.loginUsuario("Juan", "clave");
        assertNotNull(u);
        assertTrue(controlador.estaLogueado());
    }

    @Test
    public void testCerrarSesionYEstadisticas() {
        controlador.registrarUsuario("Ana", "López", "456", "ana@correo.com", "clave", "Alumno");
        controlador.loginUsuario("ana@correo.com", "clave");
        Alumno a = (Alumno) controlador.getUsuarioActual();
        assertNotNull(a.getEstadisticas());

        controlador.cerrarSesion();
        assertNull(controlador.getUsuarioActual());
    }

    @Test
    public void testObtenerEstadisticas() {
        controlador.registrarUsuario("Luis", "Martínez", "789", "luis@correo.com", "clave", "Alumno");
        controlador.loginUsuario("luis@correo.com", "clave");
        Estadisticas estad = controlador.obtenerEstadisticas();
        assertNotNull(estad);
        assertEquals("luis@correo.com", estad.getAlumno().getCorreo());
    }

    @Test
    public void testImportarCursoDesdeArchivo(@TempDir File tempDir) throws Exception {
        controlador.registrarUsuario("Carlos", "Ruiz", "000", "carlos@pds.com", "123", "Alumno");
        controlador.loginUsuario("carlos@pds.com", "123");
        Alumno alumno = (Alumno) controlador.getUsuarioActual();

        CreadorCurso creador = new CreadorCurso("Autor", "Del Curso", "999", "autor@pds.com", "clave");
        RepositorioUsuarios.getInstancia().registrarUsuario(creador); 
        Curso cursoOriginal = new Curso("Curso de Integración", creador, new ArrayList<>(), Dificultad.FACIL, "Descripción");

        File archivo = new File(tempDir, "curso_integracion.json");
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(archivo, cursoOriginal);

        Curso cursoImportado = controlador.importarCurso(archivo);

        assertNotNull(cursoImportado, "El curso importado no debe ser null");
        assertEquals("Curso de Integración", cursoImportado.getNombre());
        assertTrue(alumno.getCursosImportados().contains(cursoImportado), "El alumno debe tener el curso importado en su lista");
    }

    @Test
    public void testCompartirCurso(@TempDir File tempDir) throws Exception {
        controlador.registrarUsuario("Elena", "Sanz", "321", "elena@correo.com", "clave", "Creador de cursos");
        controlador.loginUsuario("elena@correo.com", "clave");

        Curso curso = new Curso("Compartir", new CreadorCurso(), new ArrayList<>(), Dificultad.FACIL, "Descripción");
        File archivo = new File(tempDir, "curso_exportado.json");

        boolean resultado = controlador.compartirCurso(curso, archivo);

        assertTrue(resultado);
        assertTrue(archivo.exists());
    }

    @Test
    public void testIniciarYFinalizarCurso() {
        // Registro de alumno
        controlador.registrarUsuario("Ash", "Ketchum", "123", "ash@poke.com", "pikachu", "Alumno");
        controlador.loginUsuario("ash@poke.com", "pikachu");
        Alumno alumno = (Alumno) controlador.getUsuarioActual();

        // Registro de creador
        controlador.registrarUsuario("Profesor", "Oak", "000", "oak@poke.com", "paleta", "Creador de cursos");
        controlador.loginUsuario("oak@poke.com", "paleta");
        CreadorCurso creador = (CreadorCurso) controlador.getUsuarioActual();

        // Crear curso de Pokémon
        Curso curso = new Curso("Curso Pokémon", creador, new ArrayList<>(), Dificultad.FACIL, "Conocimientos básicos de Pokémon");
        BloqueContenido bloque = curso.crearYAgregarBloque("Introducción");

        // Pregunta tipo test
        List<String> opciones = List.of("Charmander", "Squirtle", "Bulbasaur", "Pikachu");
        bloque.agregarPregunta(new PreguntaTipoTest("¿Cuál es un Pokémon de tipo fuego?", opciones, "Charmander"));

        // Pregunta flashcard
        bloque.agregarPregunta(new PreguntaFlashCard("¿Quién es el Pokémon número 25?", "Pikachu"));

        // Pregunta huecos
        bloque.agregarPregunta(new PreguntaHuecos("El Pokémon más conocido es _____.", "Pikachu"));

        curso = RepositorioCurso.getInstancia().guardarCurso(curso);

        // Volver al alumno
        controlador.loginUsuario("ash@poke.com", "pikachu");

        // Iniciar el curso
        RealizarCurso rc = controlador.iniciarCurso(curso, "secuencial", alumno);
        assertNotNull(rc);

        // Comprobaciones de respuestas
        List<Pregunta> preguntas = rc.crearListaPreguntas();
        assertEquals(3, preguntas.size());

        for (Pregunta p : preguntas) {
            if (p instanceof PreguntaTipoTest) {
                assertTrue(p.isCorrecta("Charmander"));
                assertFalse(p.isCorrecta("Squirtle"));
            } else if (p instanceof PreguntaHuecos) {
                assertTrue(p.isCorrecta("Pikachu"));
                assertFalse(p.isCorrecta("Bulbasaur"));
            } else if (p instanceof PreguntaFlashCard) {
                // No se comprueba su corrección, es flashcard
                assertEquals("¿Quién es el Pokémon número 25?", p.getEnunciado());
            }
        }

        // Finalizar curso
        controlador.finalizarCurso(rc);
        assertTrue(rc.isCompletado());
    }


    @Test
    public void testGetCursosImportadosDelAlumno() {
        controlador.registrarUsuario("Laura", "Ríos", "999", "laura@correo.com", "clave", "Alumno");
        controlador.loginUsuario("laura@correo.com", "clave");
        Alumno alumno = (Alumno) controlador.getUsuarioActual();

        Curso curso1 = new Curso("Java", new CreadorCurso(), new ArrayList<>(), Dificultad.FACIL, "Descripción");
        Curso curso2 = new Curso("Python", new CreadorCurso(), new ArrayList<>(), Dificultad.NORMAL, "Descripción");

        alumno.agregarCursoImportado(curso1);
        alumno.agregarCursoImportado(curso2);

        List<Curso> cursos = controlador.getCursosImportadosDelAlumno();
        assertEquals(2, cursos.size());
        assertTrue(cursos.contains(curso1));
        assertTrue(cursos.contains(curso2));
    }
}
