package pds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CursoTest {

    private Curso curso;
    private CreadorCurso creador;

    @BeforeEach
    public void setUp() {
        creador = new CreadorCurso("Brock", "Harrison", "999999999", "brock@poke.com", "onix123");
        curso = new Curso("Curso Pokémon Tipo Roca", creador, new ArrayList<>(), Dificultad.NORMAL, "Aprende sobre Geodude y Onix");
    }

    @Test
    public void testAgregarBloque() {
        BloqueContenido bloque = new BloqueContenido("Kanto Básico");
        curso.agregarBloque(bloque);

        assertEquals(1, curso.getBloques().size(), "Debe haber un bloque agregado");
        assertEquals("Kanto Básico", curso.getBloques().get(0).getNombre());
    }

    @Test
    public void testCrearYAgregarBloque() {
        BloqueContenido bloque = curso.crearYAgregarBloque("Kanto Avanzado");
        assertNotNull(bloque);
        assertEquals("Kanto Avanzado", bloque.getNombre());
        assertTrue(curso.getBloques().contains(bloque));
    }

    @Test
    public void testAgregarPreguntaABloqueExistente() {
        curso.crearYAgregarBloque("Kanto");
        PreguntaTipoTest pregunta = new PreguntaTipoTest();
        pregunta.setEnunciado("¿Qué tipo es Geodude?");
        curso.agregarPreguntaABloque("Kanto", pregunta);

        BloqueContenido bloque = curso.getBloquePorNombre("Kanto");
        assertEquals(1, bloque.getPreguntas().size(), "Debe haber una pregunta en el bloque");
        assertEquals("¿Qué tipo es Geodude?", bloque.getPreguntas().get(0).getEnunciado());
    }

    @Test
    public void testAgregarPreguntaABloqueInexistenteLanzaExcepcion() {
        PreguntaFlashCard pregunta = new PreguntaFlashCard();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            curso.agregarPreguntaABloque("Johto", pregunta);
        });

        assertTrue(ex.getMessage().contains("Bloque no encontrado"));
    }

    @Test
    public void testGetBloquePorNombre() {
        curso.crearYAgregarBloque("Johto");
        BloqueContenido bloque = curso.getBloquePorNombre("Johto");

        assertNotNull(bloque);
        assertEquals("Johto", bloque.getNombre());
    }

    @Test
    public void testEqualsYtoString() {
        curso.setId(1L);

        Curso otroCurso = new Curso();
        otroCurso.setId(1L);

        assertEquals(curso, otroCurso, "Dos cursos con el mismo ID deben ser iguales");
        assertTrue(curso.toString().contains("Curso Pokémon Tipo Roca"));
    }
}
