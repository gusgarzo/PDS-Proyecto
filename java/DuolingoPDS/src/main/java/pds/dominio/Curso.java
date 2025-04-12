package pds.dominio;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    private CreadorCurso creador;

    // Otros atributos y métodos necesarios

    public Curso() {}

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CreadorCurso getCreador() {
        return creador;
    }

    public void setCreador(CreadorCurso creador) {
        this.creador = creador;
    }
}
