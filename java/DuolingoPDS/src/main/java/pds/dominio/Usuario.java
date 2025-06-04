package pds.dominio;

import jakarta.persistence.*;


 public abstract class Usuario{ 
	private Long id;	
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String contrasena;
    public Usuario() {}
    

    public Usuario( String nombre, String apellidos, String telefono, String correo, String contrasena) {
		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.correo = correo;
		this.contrasena = contrasena;
	}


	// Getters y Setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
