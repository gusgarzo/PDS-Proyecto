package pds.dominio;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadisticasTest {

    private Estadisticas estadisticas;

    @BeforeEach
    void setUp() {
        estadisticas = new Estadisticas();
    }

    @Test
    void testRegistrarRachaCorrecta() {
        estadisticas.registrarRespuesta(true);
        estadisticas.registrarRespuesta(true);
        estadisticas.registrarRespuesta(true);

        assertEquals(3, estadisticas.getrachaActualPreguntasCorrectas());
        assertEquals(3, estadisticas.getmejorRachaPreguntasCorrectas());
    }

    @Test
    void testRomperRacha() {
        estadisticas.registrarRespuesta(true);
        estadisticas.registrarRespuesta(true);
        estadisticas.registrarRespuesta(false); // rompe la racha

        assertEquals(0, estadisticas.getrachaActualPreguntasCorrectas());
        assertEquals(2, estadisticas.getmejorRachaPreguntasCorrectas());
    }

    @Test
    void testTiempoUsoConSesion() throws InterruptedException {
        estadisticas.iniciarTiempo();
        Thread.sleep(1000); // 1 segundo
        estadisticas.finalizarTiempo();

        assertTrue(estadisticas.getTiempoTotalConSesionActual() >= 1000);
    }

    @Test
    void testIncrementarCursosCompletados() {
        assertEquals(0, estadisticas.getCursosCompletados());
        estadisticas.incrementarCursosCompletados();
        estadisticas.incrementarCursosCompletados();
        assertEquals(2, estadisticas.getCursosCompletados());
    }

    @Test
    void testSesionActivaSumaTiempoCorrecto() throws InterruptedException {
        estadisticas.iniciarTiempo();
        Thread.sleep(500);
        long tiempoParcial = estadisticas.getTiempoTotalConSesionActual();
        assertTrue(tiempoParcial >= 500);

        Thread.sleep(500);
        long tiempoTotal = estadisticas.getTiempoTotalConSesionActual();
        assertTrue(tiempoTotal >= 1000);
    }
}
