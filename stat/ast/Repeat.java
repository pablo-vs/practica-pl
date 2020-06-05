package ast;
import ast.exp.Exp;

public class Repeat implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.REPEAT;}
	private Exp limit;
	private Exp cond;
	private Block block;

	public Repeat(Exp l, Block b) {
		limit = l;
		cond = null;
		block = b;
	}

	public Repeat(Exp l, Exp c, Block b) {
		this(l,b);
		cond = c;
	}

	@Override
	public String getName() {return "Repeat";}
	@Override
	public NodoAst[] getChildren() {
		if (cond == null)
			return new NodoAst[] {limit, block};
		else
			return new NodoAst[] {limit, cond, block};
	}
}
