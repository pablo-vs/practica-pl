package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoDec;

public class ConstDec extends Const implements HojaAst {

	private boolean inf;
	private double value;

	public ConstDec(String txt) {
		super(txt);
		setTipo(new TipoDec());
		if (txt.equals("inf")) {
			inf = true;
		} else {
			inf = false;
			value = Double.parseDouble(txt);
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


