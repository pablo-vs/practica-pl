package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoString;

public class ConstString extends Const implements HojaAst {

	private String value;

	public ConstString(String txt) {
		super(txt);
		setTipo(new TipoString());
		value = txt;
	}

	@Override
	public String print() {return value;}

	@Override
	public String getName() {return "ConstString: " + value;}
}


