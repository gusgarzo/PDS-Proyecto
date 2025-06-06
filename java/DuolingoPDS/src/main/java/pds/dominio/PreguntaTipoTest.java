package pds.dominio;

import java.util.List;

public class PreguntaTipoTest  extends Pregunta{
	private List<String> respuestas;
	private String respuestaCorrecta;
	public PreguntaTipoTest(String enunciado, List<String> respuestas, String respuestaCorrecta) {
		super(enunciado);
		this.respuestas = respuestas;
		this.respuestaCorrecta = respuestaCorrecta;
	}
	public boolean isCorrecta(String respuesta) {
		if(respuesta == null || respuesta.isEmpty()) return false;
		return respuesta.trim().equalsIgnoreCase(respuestaCorrecta.trim());
	}
	
	
	public List<String> getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(List<String> respuestas) {
		this.respuestas = respuestas;
	}
	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}
	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}
	
	

}
