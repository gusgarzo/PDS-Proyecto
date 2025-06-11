package pds.dominio;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BloqueContenido {


    private String nombre;

  

    private List<Pregunta> preguntas;

   

    @JsonIgnoreProperties(ignoreUnknown = true)
    public BloqueContenido(String nombreBloque) {
        this.nombre = nombreBloque;
        this.preguntas = new ArrayList<>();
    }
    public BloqueContenido() {
        // Requerido por Jackson
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreBloque) {
        this.nombre = nombreBloque;
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
        return nombre ;
       
    }
}
