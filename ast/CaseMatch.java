package ast;
import ast.exp.Exp;

public class CaseMatch extends NodoAst {
	private Exp value;
	private Block block;

	public CaseMatch(Exp e, Block b) {
		value = e;
		block = b;
	}

	public Exp getValue() {return value;}
	public Block getBlock() {return block;}
	@Override
	public String getName() {return "CaseMatch";}
	@Override
	public NodoAst[] getChildren() {return new NodoAst[] {value, block};}

}
