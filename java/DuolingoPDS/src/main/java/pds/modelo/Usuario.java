package pds.modelo;

public abstract class Usuario {
    private Long id;
    private String nombreUsuario;
    
    public Usuario(String nombreUsuario) {
    	super();
    	this.nombreUsuario = nombreUsuario;
    }
    
    public String getNombreUsuario() {
		return nombreUsuario;
	}
    
    public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}