.PHONY: all clean

all: build

build: sources.txt 
	javac -cp 'jlex.jar:cup.jar:' @sources.txt  -d build

sources.txt: alex/AnalizadorLexicoTiny.java asint/AnalizadorSintacticoTiny.java ast asint alex errors
	find ast asint alex errors stat/V* -iname '*.java' > sources.txt

alex/AnalizadorLexicoTiny.java: alex/lexico.l
	cd alex; java -cp '../jlex.jar:' JLex.Main lexico.l
	mv alex/lexico.l.java alex/AnalizadorLexicoTiny.java

asint/AnalizadorSintacticoTiny.java: asint/sintaxis.cup
	cd asint; java -cp '../cup.jar:' java_cup.Main -parser AnalizadorSintacticoTiny -symbols ClaseLexica -nopositions -locations sintaxis.cup

clean:
	rm -r build/
	find . -iname 'javac*' -delete
