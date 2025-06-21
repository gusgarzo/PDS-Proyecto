package pds.dominio;

import jakarta.persistence.*;

@Entity
public class Estadisticas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long tiempoUso;        // tiempo total en milisegundos
    private long tiempoInicio;     // cuándo empezó la sesión
    private int cursosCompletados;
    private int rachaActualPreguntasCorrectas;
    private int mejorRachaPreguntasCorrectas;

    @OneToOne
    @JoinColumn(name = "alumno_id", unique = true)
    private Alumno alumno;

    public Estadisticas() {
        this.tiempoUso = 0;
        this.cursosCompletados = 0;
        this.rachaActualPreguntasCorrectas = 0;
        this.mejorRachaPreguntasCorrectas = 0;
        this.tiempoInicio = -1;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public int getTiempoTotalMinutos() {
        return (int) (tiempoUso / (1000 * 60));
    }

    public int getCursosCompletados() {
        return cursosCompletados;
    }

    public int getrachaActualPreguntasCorrectas() {
        return rachaActualPreguntasCorrectas;
    }

    public int getmejorRachaPreguntasCorrectas() {
        return mejorRachaPreguntasCorrectas;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    // Setters
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void setCursosCompletados(int cursos) {
        this.cursosCompletados = cursos;
    }

    public void setrachaActualPreguntasCorrectas(int preguntas) {
        this.rachaActualPreguntasCorrectas = preguntas;
    }

    public void setmejorRachaPreguntasCorrectas(int mejorRachaPreguntasCorrectas) {
        this.mejorRachaPreguntasCorrectas = mejorRachaPreguntasCorrectas;
    }

    public void setTiempoUso(long tiempoUso) {
        this.tiempoUso = tiempoUso;
    }

    public long getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(long tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    // Lógica de sesión
    public void iniciarTiempo() {
        tiempoInicio = System.currentTimeMillis(); 
    }

    public void finalizarTiempo() {
        if (tiempoInicio != -1) {
            long tiempoSesion = System.currentTimeMillis() - tiempoInicio;
            tiempoUso += tiempoSesion;
            tiempoInicio = -1;
        }
    }
    
    public long getTiempoTotalConSesionActual() {
        long tiempoTotal = tiempoUso;
        if (tiempoInicio != -1) {
            long tiempoSesionActual = System.currentTimeMillis() - tiempoInicio;
            tiempoTotal += tiempoSesionActual;
        }
        return tiempoTotal;
    }

    
    public void registrarRespuesta(boolean correcta) {
        if (correcta) {
            rachaActualPreguntasCorrectas++;
            if (rachaActualPreguntasCorrectas > mejorRachaPreguntasCorrectas) {
                mejorRachaPreguntasCorrectas = rachaActualPreguntasCorrectas;
            }
        } else {
            rachaActualPreguntasCorrectas = 0;
        }
    }

    // Incrementos
    public void incrementarCursosCompletados() {
        this.cursosCompletados++;
    }

}
