package pds.dominio;

public class PreguntaHuecos extends Pregunta{

	private String respuestaCorrecta;
	
	public PreguntaHuecos(String enunciado, String respuestaCorrecta) {
		super(enunciado);
		this.respuestaCorrecta = respuestaCorrecta;
	}
	public boolean isCorrecta(String respuesta) {
		if(respuesta == null || respuesta.isEmpty()) return false;
		return respuesta.trim().equalsIgnoreCase(respuestaCorrecta.trim());
	}
	
}
