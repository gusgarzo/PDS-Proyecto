package pds.dominio;

public class RealizarCurso {
	
	private Integer id;
	private Curso curso;
	private BloqueContenido bloque;
	private EstrategiaPreguntas estrategia;
	private Alumno alumno;
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
	public EstrategiaPreguntas getEstrategia() {
		return estrategia;
	}
	public void setEstrategia(EstrategiaPreguntas estrategia) {
		this.estrategia = estrategia;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	public RealizarCurso(Curso curso, BloqueContenido bloque, EstrategiaPreguntas estrategia, Alumno alumno) {
		this.curso = curso;
		this.bloque = bloque;
		this.estrategia = estrategia;
		this.alumno = alumno;
	}
	
	
	
	
	
}
