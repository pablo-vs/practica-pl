package errors;

import alex.UnidadLexica;
import stat.VincException;
import stat.CompException;

public class GestionErroresTiny {
	
	private int numErrores = 0;

	public void errorLexico(int fila, int col, String lexema) {
		System.out.println("ERROR fila "+fila+", columna "+col+": Caracter inesperado: "+lexema); 
		++numErrores;
		//System.exit(1);
	}  
	public void errorSintactico(UnidadLexica unidadLexica) {
		System.out.println("ERROR fila "+unidadLexica.fila()+", columna "+unidadLexica.col()+": Elemento inesperado "+unidadLexica.lexema());
		++numErrores;
		//System.exit(1);
	}

	public void errorVinculo(VincException e) {
		// TODO mejorar esto
		System.out.println("ERROR fila " + e.fila + ", columna " + e.col + ": " + e.iden + " - " + e.getMessage());
		++numErrores;
	}

	public void errorComprob(CompException e) {
		// TODO mejorar esto
		System.out.println("ERROR fila " + e.fila + ", columna " + e.col + ": " + "Error de tipo: " + e.getMessage());
		e.printStackTrace();
		++numErrores;
	}

	public int numErrores() {return numErrores;}
}
