package ast;

public abstract class HojaAst extends NodoAst {
	public HojaAst() {}
	public HojaAst(int f,int c) {super(f,c);}
	@Override
	public NodoAst[] getChildren() {return new NodoAst[0];}
	@Override
	public void printAst(StringBuilder result, String prefix) {
		result.append(prefix + "\\__" + getName() + "\n");
	}
}
