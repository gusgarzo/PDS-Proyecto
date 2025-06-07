package pds.dominio;

import jakarta.persistence.*;
import java.util.List;


public class Alumno extends Usuario {

  
    private List<RealizarCurso> cursosEnProgreso;


    private int cursosCompletados = 0;

 
    private int rachaDias = 0;


    private int tiempoTotalMinutos = 0;

    public Alumno(String nombre, String apellidos, String telefono, String correo, String contrasena) {
        super(nombre,  apellidos,  telefono,  correo,  contrasena);
        this.cursosCompletados = 0;
        this.rachaDias = 0;
        this.tiempoTotalMinutos = 0;
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
    
    
   
}
