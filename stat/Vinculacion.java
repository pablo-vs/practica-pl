package stat;

import java.util.HashMap;

import ast.*;
import ast.exp.*;
import ast.tipos.*;
import errors.GestionErroresTiny;

public class Vinculacion {
	
	private HashMap<String, Vinculo> tablaSimbolos = new HashMap<>();
	private final Vinculacion parent;
	private final GestionErroresTiny err;
	private final int profundidad;

	public Vinculacion(GestionErroresTiny e) {
		parent = null;
		err = e;
		profundidad = 0;
	}

	public Vinculacion(Vinculacion p) {
		parent = p;
		err = p.err;
		profundidad = p.profundidad + 1;
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
					case BLOCK:
						vincularBlock((Block) i);
						break;
					case TIPO_DEF:
						vincularDefTipo((DefTipo) i);
						break;
					case IF:
						vincularIf((If) i);
						break;
					case REPEAT:
						vincularRepeat((Repeat) i);
						break;
					case CASE:
						vincularCase((Case) i);
						break;
					case FUN_DEF:
						vincularDefFun((DefFun) i);
						break;
					case FUN_CALL:
						vincularFunCall((FunCall) i);
						break;
					default:
				}
			} catch (VincException e) {
				err.errorVinculo(e);
			}
		}
	}

	private void vincularDec(Dec d) throws VincException {
		d.setTipo(vincularTipo(d.getTipo()));
		String id;
		if (d.getAsig() == null) {
			// Si la declaracion no tiene asignación, tomamos directamente el id
			id = d.getIden().getName();
			try {
				addDeclaracion(id, new Vinculo(Vinculo.Tipo.VAR, d));
			} catch(VincException e) {
				throw new VincException(e, d.fila, d.col);
			}	
		} else {
			// Si la declaracion tiene asignación, tomamos el id de la asignación
			id = d.getAsig().getAsignable().getIden().getName();
			addDeclaracion(id, new Vinculo(Vinculo.Tipo.VAR, d));
			vincularAsig(d.getAsig());
		}
	}

	private void vincularAsig(Asig a) throws VincException {
		vincularExp(a.getExp());
		vincularAsignable(a.getAsignable());
	}

	private void vincularAsignable(Asignable a) throws VincException{
		switch(a.tipoAs) {
			case VAR:
				Vinculo v = vincularIden(a.getIden(), Vinculo.Tipo.VAR);
				a.setDec((Dec) v.declaracion);
				break;
			case CAMPO: {
				vincularAsignable(a.getChild());
				break;
			}
			case ACCESOR: {
				vincularAsignable(a.getChild());
				vincularExp(a.getExp());
			}
			case PUNT: {
				vincularAsignable(a.getChild());
			}
		}
	}

	// Devuelve el tipo dado, sustituyendo cada identificador de tipo por
	// el tipo que le corresponde
	private Tipo vincularTipo(Tipo t) throws VincException {
		Tipo result = t;
		switch(t.getTipo()) {
			case IDENTIPO: {
				TipoNombre tn = (TipoNombre) t;
				Vinculo v = vincularIden(tn.getValor(), Vinculo.Tipo.TIPO);
				DefTipo d = (DefTipo) v.declaracion;
				tn.setDef(d);
				result = d.getTipo();
				break;
			}
			case STRUCT: {
				TipoStruct st = (TipoStruct) t;
				HashMap<String, CampoStruct> mapa = new HashMap<>();
				for(CampoStruct c: st.getCampos()) {
					if (mapa.containsKey(c.getIden().print())) {
						throw new VincException(c.getIden().print(), "Campo duplicado", c.getIden().fila, c.getIden().col);
					} else {
						mapa.put(c.getIden().print(), c);
						c.setTipo(vincularTipo(c.getTipo()));
					}
				}
				st.setMapaCampos(mapa);
				break;
			}	
			case PUNT: {
				TipoPunt tp = (TipoPunt) t;
				tp.setTipoRef(vincularTipo(tp.getTipoRef()));
				break;
			}
			case ARRAY: {
				TipoArray ta = (TipoArray) t;
				ta.setTipoElem(vincularTipo(ta.getTipoElem()));
				break;
			}
			default:
		}
		return result;
	}

	private void vincularExp(Exp e) throws VincException {
		if (e instanceof ExpAsig) {
			vincularAsignable(((ExpAsig)e).getAsignable());
		} else {
			if (e.getOp() == Operator.NONE) {
				// TODO Constantes compuestas como listas
			}
			for(Exp op : e.getOperands()) {
				vincularExp(op);
			}
		}
	}


	private void vincularBlock(Block b) throws VincException {
		Vinculacion v = new Vinculacion(this);
		v.vincular(b.getProg());
	}
	
	private void vincularDefTipo(DefTipo d) throws VincException {
		vincularTipo(d.getTipo());
		String id = d.getIden().getName();
		try {
			addDeclaracion(id, new Vinculo(Vinculo.Tipo.TIPO, d));
		} catch (VincException e) {
			throw new VincException(e, d.getIden().fila, d.getIden().col);
		}
	}

	public void vincularIf(If i) throws VincException {
		vincularExp(i.getCond());
		vincularBlock(i.getBlock());
		if (i.getBlockElse() != null) vincularBlock(i.getBlockElse());
	}
	
	public void vincularRepeat(Repeat r) throws VincException {
		vincularExp(r.getLimit());
		if (r.getCond() != null) vincularExp(r.getCond());
		vincularBlock(r.getBlock());
	}

	public void vincularCase(Case c) throws VincException {
		vincularExp(c.getCond());
		CaseMatch[] branches = c.getBranches();
		for(int i = 0; i < branches.length; ++i)
			vincularCaseMatch(branches[i]);
	}
	
	public void vincularCaseMatch(CaseMatch c) throws VincException {
		vincularExp(c.getValue());
		vincularBlock(c.getBlock());
	}
	
	public void vincularDefFun(DefFun d) throws VincException {
		vincularTipo(d.getTipo());
		String id = d.getIden().getName();
		addDeclaracion(id, new Vinculo(Vinculo.Tipo.FUN, d));
		Vinculacion v = new Vinculacion(this);
		Argumento[] args = d.getArgs();
		for(int i = 0; i < args.length; ++i)
			v.vincularArg(args[i]); 
		v.vincular(d.getBlock().getProg());
	}
	
	private void vincularArg(Argumento a) throws VincException {
		Tipo tipo = vincularTipo(a.getTipo());
		String id = a.getIden().getName();
		addDeclaracion(id, new Vinculo(Vinculo.Tipo.VAR, new Dec(tipo, a.getIden())));
	}
	
	public void vincularFunCall(FunCall f) throws VincException {
		Vinculo v = vincularIden(f.getIden(), Vinculo.Tipo.FUN);
		f.setDef((DefFun) v.declaracion);
		Exp[] args = f.getArgs();
		for(int i = 0; i < args.length; ++i)
			vincularExp(args[i]); 
	}
	
	/* Intenta vincular un identificador, el parámetro contexto es
	 * la clase de identificador (variable, tipo, funcion) que se
	 * espera por el contexto.
	 * Devuelve la declaracion.
	 */
	private Vinculo vincularIden(Iden id, Vinculo.Tipo contexto) throws VincException {
		Vinculo v = tablaSimbolos.get(id.getName());
		if (v == null ) {
			if(parent == null) {
				// Error no encontrado
				throw new VincException(id.getName(), "Identificador no encontrado", id.fila, id.col);
			} else {
				v = parent.vincularIden(id, contexto);
			}
		}
		if (v.tipo != contexto) {
			// Error clase inesperada
			throw new VincException(id.getName(), "Se esperaba " + contexto.getVal()
					+ ", pero se ha encontrado " + v.tipo.getVal(), id.fila, id.col);
		}
		return v;
	}

	private void addDeclaracion(String iden, Vinculo v) throws VincException {
		if(!tablaSimbolos.containsKey(iden)) {
			tablaSimbolos.put(iden, v);
		} else {
			throw new VincException(iden, "Identificador ya existente");
		}
	}
}
