package ast;
import ast.tipos.*;
import ast.exp.*;

public class Asig implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.ASIG;}
	private Asignable asig;
	private Exp exp;

	public Asig(Asignable as, Exp exp) {
		this.asig = as;
		this.exp = exp;
	}

	public String getName() {return "AsigVar";}
	public NodoAst[] getChildren() {return new NodoAst[] {asig, exp};}
}
