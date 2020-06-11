package ast.exp;
import ast.NodoAst;
import ast.FunCall;

public class ExpFun extends Exp  {
	private FunCall call;

	public ExpFun(FunCall c) {
		super(Operator.NONE);
		call = c;
	}

	@Override
	public String print() {return call.print();}
	@Override
	public String getName() {return call.getName();}
	@Override
	public NodoAst[] getChildren() {
		return call.getChildren();
	}

	public FunCall getCall() {
		return call;
	}
}
