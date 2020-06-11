package ast;
import ast.exp.Exp;

public class If extends Inst {
	public EnumInst getInst() {return EnumInst.IF;}
	private Exp condIf;
	private Block block;
	private Block blockElse;

	public If(Exp e, Block b, Block elseB, int fila) {
		super(fila, 0);
		condIf = e;
		block = b;
		blockElse = elseB;
	}

	public If(Exp e, Block b, int fila) {
		this(e, b, null, fila);
	}

	public If(Exp e, Block b) {
		this(e, b, null, -1);
	}

	public If(Exp e, Block b, Block elseB) {
		this(e, b, elseB, -1);
	}

	public Exp getCond() {return condIf;}
	public Block getBlock() {return block;}
	public Block getBlockElse() {return blockElse;}
	@Override
	public String getName() {return "If";}
	@Override
	public NodoAst[] getChildren() {
		if (blockElse == null)
			return new NodoAst[] {condIf, block};
		else
			return new NodoAst[] {condIf, block, blockElse};
	}
}
