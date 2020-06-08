package ast.exp;
import ast.HojaAst;
import ast.Iden;
import ast.Dec;

public class Variable extends Exp implements HojaAst {
	private Iden iden;

	private Dec dec;

	public Variable(Iden iden) {
		this.iden = iden;
	}

	@Override
	public String print() {return iden.print();}
	@Override
	public String getName() {return "Variable: " + print();}

	public void setDec(Dec d) {
		dec = d;
	}

	public Dec getDec() {
		return dec;
	}

	public Iden getIden() {
		return iden;
	}
}
