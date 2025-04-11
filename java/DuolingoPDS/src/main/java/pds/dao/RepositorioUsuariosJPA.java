package pds.dao;

import jakarta.persistence.*;
import pds.dominio.Usuario;

import java.util.List;

public class RepositorioUsuariosJPA {

    private EntityManagerFactory emf;

    public RepositorioUsuariosJPA() {
        this.emf = Persistence.createEntityManagerFactory("PersistenciaPDS");
    }

    public void registrarUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public Usuario buscarUsuarioPorCorreoYContrasena(String correo, String contrasena) {
        EntityManager em = emf.createEntityManager();
        Usuario usuario = null;
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.correo = :correo AND u.contrasena = :contrasena",
                Usuario.class);
            query.setParameter("correo", correo);
            query.setParameter("contrasena", contrasena);
            usuario = query.getSingleResult();
        } catch (NoResultException e) {
            usuario = null;
        } finally {
            em.close();
        }
        return usuario;
    }

    public boolean existeUsuario(String correo) {
        EntityManager em = emf.createEntityManager();
        boolean existe;
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.correo = :correo", Long.class);
            query.setParameter("correo", correo);
            Long count = query.getSingleResult();
            existe = count > 0;
        } finally {
            em.close();
        }
        return existe;
    }

    public Usuario recuperarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Usuario usuario = em.find(Usuario.class, id);
        em.close();
        return usuario;
    }

    public List<Usuario> recuperarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        em.close();
        return usuarios;
    }
}
