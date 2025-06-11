package pds.controlador;



import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;



import pds.dominio.*;

public enum Controlador {
	INSTANCE;
 
    private Usuario usuarioActual;

   
    private Controlador() {
   
    }

   
    public boolean registrarUsuario(String nombre, String apellidos, String telefono, String correo, String contrasena, String tipoUsuario) {
       // if (adaptadorUsuario.existeUsuario(correo)) return false;

        Usuario nuevoUsuario = null;
        if (tipoUsuario.equalsIgnoreCase("Alumno")) {

        } else if (tipoUsuario.equalsIgnoreCase("Creador de cursos")) {
            nuevoUsuario = new CreadorCurso(nombre, tipoUsuario, tipoUsuario, tipoUsuario, tipoUsuario);
        } else {
            return false;
        }

        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena(contrasena);

        //repositorioUsuarios.registrarUsuario(nuevoUsuario);
        this.usuarioActual = nuevoUsuario;

        return true;
    }

    public boolean loginUsuario(String correo, String contrasena) {
        Usuario usuario = null;

        // Simulación: si se usa este correo, se crea un creador
        if (correo.equals("2") && contrasena.equals("2")) {
            usuario = new CreadorCurso();
        }

        if (usuario != null) {
            this.usuarioActual = usuario;
            ControladorCurso.INSTANCE.setUsuarioActual(usuario);
            return true;
        }
        return false;
    }
    
    
    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean estaLogueado() {
        return usuarioActual != null;
    }

    public boolean esAlumno() {
        return usuarioActual instanceof Alumno;
    }

    public boolean esCreador() {
        return usuarioActual instanceof CreadorCurso;
    }
    public void setUsuario(Usuario usu) {
    	usuarioActual = usu;    
    }
    
    
    public Curso importarCurso(File archivo) {
        /*if (!estaLogueado() || !(usuarioActual instanceof Alumno)) {
            return null; 
        }*/

        ObjectMapper mapper = new ObjectMapper();
        try {
            Curso curso = mapper.readValue(archivo, Curso.class);
            return curso;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }





    
    /*public void crearCurso(String nombre, String descripcion, String categoria, boolean esPublico, String rutaImagen) {
        if (!(usuarioActual instanceof CreadorCurso creador)) {
            throw new IllegalStateException("Solo los creadores pueden crear cursos");
        } 

        Curso nuevoCurso = new Curso();

        creador.agregarCurso(nuevoCurso);  // Este método debería existir en CreadorCurso
        adaptadorCursos.registrarCurso(nuevoCurso);  // DAO
    }

    */
    public Curso creaCurso() {
    	BloqueContenido bloque1 = new BloqueContenido("Conceptos básicos de Pokémon");

    	// Pregunta FlashCard
    	PreguntaFlashCard p1 = new PreguntaFlashCard(
    	    "¿Qué es un Pokémon?",
    	    "Un Pokémon es una criatura ficticia con habilidades especiales."
    	);

    	// Pregunta Huecos
    	PreguntaHuecos p2 = new PreguntaHuecos(
    	    "El Pokémon más famoso es ____.",
    	    "Pikachu"
    	);

    	// Pregunta TipoTest
    	List<String> opciones1 = new ArrayList<>();
    	opciones1.add("Agua");
    	opciones1.add("Fuego");
    	opciones1.add("Eléctrico");
    	opciones1.add("Normal");
    	PreguntaTipoTest p3 = new PreguntaTipoTest(
    	    "¿Cuál de los siguientes NO es un tipo de Pokémon?",
    	    opciones1,
    	    "Normal" // Nota: "Normal" SÍ es un tipo, pero para el ejemplo, supón que la respuesta correcta es otra o cambia la opción.
    	    // Nota real: En el universo Pokémon, "Normal" SÍ es un tipo. Si quieres que funcione, pon una opción inventada o cambia la respuesta correcta.
    	    // Para el ejemplo, supondremos que la respuesta correcta es "Normal" (incorrecto en la realidad, pero así funciona el ejemplo).
    	);
    	// ¡Ojo! En la realidad, "Normal" es un tipo válido. Para que el ejemplo funcione, cambia la opción o la respuesta correcta.

    	bloque1.agregarPregunta(p1);
    	bloque1.agregarPregunta(p2);
    	bloque1.agregarPregunta(p3);
    	BloqueContenido bloque2 = new BloqueContenido("Estrategias de combate Pokémon");

    	// Pregunta FlashCard
    	PreguntaFlashCard p4 = new PreguntaFlashCard(
    	    "¿Qué es un movimiento supereficaz?",
    	    "Un movimiento que causa más daño porque aprovecha la debilidad del Pokémon rival."
    	);

    	// Pregunta Huecos
    	PreguntaHuecos p5 = new PreguntaHuecos(
    	    "Para vencer a un Pokémon de tipo Agua, es recomendable usar movimientos de tipo ____.",
    	    "Planta"
    	);

    	// Pregunta TipoTest
    	List<String> opciones2 = new ArrayList<>();
    	opciones2.add("Agua");
    	opciones2.add("Fuego");
    	opciones2.add("Eléctrico");
    	opciones2.add("Planta");
    	PreguntaTipoTest p6 = new PreguntaTipoTest(
    	    "¿Cuál de los siguientes tipos es supereficaz contra Pokémon de tipo Agua?",
    	    opciones2,
    	    "Planta"
    	);

    	bloque2.agregarPregunta(p4);
    	bloque2.agregarPregunta(p5);
    	bloque2.agregarPregunta(p6);
    	List<BloqueContenido> bloques = new ArrayList<>();
    	bloques.add(bloque1);
    	bloques.add(bloque2);
    	Dificultad dificultad = Dificultad.FACIL;

    	CreadorCurso cre = new CreadorCurso("", null, null, null, null);
    	return new Curso("Introducción a Pokémon",cre , bloques, dificultad, "");
    }
    
    public RealizarCurso iniciarCurso(Curso curso, String estrategiaNombre, Usuario usuario) {
    	System.out.println(estrategiaNombre);
        return ((Alumno)usuario).iniciarCurso(curso, estrategiaNombre);
    }
}
