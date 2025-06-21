package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("CREADOR")
public class CreadorCurso extends Usuario {
	
    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> cursosCreados = new ArrayList<>();
    public CreadorCurso(String nombre, String apellidos, String telefono, String correo, String contrasena) {
    	super(nombre,  apellidos,  telefono,  correo,  contrasena);
        this.cursosCreados = new ArrayList<>();
    }

    public CreadorCurso() {
		// TODO Auto-generated constructor stub
	}
  
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

   
	@JsonIgnore
	public List<Curso> getCursosCreados() {
        return cursosCreados;
    }

    
}

