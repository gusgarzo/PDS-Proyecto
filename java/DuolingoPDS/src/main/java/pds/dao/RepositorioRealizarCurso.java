package pds.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pds.dominio.Alumno;
import pds.dominio.RealizarCurso;

import java.util.List;

public class RepositorioRealizarCurso {
	
	
	private static RepositorioRealizarCurso instancia;
    private final EntityManagerFactory emf;

    public RepositorioRealizarCurso() {
        emf = Persistence.createEntityManagerFactory("PokeLingo");
    }

    public static RepositorioRealizarCurso getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioRealizarCurso();
        }
        return instancia;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void guardarRealizarCurso(RealizarCurso cursoReal) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cursoReal);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

   
    public void actualizarRealizarCurso(RealizarCurso cursoReal) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cursoReal);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public List<RealizarCurso> recuperarCursosEmpezados(Alumno alumno) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT rc FROM RealizarCurso rc WHERE rc.alumno = :alumno", RealizarCurso.class)
                    .setParameter("alumno", alumno)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    public void eliminarRealizarCurso(RealizarCurso realCurso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            RealizarCurso managed = em.merge(realCurso);
            em.remove(managed);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    //Para los tests
    public void eliminarTodos() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM RealizarCurso").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
