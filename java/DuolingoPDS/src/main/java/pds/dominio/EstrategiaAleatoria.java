package pds.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstrategiaAleatoria implements Estrategia{

	@Override
	public List<Pregunta> getPreguntas(BloqueContenido bloque) {
		List<Pregunta> preguntas =  new ArrayList<>(bloque.getPreguntas());
		Collections.shuffle(preguntas);
		return preguntas;
	}

}
