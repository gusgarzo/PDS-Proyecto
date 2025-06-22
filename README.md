#  PokeLingo

### DESCRIPCIÓN

**PokeLingo** es una aplicación educativa sobre pokemon desarrollada por **Gustavo Garzo Chumilla, Rodrigo Barceló Arce  y Juan Pedro Jimenez Dato**  para el proyecto final de la asignatura PDS 2025 de la **Universidad de Murcia**, bajo la supervisión del profesor **Jesús Sánchez Cuadrado**.

La aplicación está inspirada en Duolingo y Mochi y permite a estudiantes aprender mediante cursos con ejercicios tipo test, huecos, flashcards. Los colaboradores pueden crear e importar cursos usando archivos JSON, fomentando una comunidad colaborativa de aprendizaje.

---

### Requisitos

- Java 8+
- Maven
- Git (opcional)

---

### Casos de Uso

- **Registrarse** 
- **Iniciar sesión**
- **Importar curso**
- **Realizar curso**
- **Continuar curso**
- **Ver estadísticas de uso**
- **Crear curso**
- **Compartir curso**
- **Cerrar sesión**

Funcionalidad adicional:
- **Crear cursos como creador**

  
**Información de sobre Casos de Uso mas detallada**
([Casos De Uso](requisitos/casos_de_uso.md))

---

### Diseño y Modelo

El modelo de dominio incluye dos roles principales: `Alumno` y `CreadorCurso`, ambos heredan de la clase base `Usuario`. 

- **CreadorCurso** puede crear múltiples `Curso`, los cuales contienen uno o varios `BloqueContenido`.
- Cada bloque incluye varias `Pregunta`, que es una clase abstracta de la que heredan:
  - `TipoTest`
  - `Huecos`
  - `FlashCard`

Además, las preguntas están asociadas a un nivel de `Dificultad` (Fácil, Media, Difícil), lo que permite adaptar los cursos al nivel del estudiante.

- El `Alumno` puede realizar múltiples `Curso` mediante una relación con `RealizacionCurso`, que guarda el progreso.
- Durante la realización, el alumno utiliza una `EstrategiaAprendizaje`, que puede ser:
  - `Secuencial`
  - `Aleatoria`
  - `RepeticionEspaciada`

Finalmente, se incorpora la entidad `Estadisticas`, generada por el alumno, que recoge datos como el tiempo de uso, número de cursos completados y rachas de respuestas correctas.

---

**Información de sobre Modelo de Dominio mas detallada**
([Modelo de Dominio](diseño/modelo.md))

---

## MANUAL DE USO
**Leer manual de uso aquí**
([Manual de uso](documentacion/manual.md))

---

**Desarrollado por Gustavo Garzo Chumilla (Grupo 3.3), Rodrigo Barceló Arce(Grupo 1.1) y Juan Pedro Jimenez Dato(Grupo 3.3) — Universidad de Murcia**
