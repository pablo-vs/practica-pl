package stat;

import java.util.Map;

import ast.tipos.*;

public class EquivalenciaTipos {

	public enum ClaseEquiv {
		COMPATIBLES, IGUALES
	}

	/*
	 * Comprueba si dos tipos son estructuralmente equivalentes en cierto sentido
	 * El parámetro equiv permite elegir entre comprobar la igualdad
	 * o la compatibilidad de los tipos (Ej: Dec no es igual a Int pero sí compatible)
	 */
	public static boolean equivalentes(Tipo t1, Tipo t2, ClaseEquiv equiv) {
		
		switch(t1.getTipo()) {
			// Tipos básicos
			case TSTRING:
			case TBOOL:
			case TNULL:
				return t1.getTipo() == t2.getTipo();
			case TINT:
			case TDEC:
				if (equiv == ClaseEquiv.IGUALES) {
					return t1.getTipo() == t2.getTipo();
				}
				else {
					// Enteros y decimales son compatibles
					return t2.getTipo() == EnumTipo.TINT
						|| t2.getTipo() == EnumTipo.TDEC;
				}

			// Tipos compuestos
			default:
				return equivComp(t1, t2, equiv);
		}
	}

	private static boolean equivComp(Tipo ti1, Tipo ti2, ClaseEquiv equiv) {

		// Si no comparten constructor, no son equivalentes
		if (ti1.getTipo() != ti2.getTipo())
			return false;
		else
			switch(ti1.getTipo()) {
				case ARRAY: {
					TipoArray t1 = (TipoArray) ti1;
					TipoArray t2 = (TipoArray) ti2;
					if(t1.getTipoElem() == null || t2.getTipoElem() == null)
						return true;
					return equivalentes(t1.getTipoElem(), t2.getTipoElem(), equiv);
				}
				case TUPLA: {
					TipoTupla t1 = (TipoTupla) ti1;
					TipoTupla t2 = (TipoTupla) ti2;
					Tipo[] ts1 = t1.getTiposElems(),
						   ts2 = t2.getTiposElems();
					if (ts1.length != ts2.length)
						return false;
					for (int i = 0; i < ts1.length; ++i) {
						if (!equivalentes(ts1[i], ts2[i], equiv))
							return false;
					}
					break;
				}
				case DICT: {
					TipoDict t1 = (TipoDict) ti1;
					TipoDict t2 = (TipoDict) ti2;
					return equivalentes(t1.getTipoClave(), t2.getTipoClave(), equiv)
						&& equivalentes(t1.getTipoValor(), t2.getTipoValor(), equiv);
				}
				case STRUCT: {
					TipoStruct t1 = (TipoStruct) ti1;
					TipoStruct t2 = (TipoStruct) ti2;
					Map<String, CampoStruct> c1 = t1.getMapaCampos(),
								c2 = t2.getMapaCampos();
					if (c1.size() != c2.size() && equiv == ClaseEquiv.IGUALES)
						return false;
					for(String id1: c2.keySet()) {
						if(!c1.containsKey(id1)
								|| !equivalentes(c1.get(id1).getTipo(), c2.get(id1).getTipo(), equiv))
							return false;
					}
					break;
				}
				case PUNT: {
					if(equiv == ClaseEquiv.COMPATIBLES)
						return true;
					TipoPunt t1 = (TipoPunt) ti1;
					TipoPunt t2 = (TipoPunt) ti2;
					return equivalentes(t1.getTipoRef(), t2.getTipoRef(), equiv);
				}
			}
		return true;
	}
}
