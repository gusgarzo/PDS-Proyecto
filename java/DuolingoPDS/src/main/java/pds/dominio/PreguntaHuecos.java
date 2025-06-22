package pds.dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
public class PreguntaHuecos extends Pregunta {

    private String respuestaCorrecta;

    // Constructor vacío requerido por Jackson
    public PreguntaHuecos() {
        super();
    }

    public PreguntaHuecos(String enunciado, String respuestaCorrecta) {
        super(enunciado);
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public boolean isCorrecta(String respuesta) {
        if (respuesta == null || respuesta.isEmpty()) return false;
        return respuesta.trim().equalsIgnoreCase(respuestaCorrecta.trim());
    }

    // Getters y setters requeridos por Jackson
    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}
