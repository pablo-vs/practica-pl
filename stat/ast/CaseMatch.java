package ast;
import ast.exp.Exp;

public class CaseMatch implements NodoAst {
	private Exp value;
	private Block block;

	public CaseMatch(Exp e, Block b) {
		value = e;
		block = b;
	}

	@Override
	public String getName() {return "CaseMatch";}
	@Override
	public NodoAst[] getChildren() {return new NodoAst[] {value, block};}

}
