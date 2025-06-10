package pds.dominio;

public class PreguntaFlashCard extends Pregunta{
	String respuesta;

	public PreguntaFlashCard(String enunciado, String respuesta) {
		super(enunciado);
		this.respuesta = respuesta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	@Override
	public boolean isCorrecta(String respuesta) {
		return true;
	}
	

}
