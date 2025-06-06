package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class CreadorCurso extends Usuario {
    private List<Curso> cursosCreados = new ArrayList<>();

    public Curso crearCurso(String nombre, String descripcion, Dificultad dificultad) {
        Curso curso = new Curso(
            generarId(), // o algún generador si no usas BD aún
            nombre,
            this,
            new ArrayList<>(),
            dificultad
        );
        cursosCreados.add(curso);
        return curso;
    }

    public List<Curso> getCursosCreados() {
        return cursosCreados;
    }

    private Long generarId() {
        return (long) (cursosCreados.size() + 1);
    }
}

