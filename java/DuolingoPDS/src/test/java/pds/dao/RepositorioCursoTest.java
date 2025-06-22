package pds.dao;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import pds.dominio.Alumno;
import pds.dominio.CreadorCurso;
import pds.dominio.Curso;
import pds.dominio.Usuario;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;







class RepositorioCursoTest {
	private static RepositorioCurso repositorio;
	private static Long idCurso;
	private static Usuario creadorDummy;
	@BeforeAll
	public static void setUp() {
	    repositorio = new RepositorioCurso();
	    creadorDummy = crearUsuarioDummyEnDB(); // Creador válido para asignar al curso
	}	
	 private static Usuario crearUsuarioDummyEnDB() {
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PokeLingo");
	        EntityManager em = emf.createEntityManager();
	
	        Usuario usuario = new CreadorCurso("nombre", "apellidos", "8789798998", "tester@pokelingo.com", "assdasd");
	        usuario.setNombre("Tester");
	
	        em.getTransaction().begin();
	        em.persist(usuario);
	        em.getTransaction().commit();
	        em.close();
	
	        return usuario;
	    }

	    @Test
	    @Order(1)
	    public void testGuardarCurso() {
	        Curso curso = new Curso();
	        curso.setNombre("Curso de Prueba");
	        curso.setDescripcion("Este es un curso de prueba.");
	        curso.setCreador((CreadorCurso)creadorDummy);

	        Curso cur =  repositorio.guardarCurso(curso);
	        idCurso = cur.getId();
	        System.out.println(idCurso);
	        assertNotNull(idCurso);
	    }
	    
	   

	    @Test
	    @Order(2)
	    public void testObtenerTodosLosCursos() {
	    	Curso nuevoCurso = new Curso();
	        nuevoCurso.setNombre("Curso Específico");
	        nuevoCurso.setDescripcion("Curso para probar obtención por creador");
	        nuevoCurso.setCreador((CreadorCurso)creadorDummy);
	        repositorio.guardarCurso(nuevoCurso);
	        List<Curso> cursos = repositorio.obtenerTodos();
	        assertFalse(cursos.isEmpty());
	    }
	    
	    @Test
	    @Order(3)
	    public void testObtenerPorCreador() {
	        // 1. Crear un nuevo curso con el creador dummy
	        Curso nuevoCurso = new Curso();
	        nuevoCurso.setNombre("Curso Específico");
	        nuevoCurso.setDescripcion("Curso para probar obtención por creador");
	        nuevoCurso.setCreador((CreadorCurso)creadorDummy);
	        repositorio.guardarCurso(nuevoCurso);
	        
	        // 2. Ejecutar el método bajo prueba
	        List<Curso> cursos = repositorio.obtenerPorCreador("tester@pokelingo.com");
	        
	        // 3. Verificar resultados
	        assertFalse(cursos.isEmpty(), "Debería encontrar al menos un curso");
	        
	        boolean cursoEncontrado = cursos.stream()
	            .anyMatch(c -> 
	                c.getNombre().equals("Curso Específico") && 
	                c.getCreador().getCorreo().equals("tester@pokelingo.com"));
	        
	        assertTrue(cursoEncontrado, "Debería encontrar el curso específico del creador");
	        
	        // 4. Prueba negativa: Buscar con correo inexistente
	        List<Curso> cursosInexistente = repositorio.obtenerPorCreador("noexiste@pokelingo.com");
	        assertTrue(cursosInexistente.isEmpty(), "No debería encontrar cursos para correo inexistente");
	    }
	   
   
}
