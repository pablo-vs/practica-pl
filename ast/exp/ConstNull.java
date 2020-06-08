package ast.exp;
import ast.HojaAst;
import ast.tipos.TipoNull;

public class ConstNull extends Const implements HojaAst {

	public ConstNull() {
		setTipo(new TipoNull());
	}

	@Override
	public String print() {return "null";}
	@Override
	public String getName() {return "ConstNull";}
}



