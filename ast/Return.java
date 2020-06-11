package ast;
import ast.exp.Exp;

public class Return extends Inst {
	public EnumInst getInst() {return EnumInst.RETURN;}
	private Exp val;

	public Return(Exp val, int fila) {
		super(fila, 0);
		this.val = val;
	}

	public Return(Exp val) {
		this(val, -1);
	}

	@Override
	public String getName() {return "Return";}
	@Override
	public NodoAst[] getChildren() {return new NodoAst[] {val};}
}
