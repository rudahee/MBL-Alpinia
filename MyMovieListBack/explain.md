# Elección de Controladores
La elección de implementar solo ciertos controladores en lugar de un CRUD completo se basa en las necesidades específicas 
de la aplicacion a construir. Esto permite centrarme en hacer logica en el backend (creando consultas personalizadas, 
haciendo mas logica en los servicios, etc.) sacando esa logica de pequeñas consultas en el frontend. 
Esto me ha llevado a limitar la cantidad de controladores a los que son necesarios para hacer funcional la aplicacion.

A futuro este enfoque también me permitiria una evolución más orgánica del proyecto, donde se pueden añadir más funcionalidades 
a medida que se necesiten mas funciones adicionales. 
-----

# Uso excesivo de @Query
Esto es algo realizado a proposito, aunque realmente esto complica un poco el desarrollo, queria mostrar tambien el 
codigo SQL (o HQL /JPQL en este caso) dentro del propio proyecto. Igualmente esto realmente proporciona un mayor control 
sobre las consultas generadas y optimiza el rendimiento si se realizan de forma correcta segun las necesidades del proyecto.
----
# Estructura de Paquetes
La estructura de los paquetes en el proyecto está diseñada para ser modular, lo que facilita tanto la mantenibilidad 
como la escalabilidad del sistema. pero aun asi no empleo arquitectura hexagonal o "clean archictecture" debido a que me
parecen arquitecturas demasiado complejas para el proyecto tan simple que tenemos aqui.

Con este modelo cada paquete tiene una responsabilidad específica, aunque al no existir distintos cruds, las los DTO y 
los repositorios se reusan entre los distintos paquetes. Cada paquete contiene modelos, repositorios, servicios y 
controladores que están relacionados entre sí para seguir con este modelo.
----
# Eleccion de Gradle
Gradle es mi herramienta de preferencia, sobre todo para proyectos pequeños, aunque conozco Maven igual de bien. 
Personalmente prefiero gradle por el formato simple que utiliza (sobre todo basado en Groovy), que comparado con Maven es 
mucho mas legible, pero cuando un proyecto empieza a necesitar varios modulos es cuando mi preferencia se inclina totalmente
hacia Maven. Dicho esto, para un proyecto de estas dimensiones con pocas dependencias, Gradle es mi opcion preferida.

---

# Uso de Lombok y exclusion de MapStruct

En cuanto a las bibliotecas utilizadas, se eligió Lombok por su capacidad para reducir el boilerplate y codigo 
autogenerado con click derecho, lo que mejora la legibilidad del código. 

Por otro lado, aunque MapStruct es mi herramienta por defecto para el mapeo entre objetos, decidi excluirla en este caso, 
Esto se debe a que la conversión entre objetos en este proyecto es relativamente simple y preferia repetir un poco
de codigo, pero tener la oportunidad de usar la API Stream de las versiones de Java8 o superiores.

----

# Tests

He realizado test para alguna de las funcionalidades de `UsuarioService` y `UsuarioController` ademas de estas mismas clases
para las entidades de Group. Creo que es suficiente para mostrar mis destrezas, y dan una cobertura de casos algo amplia.
Creo que no tiene sentido gastar tiempo en realizar un test para cada metodo, en un proyecto de estas caracteristicas.