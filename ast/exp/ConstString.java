package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoString;
import alex.TokenValue;

public class ConstString extends ConstHoja {

	private String value;

	public ConstString(String txt, int fila, int col) {
		super(txt, fila, col);
		setTipo(new TipoString());
		value = txt;

	}

	public ConstString(String txt) {
		this(txt, -1, -1);
	}

	public ConstString(TokenValue val) {
		this(val.lexema, val.fila, val.col);
	}

	@Override
	public String print() {return value;}

	@Override
	public String getName() {return "ConstString: " + value;}
}


