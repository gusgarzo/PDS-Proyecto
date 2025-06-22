package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@DiscriminatorValue("ALUMNO")
public class Alumno extends Usuario {

  
	


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Alumno_CursosImportados",
        joinColumns = @JoinColumn(name = "Alumno_id"),
        inverseJoinColumns = @JoinColumn(name = "Curso_id")
    )
    private List<Curso> cursosImportados = new ArrayList<>();


	
    @OneToOne(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Estadisticas estadisticas;

	public Alumno() { }
    public Alumno(String nombre, String apellidos, String telefono, String correo, String contrasena) {
        super(nombre,  apellidos,  telefono,  correo,  contrasena);
        //Para evitar problemas con JPA inicializamos las listas de cursos
        this.cursosImportados = new ArrayList<>();
        this.estadisticas = new Estadisticas();
        this.estadisticas.setAlumno(this); 
    }


	public List<Curso> getCursosImportados() {
	    return cursosImportados;
	}
	
	public void agregarCursoImportado(Curso curso) {
		if (!cursosImportados.contains(curso))
			cursosImportados.add(curso);
	}
	
    
	public Estadisticas getEstadisticas() {
		return estadisticas;
	}
	public void setEstadisticas(Estadisticas estadisticas) {
		this.estadisticas = estadisticas;
	}
	public RealizarCurso iniciarCurso(Curso curso, String estrategiaNombre) {
	    Estrategia estrategia;
	    if ("Secuencial".equalsIgnoreCase(estrategiaNombre)) {
	        estrategia = new EstrategiaSecuencial();
	    } else if ("Repetición Espaciada".equalsIgnoreCase(estrategiaNombre)) {
	    	estrategia = new EstrategiaRepeticionEspaciada();
	    } else if ("Aleatoria".equalsIgnoreCase(estrategiaNombre)) {
	    	estrategia = new EstrategiaAleatoria();
	    } else {
	        throw new IllegalArgumentException("Estrategia no válida: " + estrategiaNombre);
	    }
		RealizarCurso realizar = new RealizarCurso(curso, curso.getBloques().get(0),estrategia, this);
		return realizar;
	}

    
   

}