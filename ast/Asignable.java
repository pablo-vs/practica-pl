package ast;
import ast.exp.*;

public class Asignable implements NodoAst {
	
	enum TipoAs {
		VAR("var"), PUNT("punt"), CAMPO("campo"), ACCESOR("accesor");

		private final String txt;

		TipoAs(String txt) {this.txt = txt;}
		public String getName() {return txt;}
	}

	private TipoAs tipo;
	private Iden iden;
	private Asignable child;
	private Asignable struct;
	private Exp exp;


	// Variable
	public Asignable(Iden id) {
		iden = id;
		tipo = TipoAs.VAR;
	}

	// Dereferencia puntero;
	public Asignable(Asignable as) {
		child = as;
		tipo = TipoAs.PUNT;	
	}

	// Campo de un struct
	public Asignable(Asignable campo, Asignable struct) {
		child = campo;
		this.struct = struct;
		tipo = TipoAs.CAMPO;
	}

	// Elemento de un array/tupla/dict
	public Asignable(Exp accesor, Asignable as) {
		exp = accesor;
		child = as;
		tipo = TipoAs.ACCESOR;
	}

	@Override
	public String getName() {
		switch(tipo) {
			case VAR:
				return "Asignable " + tipo.getName() + ": " + iden.print();
			case PUNT:
				return "Asignable " + tipo.getName();
			case CAMPO:
				return "Asignable " + tipo.getName();
			case ACCESOR:
				return "Asignable " + tipo.getName();
			default:
				return "Asignable";
		}
	}

	@Override
	public NodoAst[] getChildren() {
		switch(tipo) {
			case VAR:
				return new NodoAst[0];
			case PUNT:
				return new NodoAst[] {child};
			case CAMPO:
				return new NodoAst[] {struct, child};
			case ACCESOR:
				return new NodoAst[] {child, exp};
			default:
				return new NodoAst[0];
		}
	}
}
