package pds.dominio;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "progreso_bloques")
public class RealizarCurso {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	 	
	 	@ManyToOne  
	    @JoinColumn(name = "curso_id")
	    private Curso curso;

	    @ManyToOne  
	    @JoinColumn(name = "bloque_id")
	    private BloqueContenido bloque;

	    @ManyToOne(cascade = CascadeType.PERSIST)
	    @JoinColumn(name = "estrategia_id")
	    private Estrategia estrategia;

	    @ManyToOne
	    @JoinColumn(name = "alumno_id")
	    private Alumno alumno;
	
	    Boolean completado;
	public RealizarCurso(Curso curso, BloqueContenido bloque, Estrategia estrategia, Alumno alumno) {
		this.curso = curso;
		this.bloque = bloque;
		this.estrategia = estrategia;
		this.alumno = alumno;
		this.completado = false;
	}
	public RealizarCurso() {
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
	public Float getPorcentajeCompletado() {
		return (Integer.valueOf(getIndBloqueActual()).floatValue()/Integer.valueOf(getNumBloques()).floatValue() )*100;
	}
	public Boolean isCompletado() {
		return completado;
	}
	public void setCompletado(Boolean completado) {
		this.completado = completado;
	}
	
	
	
	
	
}
