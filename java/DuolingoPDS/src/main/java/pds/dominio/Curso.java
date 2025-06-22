package pds.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "cursos")
public class Curso {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @ManyToOne
	@JoinColumn(name = "autor_id")
    private CreadorCurso creador;

    @Enumerated(EnumType.STRING)
    private Dificultad dificultad;

   
    @JsonProperty("bloques_contenidos")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id")
    private List<BloqueContenido> bloques = new ArrayList<>();

    public Curso() {
        // Necesario para JPA y Jackson
    }

    public Curso(String nombre, CreadorCurso creador,
                 List<BloqueContenido> bloques, Dificultad dificultad, String descripcion) {
        this.nombre = nombre;
        this.creador = creador;
        this.bloques = new ArrayList<>(bloques);
        this.dificultad = dificultad;
        this.descripcion = descripcion;
    }

    // === Getters y setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CreadorCurso getCreador() {
        return creador;
    }

    public void setCreador(CreadorCurso creador) {
        this.creador = creador;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public List<BloqueContenido> getBloques() {
        return bloques;
    }

    public void setBloques(List<BloqueContenido> bloques) {
        this.bloques = bloques;
    }

    public void agregarBloque(BloqueContenido bloque) {
        bloques.add(bloque);
    }

    public BloqueContenido crearYAgregarBloque(String nombre) {
        BloqueContenido nuevoBloque = new BloqueContenido(nombre);
        agregarBloque(nuevoBloque);
        return nuevoBloque;
    }

    public void agregarPreguntaABloque(String nombreBloque, Pregunta pregunta) {
        BloqueContenido bloque = getBloquePorNombre(nombreBloque);
        if (bloque != null) {
            bloque.agregarPregunta(pregunta);
        } else {
            throw new IllegalArgumentException("Bloque no encontrado: " + nombreBloque);
        }
    }

    public BloqueContenido getBloquePorNombre(String nombre) {
        for (BloqueContenido bloque : bloques) {
            if (bloque.getNombre().equals(nombre)) {
                return bloque;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return nombre + " (" + dificultad + ")";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso)) return false;
        Curso curso = (Curso) o;
        return id != null && id.equals(curso.getId());
    }

    @Override
    public int hashCode() {
        return 31 + (id == null ? 0 : id.hashCode());
    }


}
