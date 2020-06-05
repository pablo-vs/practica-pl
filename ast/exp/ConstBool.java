package ast.exp;
import ast.HojaAst;

public class ConstBool extends Const implements HojaAst {

	boolean value;

	public ConstBool(String txt) {
		super(txt);
		if (txt.equals("true")) {
			value = true;
		} else {
			value = false;
		}
	}

	@Override
	public String print() {return String.valueOf(value);}

	@Override
	public String getName() {return "ConstBool: " + value;}
}

