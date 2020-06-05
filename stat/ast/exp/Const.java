package ast.exp;
import ast.NodoAst;

public abstract class Const extends Exp {
	private boolean isNull;
	public boolean isNull() {return isNull;}

	public Const() {
		isNull = true;
	}

	public Const(String txt) {
		if (txt.equals("null"))
			isNull = true;
		else
			isNull = false;
	}
}

