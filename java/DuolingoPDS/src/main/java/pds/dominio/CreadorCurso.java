package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class CreadorCurso extends Usuario {
    private List<Curso> cursosCreados = new ArrayList<>();
    
    public CreadorCurso() {}

    public CreadorCurso(String string, String string2, String string3, String string4, String string5) {
		super(string,string2,string3,string4,string5);
	}

	public Curso crearCurso(String nombre, Dificultad dificultad, String descripcion) {
        Curso curso = new Curso(
            generarId(),
            nombre,
            this,
            new ArrayList<>(),
            dificultad,
            descripcion
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

