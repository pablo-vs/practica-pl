package ast.exp;
import ast.HojaAst;
import ast.Iden;

public class Variable extends Exp implements HojaAst {
	private Iden iden;

	public Variable(Iden iden) {
		this.iden = iden;
	}

	@Override
	public String print() {return iden.print();}
	@Override
	public String getName() {return "Variable: " + print();}
}
