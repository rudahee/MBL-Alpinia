# Elección de Controladores
La elección de implementar solo ciertos controladores en lugar de un CRUD completo se basa en las necesidades específicas.
De la aplicación a construir. Esto permite centrarme en hacer lógica en el backend (creando consultas personalizadas).
Haciendo más lógica en los servicios, etc., sacando esa lógica de pequeñas consultas en el frontend. 
Esto me ha llevado a limitar la cantidad de controladores a los que son necesarios para hacer funcionar la aplicación.

A futuro, este enfoque también me permitiría una evolución más orgánica del proyecto, donde se pueden añadir más funcionalidades a medida que se necesiten más funciones adicionales.


-----

# Uso excesivo de @Query
Esto es algo realizado a propósito, aunque realmente esto complica un poco el desarrollo. Quería mostrar también el 
Código SQL (o HQL /JPQL en este caso) dentro del propio proyecto. Igualmente, esto realmente proporciona un mayor control 
Sobre las consultas generadas y optimiza el rendimiento si se realizan de forma correcta según las necesidades del proyecto.----

-----

# Estructura de Paquetes
La estructura de los paquetes en el proyecto está diseñada para ser modular, lo que facilita tanto la mantenibilidad 
como la escalabilidad del sistema. Pero aún así no empleo arquitectura hexagonal o "clean archictecture" debido a que me parecen arquitecturas demasiado complejas para el proyecto tan simple que tenemos aquí.

Con este modelo, cada paquete tiene una responsabilidad específica, aunque al no existir distintos cruds, las DTO y 
los repositorios se reusan entre los distintos paquetes. Cada paquete contiene modelos, repositorios, servicios y 
controladores que están relacionados entre sí para seguir con este modelo.

----

# Eleccion de Gradle
Gradle es mi herramienta de preferencia, sobre todo para proyectos pequeños, aunque conozco Maven igual de bien. 
Personalmente prefiero Gradle por el formato simple que utiliza (sobre todo basado en Groovy), que comparado con Maven es mucho más legible, pero cuando un proyecto empieza a necesitar varios módulos es cuando mi preferencia se inclina totalmente hacia Maven. 

Dicho esto, para un proyecto de estas dimensiones con pocas dependencias, Gradle es mi opción preferida.

----

# Uso de Lombok y exclusion de MapStruct

En cuanto a las bibliotecas utilizadas, se eligió Lombok por su capacidad para reducir el boilerplate y código
autogenerado con click derecho, lo que mejora la legibilidad del código. 

Por otro lado, aunque MapStruct es mi herramienta por defecto para el mapeo entre objetos, decidí excluirla en este caso, 
Esto se debe a que la conversión entre objetos en este proyecto es relativamente simple y prefería repetir un poco
de código, pero tener la oportunidad de usar la API Stream de las versiones de Java8 o superiores.

----

# Tests

He realizado test para alguna de las funcionalidades de `UsuarioService` y `UsuarioController', además de estas mismas clases
para las entidades de Group. Creo que es suficiente para mostrar mis destrezas, y dan una cobertura de casos algo amplia.
Creo que no tiene sentido gastar tiempo en realizar un test para cada método en un proyecto de estas características.
----

# Docker
He tratado de desplegar la aplicación en contenedores. El back ha ido bien porque estoy más acostumbrado (aunque no es algo que hago frecuentemente)
pero en el frontend si he tenido un problema, y aunque la app se construye y arranca en el servidor de aplicaciones, se queda cargando los módulos con
la pantalla en blanco. La API del backend funciona perfectamente y existe persistencia, por lo que esto es un problema del frontend específicamente.

**Para "dockerizar"  la app he eliminado el profile de Spring Boot "dev" de la aplicacion java, asi que para tester habría que volverlo a crear.**
