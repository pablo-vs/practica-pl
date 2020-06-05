package errors;

import alex.UnidadLexica;
import stat.VincException;

public class GestionErroresTiny {
	
	private int numErrores = 0;

	public void errorLexico(int fila, int col, String lexema) {
		System.out.println("ERROR fila "+fila+", columna "+col+": Caracter inexperado: "+lexema); 
		++numErrores;
		//System.exit(1);
	}  
	public void errorSintactico(UnidadLexica unidadLexica) {
		System.out.println("ERROR fila "+unidadLexica.fila()+", columna "+unidadLexica.col()+": Elemento inexperado "+unidadLexica.value);
		++numErrores;
		//System.exit(1);
	}

	public void errorVinculo(VincException e) {
		// TODO mejorar esto
		System.err.println("Error en " + e.iden + ": " + e.getMessage());
		++numErrores;
	}

	public int numErrores() {return numErrores;}
}
