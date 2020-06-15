package ast.exp;
import ast.NodoAst;
import java.util.List;

public class ConstArray extends Const {

	private Exp [] values;

	private int dir;

	public ConstArray(Exp ... vls) {
		values = new Exp[vls.length];
		for(int i = 0; i < vls.length; ++i) {
			values[i] = vls[i];
		}
	}

	public ConstArray(List<Exp> vls, int fila, int col) {
		super(fila, col);
		values = new Exp[0];
		values = vls.toArray(values);
	}

	public Exp[] getValues() {return values;}

	public int getDir() {return dir;}
	public void setDir(int d) {dir = d;}

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

