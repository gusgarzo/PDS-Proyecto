package pds.dominio;

import jakarta.persistence.*;
import java.util.List;


public class Alumno extends Usuario {

  
    private List<Curso> cursosEnProgreso;


    private int cursosCompletados = 0;


    private int rachaDias = 0;


    private int tiempoTotalMinutos = 0;

    public Alumno(String nombre, String apellidos, String telefono, String correo, String contrasena) {
        super(nombre,  apellidos,  telefono,  correo,  contrasena);
        this.cursosCompletados = 0;
        this.rachaDias = 0;
        this.tiempoTotalMinutos = 0;
    }

   
}
