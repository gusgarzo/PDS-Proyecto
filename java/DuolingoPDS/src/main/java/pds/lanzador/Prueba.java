package pds.lanzador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pds.dominio.Alumno;

public class Prueba {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenciaPDS"); // Asegurate del nombre exacto del persistence-unit
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Alumno alumno = new Alumno();
            alumno.setNombre("Rodrigo");
            alumno.setCorreo("rodrigo@email.com");
            alumno.setContrasena("1234");

            em.persist(alumno);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
