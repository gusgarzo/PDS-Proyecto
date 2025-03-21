# Casos de uso

## Actores y casos de uso

| Actor   | Caso de Uso                      |
|---------|----------------------------------|
| Usuario | Realizar un curso               |
|         | Elegir estrategia de aprendizaje |
|         | Guardar y reanudar progreso     |
|         | Ver estadísticas de uso         |
|         | Crear y compartir cursos        |

### Realizar un curso

**Flujo Básico:**
- El usuario escoge un curso dependiendo del tema que quiera aprender.
- El usuario comienza una serie de flashcards para aprender teoría.
- Una vez terminada la teoría el usuario comienza a hacer una serie de ejercicios.
- Al terminar los ejercicios el usuario realiza un examen.
- Al aprobar el examen el usuario termina el curso.

**Flujo Alternativo:**
- **Curso no disponible:** Si el curso seleccionado no está disponible, el sistema muestra un mensaje de error y sugiere otro curso similar.
- **El usuario abandona el curso:** Si el usuario decide salir del curso, el sistema guarda su progreso y permite retomarlo más tarde.
- **Respuestas incorrectas en ejercicios:** Si el usuario responde mal varias veces, se le ofrecen pistas o material de apoyo.
- **No aprueba el examen final:** Si el usuario no pasa el examen, se le permite revisar el material y volver a intentarlo.

### Elegir estrategia aprendizaje

**Flujo Básico:**
- El usuario elige la estrategia de aprendizaje que crea que más le convenga de las que puede elegir.
- El usuario comienza el curso con la estrategia seleccionada.
- En cualquier momento, el usuario puede cambiar la estrategia desde la configuración.

**Flujo Alternativo:**
- **El usuario no elige una estrategia:** Si el usuario no selecciona ninguna estrategia, se mantiene la estrategia por defecto (secuencial).
- **Cambio de estrategia durante el curso:** Si el usuario decide cambiar la estrategia en mitad del curso, el sistema ajusta el contenido y notifica sobre posibles cambios en el orden.
- **Fallo en la configuración:** Si hay un error al guardar la estrategia elegida, se muestra un mensaje de error y se mantiene la estrategia anterior.

