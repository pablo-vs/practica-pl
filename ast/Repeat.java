package ast;
import ast.exp.Exp;

public class Repeat extends Inst {
	public EnumInst getInst() {return EnumInst.REPEAT;}
	private Exp limit;
	private Exp cond;
	private Block block;

	public Repeat(Exp l, Exp c, Block b, int fila) {
		super(fila,0);
		limit = l;
		cond = c;
		block = b;
	}

	public Repeat(Exp l, Exp c, Block b) {
		this(l,c,b,-1);
	}

	public Repeat(Exp l, Block b, int fila) {
		this(l,null,b,fila);
	}
	public Repeat(Exp l, Block b) {
		this(l,b,-1);
	}

	public Exp getLimit() {return limit;}
	public Exp getCond() {return cond;}
	public Block getBlock() {return block;}
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
