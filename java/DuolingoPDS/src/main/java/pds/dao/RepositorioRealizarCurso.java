package pds.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pds.dominio.Alumno;
import pds.dominio.Curso;

import java.util.List;

public class RepositorioRealizarCurso {

    private final EntityManagerFactory emf;

    public RepositorioRealizarCurso() {
        emf = Persistence.createEntityManagerFactory("PokeLingo");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void registrarCursoRealizado(Alumno alumno, Curso curso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Alumno a = em.merge(alumno);
            a.getCursosRealizados().add(curso);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Curso> obtenerCursosRealizadosPor(Alumno alumno) {
        EntityManager em = getEntityManager();
        try {
            Alumno a = em.find(Alumno.class, alumno.getId());
            return a != null ? a.getCursosRealizados() : List.of();
        } finally {
            em.close();
        }
    }

    public boolean cursoYaRealizado(Alumno alumno, Curso curso) {
        EntityManager em = getEntityManager();
        try {
            Alumno a = em.find(Alumno.class, alumno.getId());
            return a != null && a.getCursosRealizados().contains(curso);
        } finally {
            em.close();
        }
    }

    public void eliminarCursoRealizado(Alumno alumno, Curso curso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Alumno a = em.merge(alumno);
            a.getCursosRealizados().remove(curso);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
