package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoBool;

public class ConstBool extends Const implements HojaAst {

	public final boolean value;

	public ConstBool(String txt) {
		super(txt);
		setTipo(new TipoBool());
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

