package pds.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import pds.dominio.Alumno;
import pds.dominio.Curso;
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

    /*public void registrarCursoRealizado(Alumno alumno, Curso curso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Alumno a = em.merge(alumno);
            a.getCursosRealizados().add(curso);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }*/
    
    public void registrarCursoRealizado(Alumno alumno, Curso curso) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Alumno alumnoPersistido = em.find(Alumno.class, alumno.getId());
            Curso cursoPersistido = em.find(Curso.class, curso.getId());

            // 💡 Verificación importante
            if (!alumnoPersistido.getCursosImportados().contains(cursoPersistido)) {
                alumnoPersistido.getCursosImportados().add(cursoPersistido);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }


    /*public List<Curso> obtenerCursosRealizadosPor(Alumno alumno) {
        EntityManager em = getEntityManager();
        try {
            Alumno a = em.find(Alumno.class, alumno.getId());
            return a != null ? a.getCursosRealizados() : List.of();
        } finally {
            em.close();
        }
    }*/
    
    public List<Curso> obtenerCursosRealizadosPor(Alumno alumno) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                "SELECT c FROM Alumno a JOIN a.cursosRealizados c WHERE a.id = :id", Curso.class)
                .setParameter("id", alumno.getId())
                .getResultList();
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
    
    public List<Curso> obtenerCursosRealizadosDelAlumno(String correoAlumno) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                "SELECT rc.curso FROM RealizarCurso rc WHERE rc.alumno.correo = :correo", Curso.class)
                .setParameter("correo", correoAlumno)
                .getResultList();
        } finally {
            em.close();
        }
    }
    
    public RealizarCurso buscarRealizacionPor(Alumno alumno, Curso curso) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                "SELECT r FROM RealizarCurso r WHERE r.alumno.id = :alumnoId AND r.curso.id = :cursoId", RealizarCurso.class)
                .setParameter("alumnoId", alumno.getId())
                .setParameter("cursoId", curso.getId())
                .getResultStream()
                .findFirst()
                .orElse(null);
        } finally {
            em.close();
        }
    }


}
