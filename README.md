# Planteamiento API

## Idea: Plataforma de gestión de videojuegos

### Tabla 1: Usuarios
Esta tabla almacena la información de los usuarios registrados en la plataforma, quienes pueden tener roles de cliente o admin.

- **idUsuario**: Campo de tipo `Long`, clave primaria. Identifica de manera única a cada usuario.
- **username**: Campo de tipo `String`. No permite valores nulos. Representa el nombre del usuario.
- **email**: Campo de tipo `String`. No permite valores nulos y debe ser único. Representa el correo electrónico del usuario.
- **password**: Campo de tipo `String`. No permite valores nulos. Almacena la contraseña cifrada del usuario.
- **roles**: Campo de tipo `String` . No permite valores nulos. Representa el rol del usuario dentro de la plataforma.

---

### Tabla 2: Videojuegos
Esta tabla almacena la información de los videojuegos disponibles para los usuarios.

- **idVideojuego**: Campo de tipo `Long`, clave primaria. Identifica de manera única a cada videojuego.
- **título**: Campo de tipo `String`. No permite valores nulos. Representa el título del videojuego.
- **descripcion**: Campo de tipo `String`. Opcional. Describe el contenido o características del videojuego.
- **fecha_lanzamiento**: Campo de tipo `String`. Opcional. Representa la fecha en que se lanzó el videojuego.
- **género**: Campo de tipo `String`. Opcional. Indica el género del videojuego.
- **precio**: Campo de tipo `double`. No permite valores nulos. Representa el precio del videojuego.

---

### Tabla 3: Biblioteca
Esta tabla relaciona a los usuarios con los videojuegos que tienen en su biblioteca personal.

- **idBiblioteca**: Campo de tipo `Long`, clave primaria. Identifica de manera única cada entrada de la biblioteca.
- **idUsuario**: Campo de tipo `Long`. No permite valores nulos. Es una clave foránea que referencia al campo `idUsuario` de la tabla Usuarios. Representa al usuario propietario de la biblioteca.
- **idVideojuego**: Campo de tipo `Long`. No permite valores nulos. Es una clave foránea que referencia al campo `idVideojuego` de la tabla Videojuegos. Representa el videojuego que está en la biblioteca del usuario.
- **fechaAdquisicion**: Campo de tipo `String`. No permite valores nulos. Indica la fecha en la que el videojuego fue agregado a la biblioteca del usuario.

---

### Diagrama

![Diagrama](https://i.imgur.com/4ttbuT5.png "Diagrama del sistema")

---

## Endpoints

### Endpoints de Usuario
- **POST /usuarios**  
  Crear un nuevo usuario.
- **GET /usuarios**  
  Listar todos los usuarios (solo admins).
- **GET /usuarios/{id}**  
  Obtener detalles de un usuario específico.
- **PUT /usuarios/{id}**  
  Actualizar la información de un usuario.
- **DELETE /usuarios/{id}**  
  Eliminar un usuario (solo admins).

---

### Endpoints de Videojuegos
- **POST /videojuegos**  
  Crear un nuevo videojuego (solo admins).
- **GET /videojuegos**  
  Listar todos los videojuegos disponibles.
- **GET /videojuegos/{id}**  
  Obtener detalles de un videojuego específico.
- **PUT /videojuegos/{id}**  
  Actualizar información de un videojuego (solo admins).
- **DELETE /videojuegos/{id}**  
  Eliminar un videojuego (solo admins).

---

### Endpoints de Biblioteca
- **POST /biblioteca**  
  Agregar un videojuego a la biblioteca de un usuario.
- **GET /biblioteca/{id_usuario}**  
  Obtener la lista de videojuegos en la biblioteca de un usuario.
- **DELETE /biblioteca/{id_biblioteca}**  
  Eliminar un videojuego de la biblioteca de un usuario.
