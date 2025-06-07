package pds.dominio;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo" // Este campo debe existir en el JSON
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PreguntaTipoTest.class, name = "test"),
    @JsonSubTypes.Type(value = PreguntaHuecos.class, name = "huecos"),
    @JsonSubTypes.Type(value = PreguntaFlashCard.class, name = "flashcard")
})
public abstract class Pregunta {

    private Integer id;
    private String enunciado;

    // ✅ Constructor vacío necesario para Jackson
    public Pregunta() {}

    // Constructor normal
    public Pregunta(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
