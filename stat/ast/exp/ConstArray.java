package ast.exp;
import ast.NodoAst;
import java.util.List;

public class ConstArray extends Const implements NodoAst {

	private Exp [] values;

	public ConstArray(Exp ... vls) {
		super("");
		values = new Exp[vls.length];
		for(int i = 0; i < vls.length; ++i) {
			values[i] = vls[i];
		}
	}

	public ConstArray(List<Exp> vls) {
		super("");
		values = new Exp[0];
		values = vls.toArray(values);
	}

	@Override
	public String print() {
		StringBuilder res = new StringBuilder();
		res.append("[");
		for(Exp e : values)
			res.append(e.print());
			res.append(", ");
		res.append("]");
		return res.toString();
	}

	@Override
	public String getName() {return "ConstArray";}
	@Override
	public NodoAst[] getChildren() {return values;}
}

