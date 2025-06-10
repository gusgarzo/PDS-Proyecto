package pds.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstrategiaRepeticionEspaciada implements Estrategia {

    @Override
    public List<Pregunta> getPreguntas(BloqueContenido bloque) {
        List<Pregunta> original = bloque.getPreguntas();
        if (original.isEmpty()) return original;

        List<Pregunta> resultado = new ArrayList<>();
        int repeticiones = 2; // Cada pregunta se repite 2 veces

        for (int i = 0; i < repeticiones; i++) {
            for (Pregunta p : original) {
                resultado.add(p);
            }
            // Mezclar después de cada ciclo para intercalar mejor (opcional)
            Collections.shuffle(resultado);
        }
        // Opcional: Mezclar final para mayor aleatoriedad
        Collections.shuffle(resultado);
        return resultado;
    }
}

