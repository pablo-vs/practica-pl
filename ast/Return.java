package ast;
import ast.exp.Exp;

public class Return implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.RETURN;}
	private Exp val;

	public Return(Exp val) {
		this.val = val;
	}

	@Override
	public String getName() {return "Return";}
	@Override
	public NodoAst[] getChildren() {return new NodoAst[] {val};}
}
