package ast;

public abstract class Inst extends NodoAst {
	abstract public EnumInst getInst();
	public Inst(int f,int c) {super(f,c);}
	public Inst() {}
}
