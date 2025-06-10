package pds.dominio;

import java.util.List;

public class EstrategiaSecuencial implements Estrategia{

	@Override
	public List<Pregunta> getPreguntas(BloqueContenido bloque) {		
		return bloque.getPreguntas();
	}
	
}
