package ast.exp;

public abstract class Const extends Exp {
	private boolean isNull;
	public boolean isNull() {return isNull;}

	public Const() {
		this("",-1,-1);
	}

	public Const(int fila, int col) {
		this("", fila, col);
	}

	public Const(String txt, int fila, int col) {
		super(fila, col);
		if (txt.equals("null"))
			isNull = true;
		else
			isNull = false;
	}
}

