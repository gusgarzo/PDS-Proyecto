package pds.dominio;

import java.util.ArrayList;
import java.util.List;

public class BloqueContenido {

    private String nombre;
    private String tema;
    private List<Pregunta> preguntas;

    public BloqueContenido(String nombre) {
        this.nombre = nombre;
        this.preguntas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return nombre + " - " + tema;
    }
}
