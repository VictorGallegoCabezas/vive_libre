Imagen Docker: https://hub.docker.com/r/victorgallego/vive_libre

No sabía bien como queriais que expusiera la segunda parte de la prueba, así que he creado un endpoint http:/localhost:8081/filter/{palabra por la que se quiere filtrar} 
De esta manera realiza las tres acciones requeridas en la prueba: 
pintar en pantalla (lo saque por consola normal, pero podria haber sido con log4j pintando en algun fichero), devolver el libro con fecha adaptada, y ordenar la lista

Para lanzar la primera parte hay que llamar a http://localhost:8081/get-token para que el servicio realice una llamada a http://localhost:8080/token para obtener el token
