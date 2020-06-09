package ast.tipos;
import ast.NodoAst;

import stat.EquivalenciaTipos;

public interface Tipo extends NodoAst {
	public EnumTipo getTipo();

	default public boolean compatibleCon(Tipo t) {
		return EquivalenciaTipos.equivalentes(this, t,
				EquivalenciaTipos.ClaseEquiv.COMPATIBLES);
	}

	default public boolean igual(Tipo t) {
		return EquivalenciaTipos.equivalentes(this, t,
				EquivalenciaTipos.ClaseEquiv.IGUALES);
	}
}
