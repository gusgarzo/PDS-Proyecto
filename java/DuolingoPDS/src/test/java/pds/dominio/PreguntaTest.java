package pds.dominio;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class PreguntaTest {

    @Test
    void testRespuestaCorrectaPreguntaHuecos() {
        PreguntaHuecos pregunta = new PreguntaHuecos("The sun ___ in the east", "rises");
        assertTrue(pregunta.isCorrecta("rises"));
        assertTrue(pregunta.isCorrecta(" RISES ")); // mayúsculas y espacios
    }

    @Test
    void testRespuestaIncorrectaPreguntaHuecos() {
        PreguntaHuecos pregunta = new PreguntaHuecos("The moon ___ at night", "shines");
        assertFalse(pregunta.isCorrecta("glows"));
        assertFalse(pregunta.isCorrecta(""));
        assertFalse(pregunta.isCorrecta(null));
    }

    @Test
    void testRespuestaCorrectaPreguntaTest() {
        PreguntaTipoTest pregunta = new PreguntaTipoTest(
            "¿Cuál es la capital de Francia?",
            Arrays.asList("Madrid", "París", "Roma"),
            "París"
        );
        assertTrue(pregunta.isCorrecta("París"));
        assertTrue(pregunta.isCorrecta("parís"));
        assertTrue(pregunta.isCorrecta("  París  "));
    }

    @Test
    void testRespuestaIncorrectaPreguntaTest() {
        PreguntaTipoTest pregunta = new PreguntaTipoTest(
            "¿Cuál es la capital de Francia?",
            Arrays.asList("Madrid", "París", "Roma"),
            "París"
        );
        assertFalse(pregunta.isCorrecta("Roma"));
        assertFalse(pregunta.isCorrecta(""));
        assertFalse(pregunta.isCorrecta(null));
    }

    @Test
    void testRespuestaCorrectaFlashCard() {
        PreguntaFlashCard pregunta = new PreguntaFlashCard("water", "agua");
        assertTrue(pregunta.isCorrecta("Agua"));
        assertTrue(pregunta.isCorrecta("  AGUA "));
    }


}
