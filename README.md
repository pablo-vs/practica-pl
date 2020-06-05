# Pŕactica Procesadores de Lenguaje
Marcos Brian Leiva y Pablo Villalobos

El programa lee del fichero "input.txt" que se encuentre
en el directorio de ejecución. Proveemos dos ficheros
de prueba que demuestran las características del lenguaje:
"input.txt" e "input2.txt".

El archivo "sources.txt" contiene los nombres de todas las
fuentes .java para facilitar la compilacion.

La sintaxis está explicada [aquí](https://docs.google.com/document/d/1tiB-HO9mIKyB_R5_gvYdXtpKqpvApQXVxoce5xP-iac)


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
