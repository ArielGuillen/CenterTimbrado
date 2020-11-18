# Facturama SDK
Librería para consumir la API Web de Facturama.

## Dependencias 
* [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.2)
* [jcommande](https://mvnrepository.com/artifact/com.beust/jcommander/1.72)
* [joda-time](https://mvnrepository.com/artifact/joda-time/joda-time/2.9.9)
* [json](https://mvnrepository.com/artifact/org.json/json/20180130)
* [okhttp](https://mvnrepository.com/artifact/com.squareup.okhttp/okhttp/2.7.5)
* [okio](https://mvnrepository.com/artifact/com.squareup.okio/okio/1.6.0)

## Inicio rápido

#### Configuración  #####
Si el valor de la variable  ```isDevMode``` es ```true``` la librería esta en modo sandbox
 ```java
  new FacturamaApi("USUARIO","CONTRASEÑA", isDevMode);
```
Y si el valor de la variable  ```isDevMode``` es ```false``` la librería esta en modo producción
 ```java
  new FacturamaApi("USUARIO","CONTRASEÑA",isDevMode);
```

## Operaciones Web API

- Crear, Consultar Cancelar CFDI así como descargar XML, PDF y envió de
   estos por mail.
- Consultar Perfil y Suscripción actual
- Carga de Logo y Certificados Digitales
- CRUD de Productos, Clientes, Sucursales y Series.

Algunos ejemplos: [aquí](https://github.com/Facturama/facturama-java-sdk/wiki/API-Web)

*Todas las operaciones son reflejadas en la plataforma web.*

## Operaciones API Multiemisor

- Crear, Consultar, Cancelar descarga de XML
- CRUD de CSD (Certificados de los Sellos Digitales).

Algunos ejemplos: [aquí](https://github.com/Facturama/facturama-java-sdk/wiki/API-Multiemisor)

*Las operaciones no se reflejan en la plataforma web.*
