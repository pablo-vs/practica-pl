package ast.exp;
import ast.NodoAst;

public class DictPair extends NodoAst {
	private Exp clave, valor;

	public DictPair(Exp cv, Exp vl) {
		clave = cv;
		valor = vl;
	}

	public String print() {
		return String.format("%s : %s", clave.print(), valor.print());
	}

	@Override
	public String getName() {return "DictPair";}

	@Override
	public NodoAst[] getChildren() {return new NodoAst[] {clave, valor};}
}
