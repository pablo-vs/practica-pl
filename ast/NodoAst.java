package ast;

public abstract class NodoAst {
	
	public final int fila, col;

	public NodoAst() {
		fila = col = -1;
	}

	public NodoAst(int f, int c) {
		fila = f;
		col = c;
	}

	abstract public String getName();
	abstract public NodoAst[] getChildren();
	public void printAst(StringBuilder result, String prefix) {
		result.append(prefix+"\\__"+getName()+"\n");
		String newPref = prefix + "|" + " ".repeat(getName().length()+2);
		for (NodoAst c : getChildren()) {
			c.printAst(result, newPref);
		}
	}
}
