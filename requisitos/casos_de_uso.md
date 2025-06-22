# Casos de Uso

## Actores

- **Alumno**: Persona que realiza cursos, consulta estadísticas, continúa su aprendizaje e importa cursos.
- **Creador de cursos**: Persona que crea y comparte cursos.

## Tabla de casos de uso

| Actor             | Caso de Uso                              |
| ----------------- | ---------------------------------------- |
| Alumno            | [Registro](#caso-de-uso-registro)              |
|                   | [Login](#caso-de-uso-login)        |
|                   | [Importar curso](#caso-de-uso-importar-curso)                   |
|                   | [Realizar curso](#caso-de-uso-realizar-curso)                   |
|                   | [Continuar curso](#caso-de-uso-continuar-curso)                 |
|                   | [Ver estadísticas de uso](#caso-de-uso-ver-estadísticas-de-uso) |
| Creador de cursos | [Login](#caso-de-uso-login)              |
|                   | [Registro](#caso-de-uso-registro)        |
|                   | [Crear curso](#caso-de-uso-crear-curso)  |
|                   | [Compartir curso](#caso-de-uso-compartir-curso)                 |

---
## Caso de uso: Registro

**Descripción:**  
Permite a un nuevo usuario crear una cuenta en DuolingoPDS, eligiendo el tipo de usuario (**Alumno** o **Creador de cursos**) y proporcionando sus datos personales.

**Actor principal:**  
- Usuario (nuevo Alumno o nuevo Creador de cursos)

**Precondiciones:**  
- El usuario no debe tener una cuenta registrada previamente con los mismos datos.

**Flujo principal:**
1. El usuario inicia la aplicación DuolingoPDS.
2. Se muestra la opción de registro en la ventana de login.
3. El usuario introduce sus datos personales (nombre, apellidos, teléfono, email).
4. Selecciona el tipo de cuenta: **Alumno** o **Creador de cursos**.
5. Elige una contraseña.
6. El sistema valida los datos y crea la cuenta.
7. El usuario puede iniciar sesión con su nueva cuenta.

**Flujos alternativos:**
- **4a. Datos inválidos:** Si se detectan datos inválidos (formato incorrecto, teléfono o email ya registrado), se muestra un mensaje de error y se solicita corrección.

**Postcondiciones:**  
- El usuario queda registrado y puede acceder a la aplicación.

---

## Caso de uso: Login

**Descripción:**  
Permite al usuario acceder a la aplicación DuolingoPDS introduciendo sus credenciales (teléfono/usuario y contraseña). Según el tipo de cuenta, el usuario asume el rol de **Alumno** o **Creador de cursos**.

**Actor principal:**  
- Alumno  
- Creador de cursos

**Precondiciones:**  
- El usuario debe estar previamente registrado.

**Flujo principal:**
1. El usuario inicia la aplicación DuolingoPDS.
2. Se muestra la pantalla de login.
3. El usuario introduce su teléfono/usuario y contraseña.
4. El sistema valida las credenciales.
5. Si son correctas, el usuario accede a la aplicación con su rol correspondiente.

**Flujos alternativos:**
- **3a. Credenciales incorrectas:** Si las credenciales no coinciden, se notifica al usuario y se permite reintentar.

**Postcondiciones:**  
- El usuario queda logueado en la aplicación, con su rol asignado.

---



### Caso de uso: Importar curso

**Descripción:** Permite al alumno importar un curso compartido por un creador desde un archivo en formato JSON.

**Actor principal:** Alumno

**Precondiciones:** El alumno debe estar logueado y disponer de un archivo válido.

**Flujo principal:**

1. El alumno accede a la opción de importar curso.
2. El alumno selecciona el archivo recibido.
3. El sistema valida el contenido.
4. El curso aparecerá disponible para comenzar.

**Flujos alternativos:**

- **3a. Archivo no válido:** Si en el paso 3 el contenido no es válido, se muestra un mensaje de error.

**Postcondiciones:** El curso queda listo para ser realizado por el alumno.

---

## Caso de uso: Realizar curso

---

## Descripción
Permite al alumno seleccionar y realizar un curso.

## Actor principal
- Alumno

## Precondiciones
- El alumno debe estar registrado y logueado en el sistema.

## Flujo principal
1. El alumno selecciona un curso de los disponibles en la base de datos o previamente a importado uno.
2. El alumno selecciona la estrategia de aprendizaje.
3. El alumno completa los bloques de contenido.
4. El alumno al terminar todos los bloques, completa el curso.
5. Se le presentan estadísticas en relación a cómo a completado el curso. (opcional)

## Flujos alternativos
- **1a. Curso no disponible**: Si en el paso 1 el curso seleccionado no está disponible, el sistema muestra un mensaje de error y sugiere otro curso similar.
- **3b. Alumno abandona**: Si en el paso 1 el alumno decide salir del curso, el sistema guarda su progreso y permite retomarlo más tarde.

## Postcondiciones
- El curso queda finalizado o guardado en estado "en progreso".


### Caso de uso: Continuar curso

**Descripción:** Permite al alumno retomar un curso previamente iniciado.

**Actor principal:** Alumno

**Precondiciones:** El alumno debe haber iniciado un curso anteriormente.

**Flujo principal:**

1. El alumno accede a la lista de cursos en progreso.
2. Selecciona el curso que desea continuar.
3. El sistema lo rentoma en justo en el bloque de contenido que lo dejó.

**Flujos alternativos:**

- **2a. Curso no existe:** Si en el paso 2 el curso seleccionado no existe, se muestra un mensaje de error.

**Postcondiciones:** El alumno puede seguir avanzando en el curso.

---

### Caso de uso: Ver estadísticas de uso

**Descripción:** El alumno consulta sus estadísticas de uso, progreso y resultados.

**Actor principal:** Alumno

**Precondiciones:** No aplica.

**Flujo principal:**

1. El alumno accede a su panel de estadísticas.
2. Visualiza datos de progreso, cursos completados y tiempo de uso de la App.

**Postcondiciones:** No aplica.


---

### Caso de uso: Crear curso

**Descripción:** El creador de cursos diseña y crea un curso nuevo.

**Actor principal:** Creador de cursos

**Precondiciones:** El creador debe estar logueado y tener permisos de creación.

**Flujo principal:**

1. El creador accede al apartado de creación de cursos.
2. Ingresa título, descripción y dificultad (fácil, normal o difícil).
3. El creador crea los bloques de contenido con las preguntas correspondientes a estos.
4. Guarda el curso en el sistema.
   
**Flujos alternativos:**

- **3a. Error al guardar:** Si en el paso 3 hay un error al guardar, se muestra un mensaje de error y se solicita reintentar.

**Postcondiciones:** El curso queda disponible para ser compartido.

---

### Caso de uso: Compartir curso

**Descripción:** El creador de cursos comparte un curso previamente creado.

**Actor principal:** Creador de cursos

**Precondiciones:** El curso debe estar finalizado y guardado.

**Flujo principal:**

1. El creador accede a la lista de cursos.
2. Selecciona un curso para compartir.
3. El sistema confirma la publicación.

**Flujos alternativos:**

- **4a. Error al compartir:** Si en el paso 3 ocurre un error al compartir, se muestra un mensaje de error.

**Postcondiciones:** El curso queda accesible para ser importado por los alumnos.

