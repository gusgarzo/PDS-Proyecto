package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class CreadorCurso extends Usuario {
    private List<Curso> cursosCreados = new ArrayList<>();
    
    public CreadorCurso() {}

	public Curso crearCurso(String nombre, Dificultad dificultad, String descripcion) {
        Curso curso = new Curso(
            nombre,
            this,
            new ArrayList<>(),
            dificultad,
            descripcion
        );
        cursosCreados.add(curso);
        return curso;
	}

    public CreadorCurso(String nombre, String apellidos, String telefono, String correo, String contrasena) {
    	super(nombre,  apellidos,  telefono,  correo,  contrasena);
        this.cursosCreados = new ArrayList<>();
    }

    public List<Curso> getCursosCreados() {
        return cursosCreados;
    }

    
}

