package ast;

public class Block implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.BLOCK;}
	private Prog prog;

	public Block(Prog p) {
		prog = p;
	}

	public String getName() {return "Block";}
	public NodoAst[] getChildren() {return prog.getChildren();}
}
