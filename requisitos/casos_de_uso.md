# Casos de Uso

## Actores
- **Alumno**: Persona que realiza cursos, consulta estadísticas y continúa su aprendizaje.
- **Creador de cursos**: Persona que crea y comparte cursos.

## Tabla de casos de uso

| Actor             | Caso de Uso                  |
|-------------------|------------------------------|
| Alumno            | Realizar curso               |
|                   | Continuar curso              |
|                   | Ver estadísticas de uso      |
| Creador de cursos | Crear curso                  |
|                   | Compartir curso              |

---

### Caso de uso: Realizar curso
**Descripción:** Permite al alumno seleccionar y realizar un curso completo desde la teoría hasta el examen final.

**Actor principal:** Alumno

**Precondiciones:** El alumno debe estar registrado y logueado en el sistema.

**Flujo principal:**
1. El alumno selecciona un curso.
2. El sistema presenta la teoría mediante flashcards.
3. El alumno completa ejercicios.
4. El alumno realiza el examen final.
5. Al aprobar, el curso se marca como completado.

**Flujos alternativos:**
- **1a. Curso no disponible:** Si en el paso 1 el curso seleccionado no está disponible, el sistema muestra un mensaje de error y sugiere otro curso similar.
- **1b. Alumno abandona:** Si en el paso 1 el alumno decide salir del curso, el sistema guarda su progreso y permite retomarlo más tarde.
- **3a. Respuestas incorrectas:** Si en el paso 3 el alumno responde mal varias veces, se le ofrecen pistas o material de apoyo.
- **4a. No aprueba examen:** Si en el paso 4 el alumno no pasa el examen, se le permite revisar el material y volver a intentarlo.

**Postcondiciones:** El curso queda finalizado o guardado en estado "en progreso".

---

### Caso de uso: Continuar curso
**Descripción:** Permite al alumno retomar un curso previamente iniciado.

**Actor principal:** Alumno

**Precondiciones:** El alumno debe haber iniciado un curso anteriormente.

**Flujo principal:**
1. El alumno accede a la lista de cursos en progreso.
2. Selecciona el curso que desea continuar.
3. El sistema lo rentoma en justo donde lo dejó.

**Flujos alternativos:**
- **2a. Curso no existe:** Si en el paso 2 el curso seleccionado no existe, se muestra un mensaje de error.

**Postcondiciones:** El alumno puede seguir avanzando en el curso.

---

### Caso de uso: Ver estadísticas de uso
**Descripción:** El alumno consulta sus estadísticas de uso, progreso y resultados.

**Actor principal:** Alumno

**Precondiciones:** El alumno debe haber realizado al menos un curso.

**Flujo principal:**
1. El alumno accede a su panel de estadísticas.
2. Visualiza datos de progreso, cursos completados y resultados de exámenes.

**Postcondiciones:** No aplica.

---

### Caso de uso: Crear curso
**Descripción:** El creador de cursos diseña y crea un curso nuevo.

**Actor principal:** Creador de cursos

**Precondiciones:** El creador debe estar logueado y tener permisos de creación.

**Flujo principal:**
1. El creador accede al apartado de creación de cursos.
2. Ingresa título, descripción y contenido (teoría, ejercicios y examen).
3. Guarda el curso en el sistema.

**Flujos alternativos:**
- **3a. Error al guardar:** Si en el paso 3 hay un error al guardar, se muestra un mensaje de error y se solicita reintentar.

**Postcondiciones:** El curso queda disponible para ser compartido o editado.

---

### Caso de uso: Compartir curso
**Descripción:** El creador de cursos comparte un curso previamente creado.

**Actor principal:** Creador de cursos

**Precondiciones:** El curso debe estar finalizado y guardado.

**Flujo principal:**
1. El creador accede a la lista de cursos.
2. Selecciona un curso para compartir.
3. Elige el canal de publicación (público o privado).
4. El sistema confirma la publicación.

**Flujos alternativos:**
- **4a. Error al compartir:** Si en el paso 4 ocurre un error al compartir, se muestra un mensaje de error.

**Postcondiciones:** El curso queda accesible para los alumnos.
