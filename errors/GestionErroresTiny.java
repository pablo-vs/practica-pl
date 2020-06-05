package errors;

import alex.UnidadLexica;
import stat.VincException;

public class GestionErroresTiny {
	public void errorLexico(int fila, int chr, String lexema) {
		System.out.println("ERROR fila "+fila+", col "+chr+": Caracter inexperado: "+lexema); 
		System.exit(1);
	}  
	public void errorSintactico(UnidadLexica unidadLexica) {
		System.out.print("ERROR fila "+unidadLexica.fila()+": Elemento inexperado "+unidadLexica.value);
		System.exit(1);
	}

	public void errorVinculo(VincException e) {
		// TODO mejorar esto
		System.err.println("Error en " + e.iden + ": " + e.getMessage());
	}
}
