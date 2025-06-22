package pds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlumnoTest {

    private Alumno alumno;
    private Curso curso;

    @BeforeEach
    public void setUp() {
        alumno = new Alumno("Ash", "Ketchum", "123456789", "ash@poke.com", "pikachu");
        curso = new Curso();
        curso.setId((long) 1); 
        curso.setNombre("Aprende con Pikachu");
    }

    @Test
    public void testAshTieneEstadisticas() {
        assertEquals("Ash", alumno.getNombre(), "El nombre del alumno debería ser Ash");
        assertNotNull(alumno.getEstadisticas(), "Ash debería tener estadísticas inicializadas");
        assertEquals(alumno, alumno.getEstadisticas().getAlumno(), "Las estadísticas deben estar ligadas a Ash");
    }

    @Test
    public void testCapturaDeCursoImportado() {
        alumno.agregarCursoImportado(curso);
        List<Curso> cursos = alumno.getCursosImportados();
        assertEquals(1, cursos.size(), "Ash debería tener un curso importado");
        assertTrue(cursos.contains(curso), "El curso de Pikachu debería estar en la Pokédex... digo, en la lista");
    }


    @Test
    public void testIniciarCursoKantoConEstrategiaSecuencial() {
        BloqueContenido bloque = new BloqueContenido();
        bloque.setNombre("Kanto Básico");

        curso.setBloques(List.of(bloque));

        RealizarCurso realizar = alumno.iniciarCurso(curso, "Secuencial");

        assertNotNull(realizar);
        assertEquals(curso, realizar.getCurso());
        assertEquals(bloque, realizar.getBloque());
        assertEquals("EstrategiaSecuencial", realizar.getEstrategia().getClass().getSimpleName());
    }

    @Test
    public void testLanzarPokeballConEstrategiaDesconocida() {
        curso.setBloques(List.of(new BloqueContenido()));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            alumno.iniciarCurso(curso, "UltraInstinto");
        });

        assertTrue(exception.getMessage().contains("Estrategia no válida"),
                "Ash no puede usar una estrategia que no ha aprendido aún");
    }
}
