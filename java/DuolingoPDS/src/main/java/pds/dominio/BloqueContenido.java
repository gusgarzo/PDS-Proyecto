package pds.dominio;

import java.util.ArrayList;
import java.util.List;

public class BloqueContenido {

    private String nombre;
    private String tema;
    private Curso curso;
    private List<Pregunta> preguntas;

    public BloqueContenido(String nombre, String tema, Curso curso) {
        this.nombre = nombre;
        this.tema = tema;
        this.curso = curso;
        this.preguntas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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
