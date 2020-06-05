package ast.exp;
import ast.NodoAst;
import java.util.List;

public class ConstDict extends Const implements NodoAst {

	private DictPair [] values;

	public ConstDict(DictPair ... vls) {
		super("");
		values = new DictPair[vls.length];
		for(int i = 0; i < vls.length; ++i) {
			values[i] = vls[i];
		}
	}

	public ConstDict(List<DictPair> vls) {
		super("");
		values = new DictPair[0];
		values = vls.toArray(values);
	}

	@Override
	public String print() {
		StringBuilder res = new StringBuilder();
		res.append("[");
		for(DictPair e : values)
			res.append(e.print());
			res.append(", ");
		res.append("]");
		return res.toString();
	}

	@Override
	public String getName() {return "ConstDict";}
	@Override
	public NodoAst[] getChildren() {return values;}
}


