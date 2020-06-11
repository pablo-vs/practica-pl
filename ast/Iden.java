package ast;

import alex.TokenValue;

public class Iden extends HojaAst {
	private String value;
	public String getName() {return "Iden: " + value;}
	public String print() {return value;}

	public Iden(String val) {
		value = val;
	}

	public Iden(TokenValue val) {
		super(val.fila, val.col);
		value = val.lexema;
	}
}
