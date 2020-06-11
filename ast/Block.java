package ast;

public class Block extends Inst {
	public EnumInst getInst() {return EnumInst.BLOCK;}
	private Prog prog;

	public Block(Prog p) {
		prog = p;
	}

	public String getName() {return "Block";}
	public Prog getProg() {return prog;}
	public NodoAst[] getChildren() {return prog.getChildren();}
}
