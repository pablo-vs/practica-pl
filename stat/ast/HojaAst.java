package ast;

public interface HojaAst extends NodoAst {
	default public NodoAst[] getChildren() {return new NodoAst[0];}
	default public void printAst(StringBuilder result, String prefix) {
		result.append(prefix + "\\__" + getName() + "\n");
	}
}
