package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoInt;

public class ConstInt extends Const implements HojaAst {

	private boolean inf;
	private int value;

	public ConstInt(String txt) {
		super(txt);
		setTipo(new TipoInt());
		if (txt.equals("inf")) {
			inf = true;
		} else {
			inf = false;
			value = Integer.parseInt(txt);
		}
	}

	@Override
	public String print() {
		if (inf)
			return "inf";
		else
			return String.valueOf(value);
	}

	@Override
	public String getName() {return "ConstInt: " + value;}
}

