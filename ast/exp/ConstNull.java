package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoNull;
import alex.TokenValue;

public class ConstNull extends ConstHoja {

	public ConstNull() {
		setTipo(new TipoNull());
	}

	public ConstNull(int f, int c) {
		super(f,c);
		setTipo(new TipoNull());
	}

	public ConstNull(TokenValue val) {
		this(val.fila, val.col);
	}

	@Override
	public String print() {return "null";}
	@Override
	public String getName() {return "ConstNull";}
}
