package stat;

import java.util.HashMap;

import ast.*;
import ast.exp.*;
import ast.tipos.*;
import errors.GestionErroresTiny;

public class Vinculacion {
	
	private HashMap<String, Vinculo> tablaSimbolos = new HashMap<>();
	private Vinculacion parent;
	private GestionErroresTiny err;

	public Vinculacion(GestionErroresTiny e) {
		parent = null;
		err = e;
	}

	public Vinculacion(Vinculacion p) {
		parent = p;
		err = p.err;
	}

	public void vincular(Prog p) {
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			try {
				if(i.getInst() == null)
					continue;
				switch(i.getInst()) {
					case DEC:
						vincularDec((Dec) i);
						break;
					case ASIG:
						vincularAsig((Asig) i);
						break;
					default:
				}
			} catch (VincException e) {
				err.errorVinculo(e);
			}
		}	
	}

	public void vincularDec(Dec d) throws VincException {
		vincularTipo(d.getTipo());
		String id;
		if (d.getIden() != null)
			// Si la declaracion no tiene asignación, tomamos directamente el id
			id = d.getIden().getName();
		else
			// Si la declaracion tiene asignación, tomamos el id de la asignación
			id = d.getAsig().getAsignable().getIden().getName();

		if(!tablaSimbolos.containsKey(id)) {
			tablaSimbolos.put(id, new Vinculo(Vinculo.Tipo.VAR, d));
		} else {
			// Error
			throw new VincException(id, "Identificador ya existente");
		}
	}

	public void vincularAsig(Asig a) throws VincException {
		vincularExp(a.getExp());
		vincularAsignable(a.getAsignable());
	}

	public void vincularAsignable(Asignable a) throws VincException{
		switch(a.tipo) {
			case VAR:
				a.setDec((Dec) vincularIden(a.getIden(), Vinculo.Tipo.VAR));
				break;
			case CAMPO: {
				vincularAsignable(a.getStruct());
				break;
			}
			default:
		}
	}

	public void vincularTipo(Tipo t) throws VincException {
		switch(t.getTipo()) {
			case IDENTIPO: {
				// TOOD
				break;
			}
			case STRUCT: {
				TipoStruct st = (TipoStruct) t;
				HashMap<String, CampoStruct> mapa = new HashMap<>();
				for(CampoStruct c: st.getCampos()) {
					if (mapa.containsKey(c.getIden().print())) {
						throw new VincException(c.getIden().print(), "Campo duplicado");
					} else {
						mapa.put(c.getIden().print(), c);
						vincularTipo(c.getTipo());
					}
				}
				break;
			}	
			default:
		}
	}

	public void vincularExp(Exp e) throws VincException {
		if (e.getOp() == Operator.NONE) {
			if (e instanceof Variable) {
				Variable var = (Variable) e;
				var.setDec((Dec) vincularIden(var.getIden() , Vinculo.Tipo.VAR));
			}
			else {
				// TODO Constantes compuestas como listas
			}
		}
		for(Exp op : e.getOperands()) {
			vincularExp(op);
		}
	}

	/* Intenta vincular un identificador, el parámetro contexto es
	 * la clase de identificador (variable, tipo, funcion) que se
	 * espera por el contexto.
	 * Devuelve la declaracion.
	 */
	public NodoAst vincularIden(Iden id, Vinculo.Tipo contexto) throws VincException {
		Vinculo v = tablaSimbolos.get(id.getName());
		if (v == null) {
			// Error no encontrado
			throw new VincException(id.getName(), "Identificador no encontrado");
		}
		if (v.tipo != contexto) {
			// Error clase inesperada
			throw new VincException(id.getName(), "Se esperaba " + contexto.getVal()
					+ ", pero se ha encontrado " + v.tipo.getVal());
		}
		return v.declaracion;
	}

	public Vinculacion abreBloque() {
		return new Vinculacion(this);
	}

	public Vinculacion cierraBloque() {
		return parent;
	}

}
