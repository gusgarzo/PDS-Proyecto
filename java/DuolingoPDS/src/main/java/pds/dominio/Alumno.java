package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@DiscriminatorValue("ALUMNO")
public class Alumno extends Usuario {

  
    @OneToMany(cascade = CascadeType.ALL)
    private List<RealizarCurso> cursosEnProgreso = new ArrayList<>();


    private int cursosCompletados = 0;

 
    private int rachaDias = 0;


    private int tiempoTotalMinutos = 0;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Curso> cursosImportados = new ArrayList<>();
	
	
	@OneToMany
	private List<Curso> cursosRealizados = new ArrayList<>();


	public Alumno() { }
    public Alumno(String nombre, String apellidos, String telefono, String correo, String contrasena) {
        super(nombre,  apellidos,  telefono,  correo,  contrasena);
        this.cursosCompletados = 0;
        this.rachaDias = 0;
        this.tiempoTotalMinutos = 0;
        //Para evitar problemas con JPA inicializamos las listas de cursos
        this.cursosEnProgreso = new ArrayList<>();
        this.cursosImportados = new ArrayList<>();
    }
    



	public List<Curso> getCursosImportados() {
	    return cursosImportados;
	}
	
	public void agregarCursoImportado(Curso curso) {
	    cursosImportados.add(curso);
	}



	public List<RealizarCurso> getCursosEnProgreso() {
		return cursosEnProgreso;
	}

	public void setCursosEnProgreso(List<RealizarCurso> cursosEnProgreso) {
		this.cursosEnProgreso = cursosEnProgreso;
	}

	public int getCursosCompletados() {
		return cursosCompletados;
	}
	

	public void setCursosCompletados(int cursosCompletados) {
		this.cursosCompletados = cursosCompletados;
	}

	public List<Curso> getCursosRealizados() {
	    return cursosRealizados;
	}

	public void setCursosRealizados(List<Curso> cursosRealizados) {
	    this.cursosRealizados = cursosRealizados;
	}

	
	public int getRachaDias() {
		return rachaDias;
	}

	public void setRachaDias(int rachaDias) {
		this.rachaDias = rachaDias;
	}

	public int getTiempoTotalMinutos() {
		return tiempoTotalMinutos;
	}

	public void setTiempoTotalMinutos(int tiempoTotalMinutos) {
		this.tiempoTotalMinutos = tiempoTotalMinutos;
	}
    
	public RealizarCurso iniciarCurso(Curso curso, String estrategiaNombre) {
	    Estrategia estrategia;
	    if ("Secuencial".equals(estrategiaNombre)) {
	        estrategia = new EstrategiaSecuencial();
	    } else if ("Repetición Espaciada".equals(estrategiaNombre)) {
	    	estrategia = new EstrategiaRepeticionEspaciada();
	    } else if ("Aleatoria".equals(estrategiaNombre)) {
	    	estrategia = new EstrategiaAleatoria();
	    } else {
	        throw new IllegalArgumentException("Estrategia no válida: " + estrategiaNombre);
	    }
		RealizarCurso realizar = new RealizarCurso(curso, curso.getBloques().get(0),estrategia, this);
		return realizar;
	}
    
   

}