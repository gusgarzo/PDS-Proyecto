package pds.controlador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pds.dao.RepositorioCurso;
import pds.dao.RepositorioUsuarios;
import pds.dominio.*;

public class ControladorCursoTestIntegracion {

    private ControladorCurso controladorCurso;

    @BeforeEach
    public void setUp() {
        controladorCurso = ControladorCurso.INSTANCE;

        // Crear y loguear un creador de cursos
        CreadorCurso creador = new CreadorCurso("Ash", "Ketchum", "123", "ash@poke.com", "pikachu");
        RepositorioUsuarios.getInstancia().registrarUsuario(creador);
        controladorCurso.setUsuarioActual(creador);
    }

    @AfterEach
    public void borrarTodo() {
        RepositorioCurso.getInstancia().eliminarTodos();
        RepositorioUsuarios.getInstancia().eliminarTodos();
    }

    @Test
    public void testCrearCursoYAgregarBloquesYPreguntas() {
        Curso curso = controladorCurso.crearCurso("Curso Pokémon", "Aprende sobre Pokémon", Dificultad.NORMAL);
        assertNotNull(curso);
        assertEquals("Curso Pokémon", curso.getNombre());

        controladorCurso.agregarBloqueCurso(curso, "Bloque Inicial");
        BloqueContenido bloque = curso.getBloquePorNombre("Bloque Inicial");
        assertNotNull(bloque);
        assertEquals(0, bloque.getPreguntas().size());

        // Pregunta tipo test
        PreguntaTipoTest preguntaTest = controladorCurso.creaPreguntaTest(
            "¿Cuál es el tipo de Pikachu?",
            Arrays.asList("Fuego", "Eléctrico", "Agua"),
            "Eléctrico"
        );

        controladorCurso.agregarPreguntaBloque(curso, "Bloque Inicial", preguntaTest);

        // Pregunta huecos
        PreguntaHuecos preguntaHuecos = controladorCurso.creaPreguntaHuecos(
            "El Pokémon inicial de tipo fuego es ___.", "Charmander"
        );
        controladorCurso.agregarPreguntaBloque(curso, "Bloque Inicial", preguntaHuecos);

        // Pregunta flashcard
        PreguntaFlashCard preguntaFC = controladorCurso.creaPreguntaFC("¿Quién es el rival de Ash?", "Gary");
        controladorCurso.agregarPreguntaBloque(curso, "Bloque Inicial", preguntaFC);

        assertEquals(3, bloque.getPreguntas().size());
    }

    @Test
    public void testGuardarYRecuperarCurso() {
        Curso curso = controladorCurso.crearCurso("Curso Pokémon", "Detalles del mundo Pokémon", Dificultad.DIFICIL);
        controladorCurso.guardarCurso(curso);

        List<Curso> cursos = controladorCurso.obtenerMisCursos();
        assertEquals(1, cursos.size());
        assertEquals("Curso Pokémon", cursos.get(0).getNombre());
    }
    
    @Test
    public void testCrearCursoSinUsuario() {
        controladorCurso.setUsuarioActual(null);
        assertThrows(IllegalStateException.class, () -> 
            controladorCurso.crearCurso("Curso Inválido", "Descripción", Dificultad.NORMAL));
    }

    @Test
    public void testAgregarBloqueCursoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> 
            controladorCurso.agregarBloqueCurso(null, "Tema"));
        
        Curso curso = new Curso();
        assertThrows(IllegalArgumentException.class, () -> 
            controladorCurso.agregarBloqueCurso(curso, ""));
    }

    
    @Test
    public void testAgregarPreguntaABloqueInexistente() {
        Curso curso = new Curso("Curso sin bloques", new CreadorCurso(), new ArrayList<>(), Dificultad.FACIL, "desc");
        Pregunta p = new PreguntaHuecos("¿Pregunta?", "respuesta");
        assertThrows(IllegalArgumentException.class, () -> 
            controladorCurso.agregarPreguntaBloque(curso, "NoExiste", p));
    }
    
    @Test
    public void testGuardarCursoVacio() {
        controladorCurso.setUsuarioActual(new CreadorCurso("Ash", "Ketchum", "000", "ash@poke.com", "pikachu"));
        Curso curso = controladorCurso.crearCurso("Entrenamiento Pokémon", "Descripción", Dificultad.FACIL);
        assertDoesNotThrow(() -> controladorCurso.guardarCurso(curso));
    }


    @Test
    public void testRecuperarCursosTrasGuardar() {
    	CreadorCurso creador = new CreadorCurso("Misty", "Agua", "111", "misty@poke.com", "togepi");
    	RepositorioUsuarios.getInstancia().registrarUsuario(creador);
    	controladorCurso.setUsuarioActual(creador);                    

        Curso curso = controladorCurso.crearCurso("Curso Agua", "Curso sobre tipos agua", Dificultad.NORMAL);
        controladorCurso.guardarCurso(curso);

        List<Curso> cursos = controladorCurso.obtenerMisCursos();
        assertTrue(cursos.stream().anyMatch(c -> c.getNombre().equals("Curso Agua")));
    }


}
