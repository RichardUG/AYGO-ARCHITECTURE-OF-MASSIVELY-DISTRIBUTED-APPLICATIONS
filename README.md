# ARCHITECTURE OF MASSIVELY DISTRIBUTED APPLICATIONS

## Descripción

Este proyecto orienta a crear una arquitectura de twitter como servicio de streaming y mensajeria tanto en desarrollo como en cloud

## Arquitectura

El proyecto se basa en la siguiente arquitectura 

![](/diagramas/arquitectura.png)

Nos muestra como los usuarios a través de la pagina de twitter se conecta por un DNS que da los parametros de localización y acceso a las diferentes rutas, seguido de un cloudFront que es el despliegue del front en cloud que esta alojado en un s3 bucket; el cloudFront ademas tiene dos conexiones una para el caso de transmisiones en directo y la otra para las operaciones de tweeter y sus componentes.

En el caso de la transmisión en directo se conecta a un mediaLive que es el transmisor de video y posteriormente a un mediaPackage que es quien procesa y capsula la información para enviarla a losdemas usuarios al cloudFront, además el mediaPackage tiene un SecretManager para proteger la conexión

Por otro lado en cuanto a las operaciones de tweeter pasa por un waf para proteger la conexión y trabaja de la mano con Cognito para autenticar y autorizar al usuario que esta intentando acceder, tras pasar las barreras de seguridad,  se conecta a un APIGateway que distribuye a cada función lambda, que genera el CRUD para tweets, comentarios, usuarios y reacciones, cada una de estas interacciones se comunica con tablas de DynamoDB

Ademas la arquitectura de datos la podemos ver del siguiente modo

![](/diagramas/Twitter class diagram.png)


## Prototipo en AWS

En el codigo del prototipo se crearon las clases correspondientes al diagrama de clases con un estructura pojo que nos permitio poder crear los handler para insrtanciar los diferentes lambda que se crearian a continuación, estos lambda generan las funciones CRUD para usuarios, tweets, comentarios y reacciones

![](/img/Capture10.png)

Además tambien creamos las tablas DynamoDB a las cuales al ejecutarse las funciones lambda guardaria los datos

![](/img/Capture11.png)

Y posterirormente se creo el apigateway con cada metodo para publicarlo (En el prototipo es faltante los valores de admisión para que los request reciban los datos)

![](/img/Capture9.png)


## Nota

El despliegue en AWS se creo usando CloudFormation y en el repositorio actual lo encontramos como ```deploy.yaml``` y lo podemos usar tras ejecutar nuestra función ```mvn package``` y subir nuestro archivo ```jar``` en un bucket s3 que creemos en amazon (El nombre actual que se usa para el bucket es twitertesttesettest, en caso de cambiarlo, cambiar las direcciones de las variables CodeUri del archivo .yaml) y despues creando un stack en la opción de cloudformation y publicando nuestro archivo .yaml

## Autor
[Richard Santiago Urrea Garcia](https://github.com/RichardUG)
## Licencia & Derechos de Autor
**©** Richard Santiago Urrea Garcia, Ingeniero de Sistemas

Licencia bajo la [GNU General Public License](https://github.com/RichardUG/AYGO-Intro-virtualizacion-y-programacion-distribuida/blob/main/LICENSE).