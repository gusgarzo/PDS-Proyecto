package pds.dominio;

public abstract class Pregunta {
	
	private Integer id;
	
	private String enunciado;
	
	public Pregunta(String enunciado) {
        this.enunciado = enunciado;
    }
	public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
    
    public Integer getId() {
        return id;
    } 
    
    public abstract boolean isCorrecta(String respuesta);
}
