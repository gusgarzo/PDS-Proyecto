package pds.dominio;

public class Estadisticas {
    private int tiempoTotalMinutos;
    private int cursosCompletados;
    private int rachaDias;

    public Estadisticas(int tiempoTotalMinutos, int cursosCompletados, int rachaDias) {
        this.tiempoTotalMinutos = tiempoTotalMinutos;
        this.cursosCompletados = cursosCompletados;
        this.rachaDias = rachaDias;
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
}
