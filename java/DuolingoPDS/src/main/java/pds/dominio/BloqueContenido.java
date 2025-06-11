package pds.dominio;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BloqueContenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bloque_id") // FK en Pregunta
    private List<Pregunta> preguntas = new ArrayList<>();

    public BloqueContenido() {
        // Requerido por Jackson y JPA
    }

    public BloqueContenido(String nombreBloque) {
        this.nombre = nombreBloque;
    }

    public Long getId() {
        return id;
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

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    public void eliminarPregunta(Pregunta pregunta) {
        preguntas.remove(pregunta);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
