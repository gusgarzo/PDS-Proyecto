package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class CreadorCurso extends Usuario {

    private List<Curso> cursosCreados;

    public CreadorCurso(String nombre, String apellidos, String telefono, String correo, String contrasena) {
    	super(nombre,  apellidos,  telefono,  correo,  contrasena);
        this.cursosCreados = new ArrayList<>();
    }


}
