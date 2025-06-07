package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class Alumno extends Usuario {

  
    private List<Curso> cursosEnProgreso;


    private int cursosCompletados = 0;


    private int rachaDias = 0;


    private int tiempoTotalMinutos = 0;


	private ArrayList cursosImportados;

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




   

}