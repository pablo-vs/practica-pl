package ast.tipos;
import ast.NodoAst;

import stat.EquivalenciaTipos;

public abstract class Tipo extends NodoAst {
	abstract public EnumTipo getTipo();

	public boolean compatibleCon(Tipo t) {
		return EquivalenciaTipos.equivalentes(this, t,
				EquivalenciaTipos.ClaseEquiv.COMPATIBLES);
	}

	public boolean igual(Tipo t) {
		return EquivalenciaTipos.equivalentes(this, t,
				EquivalenciaTipos.ClaseEquiv.IGUALES);
	}

	abstract public int getSize();
}
