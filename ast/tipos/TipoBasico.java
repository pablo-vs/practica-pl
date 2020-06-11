package ast.tipos;
import ast.NodoAst;

public abstract class TipoBasico extends Tipo {
	@Override
	public int getSize() {return 1;}
	@Override
	public NodoAst[] getChildren() {return new NodoAst[0];}
	@Override
	public void printAst(StringBuilder result, String prefix) {
		result.append(prefix + "\\__" + getName() + "\n");
	}
}
