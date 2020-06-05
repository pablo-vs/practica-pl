package ast;

public interface NodoAst {
	public String getName();
	public NodoAst[] getChildren();
	default public void printAst(StringBuilder result, String prefix) {
		result.append(prefix+"\\__"+getName()+"\n");
		String newPref = prefix + "|" + " ".repeat(getName().length()+2);
		for (NodoAst c : getChildren()) {
			c.printAst(result, newPref);
		}
	}
}
