package pds.dominio;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PreguntaTipoTest.class, name = "test"),
    @JsonSubTypes.Type(value = PreguntaHuecos.class, name = "huecos"),
    @JsonSubTypes.Type(value = PreguntaFlashCard.class, name = "flashcard")
})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String enunciado;
    
  
    public Pregunta() {}

   
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
    
    public abstract boolean isCorrecta(String respuesta);


    public void setId(Integer id) {
        this.id = id;
    }

}
