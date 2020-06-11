package ast.exp;
import ast.NodoAst;
import ast.Asignable;

public class ExpAsig extends Exp  {
	private Asignable asig;

	public ExpAsig(Asignable a) {
		super(Operator.NONE);
		asig = a;
	}

	@Override
	public String print() {return asig.print();}
	@Override
	public String getName() {return asig.getName();}
	@Override
	public NodoAst[] getChildren() {
		return asig.getChildren();
	}

	public Asignable getAsignable() {
		return asig;
	}
}
