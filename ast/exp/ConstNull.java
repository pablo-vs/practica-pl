package ast.exp;
import ast.HojaAst;

public class ConstNull extends Const implements HojaAst {
	@Override
	public String print() {return "null";}
	@Override
	public String getName() {return "ConstNull";}
}



