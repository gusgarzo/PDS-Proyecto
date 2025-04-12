package pds.dominio;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Alumno")
public class Alumno extends Usuario {

    @ManyToMany
    private List<Curso> cursosEnProgreso;

    @Column(nullable = false)
    private int cursosCompletados = 0;

    @Column(nullable = false)
    private int rachaDias = 0;

    @Column(nullable = false)
    private int tiempoTotalMinutos = 0;

    public Alumno() {
        // Estos valores ya se inicializan arriba, pero podés hacerlo aquí también si preferís
        this.cursosCompletados = 0;
        this.rachaDias = 0;
        this.tiempoTotalMinutos = 0;
    }

    // Getters y setters si los necesitás
}
