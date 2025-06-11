package pds.dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
public class PreguntaFlashCard extends Pregunta {
	
    private String respuesta;

    
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
