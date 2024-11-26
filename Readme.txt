API Usuarios
Descripción
Esta API RESTful permite la creación de usuarios con validaciones de correo, contraseña y manejo de errores.

Tecnologías Utilizadas
Java 17
Spring Boot 3.1.5
H2 Database (en memoria)
JWT para generación de tokens
Swagger/OpenAPI para documentación interactiva.
JUnit 5 y Mockito para pruebas unitarias.
Requisitos para Ejecutar el Proyecto
Java 17 instalado y configurado.
Apache Maven 3.8.1+ instalado.
IDE recomendado: Eclipse (aunque puedes usar IntelliJ IDEA o cualquier otro IDE compatible con Maven y Spring Boot).
Cómo Ejecutar el Proyecto
Clona este repositorio:

bash
Copy code
git clone https://github.com/PauloSalas13/testPostulacionBackend.git
cd testPostulacionBackend
Compila el proyecto:

bash
Copy code
mvn clean install
Ejecuta la aplicación:

bash
Copy code
mvn spring-boot:run
Accede a los recursos de la API:

Swagger UI: http://localhost:8080/swagger-ui.html
Documentación OpenAPI (JSON): http://localhost:8080/v3/api-docs
Prueba el endpoint de registro usando Postman o cualquier cliente HTTP:

Endpoint: POST /api/usuarios/register
Body (JSON):
json
Copy code
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}
Respuestas esperadas:
Éxito (201 Created):
json
Copy code
{
  "id": "UUID",
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "created": "2024-11-25T16:54:21.0091637",
  "modified": "2024-11-25T16:54:21.0091637",
  "lastLogin": "2024-11-25T16:54:21.0091637",
  "token": "JWT-TOKEN",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ],
  "isactive": true
}
Error (400 Bad Request):
json
Copy code
{
  "mensaje": "El correo ya está registrado"
}
Cómo Probar la Aplicación
Pruebas Unitarias:

Ejecuta los tests unitarios con:
bash
Copy code
mvn test
Esto ejecutará las pruebas de los servicios y validará que las funcionalidades principales (creación de usuario, manejo de errores, generación de JWT) funcionan correctamente.
Base de Datos H2:

La base de datos H2 se configura automáticamente al ejecutar el proyecto.
Puedes acceder a la consola web de H2 en http://localhost:8080/h2-console.
Credenciales de H2:
JDBC URL: jdbc:h2:mem:apiusuariosdb
Usuario: sa
Contraseña: (vacío)
Notas Adicionales
Swagger te permite interactuar directamente con la API desde tu navegador. Accede a http://localhost:8080/swagger-ui.html para probar los endpoints.
Para ajustar la configuración, edita el archivo application.properties ubicado en src/main/resources.

