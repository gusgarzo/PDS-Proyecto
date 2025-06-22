package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@DiscriminatorValue("ALUMNO")
public class Alumno extends Usuario {

  
    @OneToMany(cascade = CascadeType.ALL)
    private List<RealizarCurso> cursosEnProgreso = new ArrayList<>();


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Alumno_CursosImportados",
        joinColumns = @JoinColumn(name = "Alumno_id"),
        inverseJoinColumns = @JoinColumn(name = "Curso_id")
    )
    private List<Curso> cursosImportados = new ArrayList<>();

    @OneToMany
    @JoinTable(
        name = "Alumno_CursosRealizados",
        joinColumns = @JoinColumn(name = "Alumno_id"),
        inverseJoinColumns = @JoinColumn(name = "Curso_id")
    )
    private List<Curso> cursosRealizados = new ArrayList<>();

	
    @OneToOne(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Estadisticas estadisticas;

	public Alumno() { }
    public Alumno(String nombre, String apellidos, String telefono, String correo, String contrasena) {
        super(nombre,  apellidos,  telefono,  correo,  contrasena);
        //Para evitar problemas con JPA inicializamos las listas de cursos
        this.cursosEnProgreso = new ArrayList<>();
        this.cursosImportados = new ArrayList<>();
        this.estadisticas = new Estadisticas();
        this.estadisticas.setAlumno(this); 
    }


	public List<Curso> getCursosImportados() {
	    return cursosImportados;
	}
	
	public void agregarCursoImportado(Curso curso) {
	    if (!cursosImportados.contains(curso)) {
	        cursosImportados.add(curso);
	    }
	}

	public List<RealizarCurso> getCursosEnProgreso() {
		return cursosEnProgreso;
	}

	public void setCursosEnProgreso(List<RealizarCurso> cursosEnProgreso) {
		this.cursosEnProgreso = cursosEnProgreso;
	}


	public List<Curso> getCursosRealizados() {
	    return cursosRealizados;
	}

	public void setCursosRealizados(List<Curso> cursosRealizados) {
	    this.cursosRealizados = cursosRealizados;
	}
	
    
	public Estadisticas getEstadisticas() {
		return estadisticas;
	}
	public void setEstadisticas(Estadisticas estadisticas) {
		this.estadisticas = estadisticas;
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