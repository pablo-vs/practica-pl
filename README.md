# Pŕactica Procesadores de Lenguaje

Marcos Brian Leiva y Pablo Villalobos

El programa lee del fichero "input.txt" que se encuentre
en el directorio de ejecución. Proveemos dos ficheros
de prueba que demuestran las características del lenguaje:
"input.txt" e "input2.txt".

El archivo "sources.txt" contiene los nombres de todas las
fuentes .java para facilitar la compilacion.

La sintaxis está explicada [aquí](https://docs.google.com/document/d/1tiB-HO9mIKyB_R5_gvYdXtpKqpvApQXVxoce5xP-iac)

Para compilar, se pueden ejecutar los siguientes comandos

```
java -cp '../jlex.jar:' JLex.Main lexico.l
java -cp '../cup.jar:' java_cup.Main -parser AnalizadorSintacticoTiny -symbols ClaseLexica -nopositions sintaxis.cup
```

```
javac -cp 'jlex.jar:cup.jar:' @sources.txt  -d build
```

```
java -cp '../cup.jar:../jlex.jar:' asint.Main
```

También se puede usar `make` para compilar, `make clean` para eliminar los archivos compilados.

## Tests

Incluimos una serie de tests que se pueden ejecutar con `make test`. Son programas cortos que comprueban las características del lenguaje. Cada test tiene un archivo en test/inputs que contiene el código del programa, y un archivo en tests/outputs que contiene la salida
esperada.

## Estructura de la práctica

El paquete stat contiene las clases encargadas de la comprobación de la semántica estática.
El paquete gen contiene la generación de código, incluyendo funciones predefinidas en el paquete gen.fun.


## Lista de características del lenguaje

- Declaración de variables y arrays
- Bloques anidados
- Punteros y memoria dinámica
- Registros
- Funciones, incluyendo definiciones anidadas
- Tipos enteros, booleanos y decimales
- Definición de tipos con nombre
- Gestión de errores indicando fila y columna


## Detalles de implementación

- Los decimales se redondean y se tratan como enteros
- La compilación prosigue hasta la fase de vinculación intentando ignorar los errores anteriores. Si hay fallos durante la fase de vinculación o comprobación, no se pasa a la siguiente fase.

## Modificaciones respecto a la entrega 2

- Hemos modificado la jerarquía de designadores, para facilitar la generación de código.
- También hemos eliminado la distinción entre identificadores de tipo y de variable/función del léxico para tratarla durante la vinculación.
