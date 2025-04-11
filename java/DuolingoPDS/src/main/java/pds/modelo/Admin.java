package pds.modelo;

public class Admin extends Usuario {
	private String permisos;
    
	public Admin(String nombreUsuario, String permisos) {
		super(nombreUsuario);
		this.permisos = permisos;
	}
	
	public String getPermisos() {
		return permisos;
	}
	
	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}
}
