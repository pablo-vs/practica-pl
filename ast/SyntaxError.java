package ast;

public class SyntaxError implements HojaAst, Inst {
	public EnumInst getInst() {return null;}
	public final int fila, columna;
	
	public SyntaxError() {
		fila = 0;
		columna = 0;
	};

	public SyntaxError(int f, int c) {
		fila = f;
		columna = c;
	}

	public String getName() {return "Error: " + fila + "," + columna;}
	public String print() {return "Syntax Error";}
}
