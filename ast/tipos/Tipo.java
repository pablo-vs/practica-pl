package ast.tipos;
import ast.NodoAst;

public interface Tipo extends NodoAst {
	public EnumTipo getTipo();

	default public boolean compatibleCon(Tipo t) {
		return getTipo() == t.getTipo();
		//TODO algoritmo descendente
	}
}
