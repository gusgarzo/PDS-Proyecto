package pds.dominio;

import jakarta.persistence.*;
import java.util.List;


public class Curso {

 
    private Long id;

    private String nombre;


    private CreadorCurso creador;

    // Otros atributos y m�todos necesarios

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
