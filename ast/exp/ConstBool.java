package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoBool;
import alex.TokenValue;

public class ConstBool extends ConstHoja {

	public final boolean value;

	public ConstBool(String txt, int fila, int col) {
		super(txt, fila, col);
		setTipo(new TipoBool());
		if (txt.equals("true")) {
			value = true;
		} else {
			value = false;
		}
	}

	public ConstBool(TokenValue val) {
		this(val.lexema, val.fila, val.col);
	}

	public ConstBool(String txt) {
		this(txt, -1, -1);
	}

	@Override
	public String print() {return String.valueOf(value);}

	@Override
	public String getName() {return "ConstBool: " + value;}
}

