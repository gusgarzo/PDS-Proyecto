package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CreadorCursos")
public class CreadorCurso extends Usuario {

    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL)
    private List<Curso> cursosCreados;

    public CreadorCurso() {
        this.cursosCreados = new ArrayList<>();
    }


}
