	package pds.dao;
	
	import jakarta.persistence.EntityManager;
	import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
	import pds.dominio.Usuario;
	import pds.dominio.Alumno;
	import pds.dominio.CreadorCurso;
	
	import java.util.List;
	
	public class RepositorioUsuarios {
	
		private static RepositorioUsuarios instancia;
	    private final EntityManagerFactory emf;
	
	    public RepositorioUsuarios() {
	        emf = Persistence.createEntityManagerFactory("PokeLingo");
	    }
	
	    public static RepositorioUsuarios getInstancia() {
	        if (instancia == null) {
	            instancia = new RepositorioUsuarios();
	        }
	        return instancia;
	    }
	    
	    private EntityManager getEntityManager() {
	        return emf.createEntityManager();
	    }
	
	    // Guarda cualquier tipo de usuario: Alumno o CreadorCurso
	    public void registrarUsuario(Usuario usuario) {
	        EntityManager em = getEntityManager();
	        try {
	            em.getTransaction().begin();
	            em.persist(usuario);
	            em.getTransaction().commit();
	        } finally {
	            em.close();
	        }
	    }
	
	    public Usuario buscarPorNombre(String nombreUsuario) {
	        EntityManager em = getEntityManager();
	        try {
	            List<Usuario> resultado = em.createQuery(
	                "SELECT u FROM Usuario u WHERE u.nombre = :nombre", Usuario.class)
	                .setParameter("nombre", nombreUsuario)
	                .getResultList();
	            return resultado.isEmpty() ? null : resultado.get(0);
	        } finally {
	            em.close();
	        }
	    }
	
	    public Usuario autenticar(String nombreUsuario, String contrasena) {
	        EntityManager em = getEntityManager();
	        try {
	            List<Usuario> resultado = em.createQuery(
	                "SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.contrasena = :clave", Usuario.class)
	                .setParameter("nombre", nombreUsuario)
	                .setParameter("clave", contrasena)
	                .getResultList();
	            return resultado.isEmpty() ? null : resultado.get(0);
	        } finally {
	            em.close();
	        }
	    }
	
	    // Solo los alumnos
	    public List<Alumno> listarAlumnos() {
	        EntityManager em = getEntityManager();
	        try {
	            return em.createQuery("SELECT a FROM Alumno a", Alumno.class).getResultList();
	        } finally {
	            em.close();
	        }
	    }
	
	    // Solo los creadores
	    public List<CreadorCurso> listarCreadores() {
	        EntityManager em = getEntityManager();
	        try {
	            return em.createQuery("SELECT c FROM CreadorCurso c", CreadorCurso.class).getResultList();
	        } finally {
	            em.close();
	        }
	    }
	
	    public void actualizarUsuario(Usuario usuario) {
	        EntityManager em = getEntityManager();
	        try {
	            em.getTransaction().begin();
	            em.merge(usuario);
	            em.getTransaction().commit();
	        } finally {
	            em.close();
	        }
	    }
	
	    public void eliminarPorId(int id) {
	        EntityManager em = getEntityManager();
	        try {
	            em.getTransaction().begin();
	            Usuario usuario = em.find(Usuario.class, id);
	            if (usuario != null) {
	                em.remove(usuario);
	            }
	            em.getTransaction().commit();
	        } finally {
	            em.close();
	        }
	    }
	    
	    
	    public CreadorCurso obtenerCreadorPorCorreo(String correo) {
	        EntityManager em = getEntityManager();
	        try {
	            return em.createQuery("SELECT c FROM CreadorCurso c WHERE c.correo = :correo", CreadorCurso.class)
	                     .setParameter("correo", correo)
	                     .getSingleResult();
	        } catch (Exception e) {
	            return null;
	        } finally {
	            em.close();
	        }
	    }
	    
	    public Alumno obtenerAlumnoPorCorreo(String correo) {
	        EntityManager em = getEntityManager();
	        try {
	            return em.createQuery("SELECT a FROM Alumno a WHERE a.correo = :correo", Alumno.class)
	                     .setParameter("correo", correo)
	                     .getSingleResult();
	        } catch (NoResultException e) {
	            return null;
	        } finally {
	            em.close();
	        }
	    }

	    public List<Usuario> obtenerTodosLosUsuarios() {
	        EntityManager em = emf.createEntityManager();
	        try {
	            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();

			}
		}
	    public void eliminarTodo(){
    	    EntityManager em = emf.createEntityManager();
    	    try {
    	    	em.getTransaction().begin();
    	    	List<Usuario> usuarios = obtenerTodosLosUsuarios();
        	    usuarios.stream()
        	    .map(u -> u.getId())
        	    .forEach(id -> eliminarPorId(id));
        	    em.getTransaction().commit();
    	    } finally {
    	    	em.close();
    	    	
    	    }
    }
	    
	  

	}
