package ast.exp;
import ast.HojaAst;

public class ConstString extends Const implements HojaAst {

	private String value;

	public ConstString(String txt) {
		super(txt);
		value = txt;
	}

	@Override
	public String print() {return value;}

	@Override
	public String getName() {return "ConstString: " + value;}
}


