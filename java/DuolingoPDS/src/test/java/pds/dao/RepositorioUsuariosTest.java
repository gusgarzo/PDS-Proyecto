package pds.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import pds.dominio.Alumno;
import pds.dominio.CreadorCurso;
import pds.dominio.Usuario;





class RepositorioUsuariosTest {
	static RepositorioUsuarios repositorio;

	@BeforeEach
	public  void setup() {
		repositorio = new RepositorioUsuarios();
		repositorio.eliminarTodo();
	}
	
	@Test
	@Order(1)
	public void testGuardarYBuscarUsuario() {
		Alumno e = new Alumno("nombre", "apellidos", "1234", "tester@pokelingo.com", "assdasd");
		repositorio.registrarUsuario(e);

		Usuario u = repositorio.obtenerAlumnoPorCorreo("tester@pokelingo.com");
		assertNotNull(u);
		assertEquals("nombre", u.getNombre());
	}

	@Test
	@Order(2)
	public void testIniciarSesionExitosa() {
		Alumno e = new Alumno("nombre", "apellidos", "1234", "tester@pokelingo.com", "assdasd");
		repositorio.registrarUsuario(e);
		Usuario u = repositorio.autenticar("nombre", "assdasd");
		assertNotNull(u);
	}

	@Test
	@Order(3)
	public void testIniciarSesionFallida() {
		Alumno e = new Alumno("nombre", "apellidos", "8789798998", "tester@pokelingo.com", "assdasd");
		repositorio.registrarUsuario(e);
		Usuario u = repositorio.autenticar("nombre", "33333");
		assertNull(u);
	}


	@Test
	@Order(5)
	public void testActualizar() {
		Alumno e = new Alumno("nombre", "apellidos", "8789798998", "tester@pokelingo.com", "assdasd");
		repositorio.registrarUsuario(e);
		
	    e.setContrasena("nueva123");
	    repositorio.actualizarUsuario(e);

	    Usuario u = repositorio.autenticar("nombre", "nueva123");
	    assertNotNull(u);
	}
	
	@Test
	@Order(6)
	public void testEliminarTodo() {
	    repositorio.registrarUsuario(new Alumno("nombre", "apellidos", "8789798998", "tester@pokelingo.com", "assdasd"));
	    repositorio.registrarUsuario(new Alumno("nombre2", "apellidos2", "87897989982", "tester@pokelingo.com2", "assdasd2"));

	    repositorio.eliminarTodo();

	    assertNull(repositorio.obtenerAlumnoPorCorreo("tester@pokelingo.com"));
	    assertNull(repositorio.obtenerAlumnoPorCorreo("tester@pokelingo.com2"));
	}
	
	@Test
	@Order(7)
	public void testGuardarTodos() {
		Alumno e = new Alumno("nombre", "apellidos", "8789798998", "tester@pokelingo.com", "assdasd");
		Alumno e2 = new Alumno("nombre2", "apellidos2", "878979899823", "tester@pokelingo.com2", "assdasd2");
		Alumno e3 = new Alumno("nombre3", "apellidos3", "878979899832", "tester@pokelingo.com3", "assdasd3");
		repositorio.registrarUsuario(e);
		repositorio.registrarUsuario(e2);
		repositorio.registrarUsuario(e3);

		List<Usuario> usuarios = repositorio.obtenerTodosLosUsuarios();
		assertEquals(3, usuarios.size());
		assertTrue(usuarios.stream().anyMatch(u -> u.getNombre().equals("nombre")));
		assertTrue(usuarios.stream().anyMatch(u -> u.getNombre().equals("nombre2")));
		assertTrue(usuarios.stream().anyMatch(u -> u.getNombre().equals("nombre3")));
	}
	
	@Test
	@Order(8)
	public void testGetCreadorCursoCorreo() {
		CreadorCurso e = new CreadorCurso("nombre", "apellidos", "8789798998", "tester@pokelingo.com", "assdasd");
		repositorio.registrarUsuario(e);

		Usuario u = repositorio.obtenerCreadorPorCorreo("tester@pokelingo.com");
		assertNotNull(u);
		assertEquals(e.getId(), u.getId());
		assertEquals(e.getNombre(), u.getNombre());
		assertEquals(e.getContrasena(), u.getContrasena());
	}

}
