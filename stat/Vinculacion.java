package stat;

import java.util.HashMap;

import ast.*;
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
						vincular((Dec) i);
						break;
					case ASIG:
						vincular((Asig) i);
						break;
					default:
				}
			} catch (VincException e) {
				err.errorVinculo(e);
			}
		}	
	}

	public void vincular(Dec d) throws VincException {
		//vincular(d.tipo);
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

	public void vincular(Asig a) throws VincException {
		switch(a.getAsignable().tipo) {
			case VAR:
				a.setDec((Dec) vincular(a.getAsignable().getIden(), Vinculo.Tipo.VAR));
				break;
			default:
		}
	}

	/* Intenta vincular un identificador, el parámetro contexto es
	 * la clase de identificador (variable, tipo, funcion) que se
	 * espera por el contexto.
	 * Devuelve la declaracion.
	 */
	public NodoAst vincular(Iden id, Vinculo.Tipo contexto) throws VincException {
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
