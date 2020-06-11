package ast.exp;
import ast.NodoAst;

public abstract class ConstHoja extends Const {
	public ConstHoja() {}
	public ConstHoja(int fila, int col) {super(fila, col);}
	public ConstHoja(String txt, int fila, int col) {super(txt, fila, col);}
	@Override
	public NodoAst[] getChildren() {return new NodoAst[0];}
	@Override
	public void printAst(StringBuilder result, String prefix) {
		result.append(prefix + "\\__" + getName() + "\n");
	}
}

