package pds.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pds.dominio.Alumno;
import pds.dominio.BloqueContenido;
import pds.dominio.CreadorCurso;
import pds.dominio.Curso;
import pds.dominio.Dificultad;
import pds.dominio.Estrategia;
import pds.dominio.EstrategiaSecuencial;
import pds.dominio.RealizarCurso;

class RepositorioRealizarCursoTest {
		private static EntityManagerFactory emf;
		private static EntityManager em;
		 
		 
	    private static RepositorioRealizarCurso repositorio;
	    private static RealizarCurso realizarCurso;
	    private static Alumno alumno;
	    private static Curso curso;
	    private static Estrategia estrategia;
	    private static CreadorCurso creador;
	    @BeforeAll
	    public static void setupOnce() {
	        emf = Persistence.createEntityManagerFactory("PokeLingo");
	    }
		 @BeforeEach
		     void setUp() {    	
			 	
			 	repositorio = new RepositorioRealizarCurso();
		        // Configurar EMF para H2 en memoria
			 	em = emf.createEntityManager();

		    	// PRIMERA TRANSACCIÓN: limpiar base de datos
		    	em.getTransaction().begin();
		    	em.createQuery("DELETE FROM RealizarCurso").executeUpdate();
		    	em.createQuery("DELETE FROM Estadisticas").executeUpdate();
		    	em.createQuery("DELETE FROM Usuario").executeUpdate();
		    	em.createQuery("DELETE FROM Curso").executeUpdate();
		    	em.createQuery("DELETE FROM BloqueContenido").executeUpdate();
		    	em.getTransaction().commit();

		    	// SEGUNDA TRANSACCIÓN: insertar datos de prueba
		    	em.getTransaction().begin();
		    	creador = new CreadorCurso();
		    	alumno = new Alumno();
		    	curso = creador.crearCurso("Curso de prueba", Dificultad.DIFICIL, "Descripción");
		    	
		    	
		    	BloqueContenido bloque = new BloqueContenido("Bloque de prueba");
		    	BloqueContenido bloque2 = new BloqueContenido("Bloque 2");
		    	curso.agregarBloque(bloque);
		    	curso.agregarBloque(bloque2);


		    	em.persist(creador);
		    	em.persist(alumno);
		    	em.persist(curso);
		    	em.persist(bloque);
		    	em.persist(bloque2);

		    	realizarCurso = new RealizarCurso(curso,bloque, estrategia, alumno);
		    	
		    	em.getTransaction().commit(); 
		    }
	 	@Test
	    @Order(1)
	    void testGuardarRealizarCurso() {
	        
	        repositorio.guardarRealizarCurso(realizarCurso);
	        
	        assertNotNull(realizarCurso.getId(), "El ID debe generarse automáticamente");
	    }

	    @Test
	    @Order(2)
	    void testActualizarRealizarCurso() {
	        // Cambiar el bloque actual
	       
	        realizarCurso.avanzarBloque();
	        repositorio.actualizarRealizarCurso(realizarCurso);
	        
	        // Verificar actualización
	        RealizarCurso actualizado = repositorio.recuperarCursosEmpezados(alumno).get(0);
	        assertEquals("Bloque 2", actualizado.getBloque().getNombre());
	    }

	    @Test
	    @Order(3)
	    void testRecuperarCursosEmpezados() {
	    	repositorio.guardarRealizarCurso(realizarCurso);
	        List<RealizarCurso> cursos = repositorio.recuperarCursosEmpezados(alumno);
	        System.out.println(cursos.size());
	        assertFalse(cursos.isEmpty(), "Debería recuperar cursos del alumno");
	        assertEquals(1, cursos.size());
	        assertEquals(realizarCurso.getId(), cursos.get(0).getId());
	    }

	    


}
