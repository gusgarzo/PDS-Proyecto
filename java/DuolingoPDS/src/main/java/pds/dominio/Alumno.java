package pds.dominio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Alumno")
public class Alumno extends Usuario {

    @ManyToMany
    private List<Curso> cursosEnProgreso;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Curso> cursosImportados;

    @Column(nullable = false)
    private int cursosCompletados = 0;

    @Column(nullable = false)
    private int rachaDias = 0;

    @Column(nullable = false)
    private int tiempoTotalMinutos = 0;

    public Alumno() {
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

    // Getters/setters de otros campos si los necesitás
}
