package pds.modelo;

public class Cliente extends Usuario {
    private String direccion;
    
    public Cliente(String nombreUsuario, String direccion) {
    	super(nombreUsuario);
    	this.direccion = direccion;
    }
    
    public String getDireccion() {
		return direccion;
	}
    
    public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}