package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class CreadorCurso extends Usuario {

    private List<Curso> cursosCreados;

    public CreadorCurso() {
        this.cursosCreados = new ArrayList<>();
    }


}
