package ast;
import ast.tipos.*;
import ast.exp.*;

public class Asig extends Inst {
	public EnumInst getInst() {return EnumInst.ASIG;}
	private Asignable asig;
	private Exp exp;

	public Asig(Asignable as, Exp exp) {
		super(as.fila, 0);
		this.asig = as;
		this.exp = exp;
	}

	public String getName() {return "AsigVar";}
	public NodoAst[] getChildren() {return new NodoAst[] {asig, exp};}

	public Asignable getAsignable() {return asig;}

	public Exp getExp() {return exp;}
}
