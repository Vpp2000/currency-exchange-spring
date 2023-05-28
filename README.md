# DOCUMENTACION

## Index

- [¿COMO INTERPRETÉ LOS REQUERIMIENTOS?](#COMO-INTERPRETÉ-REQUERIMIENTOS)
- [ESQUEMA DE BASE DE DATOS](#ESQUEMA-BASE-DE-DATOS)
- [ESTRUCTURA DE CARPETAS](#ESTRUCTURA-CARPETAS)

## ¿COMO INTERPRETÉ LOS REQUERIMIENTOS?
- Lo que entendí básicamente es que se me pedía un sistema que permitiese almacenar monedas (o timpos de cambio según tengo entendido), almacenar su valor en el mercado internacional y permitir cálculos de una moneda a otra moneda.
- Investigando encontré que una manera de lograr implementar mi sistema es guardando los valores de todas mis monedas en función de una moneda común (**en mi caso tomé al dólar como base**).
- Finalmente, implementé un sistema que permita guardar las monedas y su valor en función de otra moneda (**este valor no lo valido yo, lo que yo entiendo es que puede ser como una regla de negocio y se espera que los usuarios guarden el valor de la moneda en función al dolar**) y que permite también el calculo del valor de una moneda en otra moneda.

## ESQUEMA DE BASE DE DATOS
![database_schema](https://github.com/Vpp2000/currency-exchange-spring/assets/48797063/e874aedf-eb76-41c5-a432-aa6b72417baa)

- Manejo tables de usuarios, roles y una tabla **user_roles** para el mapeo de roles.
- Con respecto a las monedas y sus valores:
  - **currency** maneja la información básica de una moneda y **exchange_rate_last** es una tabla que se enlaza a **currency** y que contiene su último valor en el mercado internacional respecto al dólar.
  - Opté por hacer dos tablas ya que no espero que **currency** sea consultado constantemente mientras que **exchange_rate_last** si sufre bastantes consultas y actualizaciones.
  - **exchange_rate** lo uso para mantener un historial de los cambios del valor de una moneda y almaceno el **username** para saber quien lo modificó y que moneda modificó (no sabía si enlazar la tabla con **exchange_rate_last** o **currency** porque si las filas de esas tablas sufren eliminaciones creí que sería apropiado no enlazarlas con mi tabla de historial).

## ESTRUCTURA DE CARPETAS
![folder_structure](https://github.com/Vpp2000/currency-exchange-spring/assets/48797063/93ae7733-b328-4841-8962-54210d3667dc)
- **api**: esta carpeta contiene los controladores, repositorios, servicios y helpers que dan vida a la API.
- **config**: esta carpeta contiene clases que sirven para configurar nuestra API.
- **entities**: esta carpeta viena a contener las clases que representan las tablas de la base de datos.
- **dtos**: esta carpeta contiene clases de propósito general que nos sirven para empaquetar diversos objetos.
- **exceptions**: esta carpeta nos permite definir excepciones más personalisadas.
- **security**: esta carpeta contiene las clases necesarias para manejar la protección de la API con JWT.

## ARQUITECTURA GENERAL DEL PROYECTO
![neoris_currency_api drawio](https://github.com/Vpp2000/currency-exchange-spring/assets/48797063/a5324237-a7fc-47cd-b42d-b9d59e9ccdd5)
- Los controladores permiten interceptar y manejar los Http Requests. Usan los servicios para construir sus respuestas.
- Los servicios manejan la lógica de negocio e interactúan con repositorios y helpers.
- Los helpers son clases que se crean para evitar acoplamiento entre servicios. Estos helpers también pueden interactuar con repositorios y manejar operaciones complejas.
- Los repositorios interactúan con la base de datos y son usados por los servicios.

## ENDPOINTS DE LA API
Al desplegar la aplicación en el puerto 9999 se puede acceder a Swagger mediante el URL http://localhost:9999/swagger-ui.html

- **POST /currency/{currencyId}**: permite crear una moneda desde cero con su valor en el mercado, código y nombre.
- **PUT /currency/{currencyId}/rate**: permite actualizar el valor de mercado de una moneda.
- **PUT /currency/{currencyId}**: permite actualizar el nombre y/o código de una moneda.
- **GET /currency/{currencyId**: permite obtener el detalle de una moneda (nombre, código y valor actual en el mercado)
- **GET /currency**: permite obtener la lista de monedas, pero sin su detalle (osea sin su valor)
- **POST /currency/exchange**: permite calcular un monto de una moneda en otra moneda.
- **POST /auth/login**: nos permite loguearnos.
- **POST /auth/register**: permite crear un usuario nuevo. Si no hay usuarios el primero que se cree sera el ADMIN (hay 2 roles, USER y ADMIN).