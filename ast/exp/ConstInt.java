package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoInt;
import alex.TokenValue;

public class ConstInt extends ConstHoja {

	private boolean inf;
	public final int value;

	public ConstInt(String txt, int fila, int col) {
		super(txt, fila, col);
		setTipo(new TipoInt());
		if (txt.equals("inf")) {
			inf = true;
			value = 0;
		} else {
			inf = false;
			value = Integer.parseInt(txt);
		}
	}

	public ConstInt(TokenValue val) {
		this(val.lexema, val.fila, val.col);
	}

	public ConstInt(String txt) {
		this(txt, -1, -1);
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

