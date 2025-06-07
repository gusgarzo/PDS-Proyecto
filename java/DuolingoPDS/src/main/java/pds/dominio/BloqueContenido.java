package pds.dominio;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BloqueContenido {

    private String nombreBloque;
    private String tema;
    private List<Pregunta> preguntas;

   

    @JsonIgnoreProperties(ignoreUnknown = true)
    public BloqueContenido(String nombreBloque) {
        this.nombreBloque = nombreBloque;
        this.preguntas = new ArrayList<>();
    }
    public BloqueContenido() {
        // Requerido por Jackson
    }

    public String getNombreBloque() {
        return nombreBloque;
    }

    public void setNombre(String nombreBloque) {
        this.nombreBloque = nombreBloque;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    /**
     * Añade una pregunta al bloque.
     */
    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    /**
     * Elimina una pregunta del bloque.
     */
    public void eliminarPregunta(Pregunta pregunta) {
        preguntas.remove(pregunta);
    }

    @Override
    public String toString() {
        return nombreBloque + " - " + tema;
    }
}
