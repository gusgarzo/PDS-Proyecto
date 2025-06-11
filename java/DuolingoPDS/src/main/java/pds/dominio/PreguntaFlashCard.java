package pds.dominio;

public class PreguntaFlashCard extends Pregunta {

    private String respuesta;

    // ✅ Constructor vacío requerido por Jackson
    public PreguntaFlashCard() {
        super();
    }


	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	@Override
	public boolean isCorrecta(String respuesta) {
		return true;
	}
	

    public PreguntaFlashCard(String enunciado, String respuesta) {
        super(enunciado);
        this.respuesta = respuesta;
    }


    public String getRespuesta() {
        return respuesta;
    }

   
}
