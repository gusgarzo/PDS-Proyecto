package pds.dao;

import pds.dominio.Curso;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class RepositorioCurso {

    private static RepositorioCurso instancia;
    private EntityManagerFactory emf;

    public RepositorioCurso() {
        emf = Persistence.createEntityManagerFactory("PokeLingo");
    }

    public static RepositorioCurso getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCurso();
        }
        return instancia;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void guardarCurso(Curso curso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(curso);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void actualizarCurso(Curso curso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(curso);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminarCurso(Curso curso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Curso cursoRef = em.find(Curso.class, curso.getId());
            if (cursoRef != null) {
                em.remove(cursoRef);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Curso buscarPorId(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public List<Curso> obtenerTodos() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Curso> obtenerPorCreador(String nombreUsuario) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Curso c WHERE c.creador.nombre = :usuario", Curso.class)
                    .setParameter("usuario", nombreUsuario)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
