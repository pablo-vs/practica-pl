package stat;

import ast.NodoAst;

public class Vinculo {
	
	public enum Tipo {
		VAR("variable"), TIPO("tipo"), FUN("funcion");

		private final String val;

		Tipo(String v) {val = v;}

		public String getVal() {return val;}
	}

	public final NodoAst declaracion;	
	public final Tipo tipo;

	public Vinculo(Tipo t, NodoAst dec) {
		tipo = t;
		declaracion = dec;
	}
}
