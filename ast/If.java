package ast;
import ast.exp.Exp;

public class If implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.IF;}
	private Exp condIf;
	private Block block;
	private Block blockElse;

	public If(Exp e, Block b) {
		condIf = e;
		block = b;
		blockElse = null;
	}

	public If(Exp e, Block b, Block elseB) {
		this(e,b);
		blockElse = elseB;
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