package pds.dominio;

import java.util.List;

public interface Estrategia {
	
	public List<Pregunta> getPreguntas(BloqueContenido bloque);
	
}
