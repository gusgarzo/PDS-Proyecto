package pds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreadorCursoTest {

    private CreadorCurso brock;

    @BeforeEach
    public void setUp() {
        brock = new CreadorCurso("Brock", "Harrison", "999999999", "brock@poke.com", "onix123");
    }

    @Test
    public void testCrearUnCurso() {
        Curso curso = brock.crearCurso(
            "Pokémon tipo Roca",
            Dificultad.NORMAL,
            "Aprende sobre Geodude, Onix y Golem"
        );

        List<Curso> cursos = brock.getCursosCreados();

        assertEquals(1, cursos.size(), "Brock debería tener 1 curso creado");
        assertTrue(cursos.contains(curso), "El curso creado debe estar en la lista de cursos del creador");
        assertEquals(brock, curso.getCreador(), "El curso debería tener a Brock como creador");
    }

    @Test
    public void testCrearVariosCursos() {
        brock.crearCurso("Pokémon tipo Fuego", Dificultad.DIFICIL, "Entrena con Charizard");
        brock.crearCurso("Cocina Pokémon con Chansey", Dificultad.FACIL, "Recetas saludables para entrenadores");

        List<Curso> cursos = brock.getCursosCreados();

        assertEquals(2, cursos.size(), "Brock debería tener 2 cursos creados");
    }
}
