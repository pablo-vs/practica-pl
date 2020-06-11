package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoDec;
import alex.TokenValue;

public class ConstDec extends ConstHoja {

	private boolean inf;
	private double value;

	public ConstDec(String txt, int fila, int col) {
		super(txt, fila, col);
		setTipo(new TipoDec());
		if (txt.equals("inf")) {
			inf = true;
		} else {
			inf = false;
			value = Double.parseDouble(txt);
		}
	}

	public ConstDec(String txt) {
		this(txt, -1, -1);
	}

	public ConstDec(TokenValue val) {
		this(val.lexema, val.fila, val.col);
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


