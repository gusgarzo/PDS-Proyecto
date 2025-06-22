package pds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BloqueContenidoTest {

    @Test
    void testAgregarVariasPreguntas() {
        BloqueContenido bloque = new BloqueContenido("Región Kanto");

        PreguntaTipoTest p1 = new PreguntaTipoTest();
        p1.setEnunciado("¿Cuál es el tipo de Pikachu?");
        
        PreguntaHuecos p2 = new PreguntaHuecos();
        p2.setEnunciado("Charmander evoluciona a ____.");

        PreguntaFlashCard p3 = new PreguntaFlashCard();
        p3.setEnunciado("¿Qué Pokémon es el número 001 en la Pokédex?");

        bloque.agregarPregunta(p1);
        bloque.agregarPregunta(p2);
        bloque.agregarPregunta(p3);

        assertEquals(3, bloque.getPreguntas().size(), "Debe haber 3 preguntas en el bloque");
    }

    @Test
    void testEliminarPregunta() {
        BloqueContenido bloque = new BloqueContenido("Kanto Avanzado");

        PreguntaTipoTest p1 = new PreguntaTipoTest();
        p1.setEnunciado("¿Quién es el líder del gimnasio de Ciudad Plateada?");

        bloque.agregarPregunta(p1);
        bloque.eliminarPregunta(p1);

        assertTrue(bloque.getPreguntas().isEmpty(), "La lista de preguntas debe estar vacía tras eliminar");
    }

    @Test
    void testToStringDevuelveNombre() {
        BloqueContenido bloque = new BloqueContenido("Johto Básico");
        assertEquals("Johto Básico", bloque.toString(), "El toString debe devolver el nombre del bloque");
    }
}
