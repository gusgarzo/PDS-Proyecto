package pds.dominio;

import java.util.List;

public class RealizarCurso {
	
	private Integer id;
	private Curso curso;
	private BloqueContenido bloque;
	private Estrategia estrategia;
	private Alumno alumno;
	
	public RealizarCurso(Curso curso, BloqueContenido bloque, Estrategia estrategia, Alumno alumno) {
		this.curso = curso;
		this.bloque = bloque;
		this.estrategia = estrategia;
		this.alumno = alumno;
	}
	
	public boolean avanzarBloque() {
	    int indiceActual = curso.getBloques().indexOf(bloque);
	    if (indiceActual < 0 || indiceActual >= curso.getBloques().size() - 1) {
	        return false; // No hay más bloques
	    }
	    bloque = curso.getBloques().get(indiceActual + 1);
	    return true;
	}
	public boolean puedeAvanzarBloque() {
	    int indiceActual = curso.getBloques().indexOf(bloque);
	    if ( indiceActual <= curso.getBloques().size() - 1) {
	        return true; // No hay más bloques
	    }
	    return false;
	}
	public boolean retrocederBloque() {
	    int indiceActual = curso.getBloques().indexOf(bloque);
	    if (indiceActual <= 0 ) {
	        return false; 
	    }
	    bloque = curso.getBloques().get(indiceActual -1);
	    return true;
	}
	public boolean puedeRetrocederBloque() {
	    int indiceActual = curso.getBloques().indexOf(bloque);
	    if ( indiceActual > 0) {
	        return true; 
	    }
	    return false;
	}
	
	public int getNumBloques() {
		return curso.getBloques().size();
	}
	public int getIndBloqueActual() {
		return curso.getBloques().indexOf(bloque);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public BloqueContenido getBloque() {
		return bloque;
	}
	public void setBloque(BloqueContenido bloque) {
		this.bloque = bloque;
	}
	public Estrategia getEstrategia() {
		return estrategia;
	}
	public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public List<Pregunta> crearListaPreguntas(){
		return estrategia.getPreguntas(bloque);
	}
	
	
	
	
}
