package pds.dominio;

import jakarta.persistence.*;

@Entity
public class Estadisticas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int tiempoTotalMinutos;
    private int cursosCompletados;
    private int rachaDias;

    // Relación con el usuario (opcional, ver abajo)
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    public Estadisticas() {
        // Constructor por defecto requerido por JPA
    }

    public Estadisticas(int tiempoTotalMinutos, int cursosCompletados, int rachaDias) {
        this.tiempoTotalMinutos = tiempoTotalMinutos;
        this.cursosCompletados = cursosCompletados;
        this.rachaDias = rachaDias;
    }

    public Long getId() {
        return id;
    }

    public int getTiempoTotalMinutos() {
        return tiempoTotalMinutos;
    }

    public int getCursosCompletados() {
        return cursosCompletados;
    }

    public int getRachaDias() {
        return rachaDias;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTiempoTotalMinutos(int minutos) {
        this.tiempoTotalMinutos = minutos;
    }

    public void setCursosCompletados(int cursos) {
        this.cursosCompletados = cursos;
    }

    public void setRachaDias(int dias) {
        this.rachaDias = dias;
    }
}
