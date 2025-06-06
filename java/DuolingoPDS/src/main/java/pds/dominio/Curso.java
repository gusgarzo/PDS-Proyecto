package pds.dominio;

import jakarta.persistence.*;
import java.util.List;


public class Curso {

 
    private Long id;

    private String nombre;
    
    private String descripcion;
    
    private CreadorCurso creador;
    
    
    private Dificultad dificultad;
    
    private List <BloqueContenido> bloques;

    public Curso(Long id, String nombre, CreadorCurso creador,List<BloqueContenido> bloques, Dificultad dificultad, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.creador = creador;
		this.bloques = bloques;
		this.dificultad = dificultad;
		this.descripcion = descripcion;
	}

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

	public List<BloqueContenido> getBloques() {
		return bloques;
	}

	public void setBloques(List<BloqueContenido> bloques) {
		this.bloques = bloques;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}
    
    public void agregarBloque(BloqueContenido bloque) {
        bloques.add(bloque);
    }
    
    public BloqueContenido crearYAgregarBloque(String nombre) {
        BloqueContenido nuevoBloque = new BloqueContenido(nombre);
        bloques.add(nuevoBloque);
        return nuevoBloque;
    }

}
