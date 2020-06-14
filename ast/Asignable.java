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

	public final TipoAs tipoAs;
	private Iden iden;
	private Asignable child;
	private Exp exp;


	// Solo uno de los dos se utiliza
	private Dec declaracion = null;
	private Argumento argumento = null;

	private Tipo tipo;
	private int prof;

	// Variable
	public Asignable(Iden id) {
		super(id.fila, id.col);
		iden = id;
		tipoAs = TipoAs.VAR;
	}

	// Dereferencia puntero;
	public Asignable(Asignable as) {
		super(as.fila, as.col);
		child = as;
		tipoAs = TipoAs.PUNT;	
	}

	// Campo de un struct
	public Asignable(Asignable struct, Iden campo) {
		super(campo.fila, campo.col);
		child = struct;
		iden = campo;
		tipoAs = TipoAs.CAMPO;
	}

	// Elemento de un array/tupla/dict
	public Asignable(Exp accesor, Asignable as) {
		super(as.fila, as.col);
		exp = accesor;
		child = as;
		tipoAs = TipoAs.ACCESOR;
	}

	@Override
	public String getName() {
		switch(tipoAs) {
			case VAR:
				return "Asignable " + tipoAs.getName() + ": " + iden.print();
			case PUNT:
				return "Asignable " + tipoAs.getName();
			case CAMPO:
				return "Asignable " + tipoAs.getName();
			case ACCESOR:
				return "Asignable " + tipoAs.getName();
			default:
				return "Asignable";
		}
	}

	public String print() {
		switch(tipoAs) {
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
		switch(tipoAs) {
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
		if(tipoAs == TipoAs.VAR)
			return this;
		else
			return child.getBottom();
	}

	public void setDec(Dec d) {declaracion = d;}
	public Dec getDec() {return declaracion;}
	public void setArg(Argumento a) {argumento = a;}
	public Argumento getArg() {return argumento;}
	public void setProf(int p) {prof = p;}
	public int getProf() {return prof;}
	public void setTipo(Tipo t) {tipo = t;}
	public Tipo getTipo() {return tipo;}
}
