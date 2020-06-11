package ast;
import ast.exp.*;
import ast.tipos.Tipo;

public class Asignable extends NodoAst {
	
	public enum TipoAs {
		VAR("var"), PUNT("punt"), CAMPO("campo"), ACCESOR("accesor");

		private final String txt;

		TipoAs(String txt) {this.txt = txt;}
		public String getName() {return txt;}
	}

	public final TipoAs tipo;
	private Iden iden;
	private Asignable child;
	private Exp exp;

	private Dec declaracion;
	private int prof;

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
	public Asignable(Asignable struct, Iden campo) {
		child = struct;
		iden = campo;
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

	public String print() {
		switch(tipo) {
			case VAR:
				return iden.print();
			case PUNT:
				return "^(" + child.print() + ")";
			case CAMPO:
				return child.print() + "." + iden.print();
			case ACCESOR:
				return child.print() + "[" + exp.print() + "]";
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
				return new NodoAst[] {iden, child};
			case ACCESOR:
				return new NodoAst[] {child, exp};
			default:
				return new NodoAst[0];
		}
	}

	public Iden getIden() {return iden;}

	//public Asignable getStruct() {return struct;}

	public Asignable getChild() {return child;}
	public Exp getExp() {return exp;}

	public Asignable getBottom() {
		Asignable res;
		if(tipo == TipoAs.VAR)
			return this;
		else
			return child.getBottom();
	}

	public void setDec(Dec d) {
		declaracion = d;
	}
	
	public Dec getDec() {
		return declaracion;
	}

	public void setProf(int p) {prof = p;}
	public int getProf() {return prof;}
}
