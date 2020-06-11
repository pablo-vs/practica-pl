package stat;

import errors.GestionErroresTiny;
import ast.*;
import ast.exp.*;
import ast.tipos.*;

import java.util.HashMap;

public class Comprobacion {


	private GestionErroresTiny err;

	public Comprobacion(GestionErroresTiny e) {
		err = e;
	}

	public void comprobar(Prog p) {
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			try {
				if(i.getInst() == null)
					continue;
				switch(i.getInst()) {
					case DEC:
						comprobarDec((Dec) i);
						break;
					case ASIG:
						comprobarAsig((Asig) i);
						break;
					case BLOCK:
						comprobarBlock((Block) i);
						break;
					case IF:
						comprobarIf((If) i);
						break;
					case REPEAT:
						comprobarRepeat((Repeat) i);
						break;
					default:
				}
			} catch (CompException e) {
				err.errorComprob(e);
			}
		}	
	}

	private void comprobarDec(Dec d) throws CompException {
		if (d.getAsig() != null) {
			comprobarAsig(d.getAsig());
		}
	}


	private void comprobarAsig(Asig a) throws CompException {
		comprobarExp(a.getExp());
		comprobarAsignable(a.getAsignable());
		if (!a.getAsignable().getTipo().compatibleCon(a.getExp().getTipo())) {
			throw new CompException("Se esperaba " + a.getAsignable().getTipo() + " pero se ha encontrado " + a.getExp().getTipo(), a.fila, a.col);
		}
	}



	private void comprobarAsignable(Asignable a) throws CompException {
		Tipo res = null;
		switch(a.tipoAs) {
			case VAR: {
				res = a.getDec().getTipo();
				break;
			}
			case CAMPO: {
				comprobarAsignable(a.getChild());
				Tipo t = a.getChild().getTipo();
				if(t.getTipo() != EnumTipo.STRUCT)
					throw new CompException(a.getChild().getIden().print()
							+ " no es un struct", a.fila, a.col);
				
				TipoStruct ts = (TipoStruct) t;
				Iden id = a.getIden();

				if(!ts.getMapaCampos().containsKey(id.print()))
					throw new CompException("El struct "
							+ a.getChild().getIden().print() + " no tiene un campo "
							+ id.print(), a.fila, a.col);

				res = ts.getMapaCampos().get(id.print()).getTipo();
				break;
			}
			case ACCESOR: {
				comprobarExp(a.getExp());
				comprobarAsignable(a.getChild());
				Tipo t = a.getChild().getTipo();
				if(t.getTipo() == EnumTipo.ARRAY) {
					Tipo te = a.getExp().getTipo();
					TipoArray ta = (TipoArray) t;
					if(!te.igual(new TipoInt())) {
						throw new CompException("El índice " + a.getExp().print()
								+ " no es de tipo Int", a.getExp().fila, a.getExp().col);
					}
					res = ta.getTipoElem();
				} else if(t.getTipo() == EnumTipo.TUPLA) {
					// TODO
					throw new RuntimeException("Tuplas no soportadas");
				} else if(t.getTipo() == EnumTipo.DICT) {
					Tipo te = a.getExp().getTipo();
					TipoDict td = (TipoDict) t;
					if(!te.igual(td.getTipoClave())) {
						throw new CompException("El índice " + a.getExp().print()
								+ " tiene tipo " + te + ", se esperaba " +
								td.getTipoClave(), a.getExp().fila, a.getExp().col);
					}
					res = td.getTipoValor();
				} else {
					throw new CompException(a.getChild().getIden().print()
							+ " no es un struct", a.fila, a.col);
				}
			}
			case PUNT: {
				comprobarAsignable(a.getChild());
				Tipo t = a.getChild().getTipo();
				if(t.getTipo() != EnumTipo.PUNT)
					throw new CompException(a.getChild().getIden().print()
							+ " no es un puntero", a.fila, a.col);
				TipoPunt tp = (TipoPunt) t;
				res = tp.getTipoRef();
			}
			default:
		}
		a.setTipo(res);
	}

	private void comprobarExp(Exp e) throws CompException {
		Tipo res = null;
		if(e instanceof ExpAsig) {
			comprobarAsignable(((ExpAsig)e).getAsignable());
			res = ((ExpAsig)e).getAsignable().getTipo();
		}
		else if(e.getOp() == Operator.NONE) {
			if(e instanceof Const) {
				res = e.getTipo();
			}
		} else {
			res = comprobarOp(e.getOp(), e.getOperands());
		}
		e.setTipo(res);
	}

	// Tipos de los operadores
	private Tipo comprobarOp(Operator op, Exp [] operands) throws CompException {
		for (Exp e: operands) {
			comprobarExp(e);
		}
		Tipo result, tipoOp;
		switch(op) {
			case AND:
				result = new TipoBool();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("and: Se esperaba " + result
								+ " pero se ha encontrado " + e.getTipo(), e.fila, e.col);
				break;
			case OR:
				result = new TipoBool();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("or: Se esperaba " + result
								+ " pero se ha encontrado " + e.getTipo(), e.fila, e.col);
				break;
			case NOT:
				result = new TipoBool();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("not: Se esperaba " + result
								+ " pero se ha encontrado " + e.getTipo(), e.fila, e.col);
				break;
			case ES_IGUAL:
				result = new TipoBool();
				tipoOp = operands[0].getTipo();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException("==: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				break;
			case MENOR:
				result = new TipoBool();
				tipoOp = new TipoDec();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException("<: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				break;
			case MAYOR:
				result = new TipoBool();
				tipoOp = new TipoDec();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException(">: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				break;
			case MENIG:
				result = new TipoBool();
				tipoOp = new TipoDec();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException("<=: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				break;
			case MAYIG:
				result = new TipoBool();
				tipoOp = new TipoDec();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException(">=: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				break;
			case MAS: {
				tipoOp = operands[0].getTipo();
				if (!tipoOp.compatibleCon(new TipoDec()))
						throw new CompException("+: Tipo incompatible " + tipoOp
								, operands[0].fila, operands[0].col);
				boolean isDec = false;
				result = new TipoInt();
				for (Exp e: operands) {
					if(e.getTipo().igual(new TipoDec()))
						isDec = true;
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("+: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				}
				if (isDec)
					result = new TipoDec();
				break;
			}
			case MENOS: {
				tipoOp = operands[0].getTipo();
				if (!tipoOp.compatibleCon(new TipoDec()))
						throw new CompException("+: Tipo incompatible " + tipoOp
								, operands[0].fila, operands[0].col);
				boolean isDec = false;
				result = new TipoInt();
				for (Exp e: operands) {
					if(e.getTipo().igual(new TipoDec()))
						isDec = true;
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("-: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				}
				if (isDec)
					result = new TipoDec();
				break;
			}
			case POR: {
				tipoOp = operands[0].getTipo();
				if (!tipoOp.compatibleCon(new TipoDec()))
						throw new CompException("+: Tipo incompatible " + tipoOp
								, operands[0].fila, operands[0].col);
				boolean isDec = false;
				result = new TipoInt();
				for (Exp e: operands) {
					if(e.getTipo().igual(new TipoDec()))
						isDec = true;
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("*: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				}
				if (isDec)
					result = new TipoDec();
				break;
			}
			case DIV: {
				tipoOp = operands[0].getTipo();
				if (!tipoOp.compatibleCon(new TipoDec()))
						throw new CompException("+: Tipo incompatible " + tipoOp
								, operands[0].fila, operands[0].col);
				boolean isDec = false;
				result = new TipoInt();
				for (Exp e: operands) {
					if(e.getTipo().igual(new TipoDec()))
						isDec = true;
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("/: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				}
				if (isDec)
					result = new TipoDec();
				break;
			}
			case CONCAT:
				tipoOp = operands[0].getTipo();
				if(!(tipoOp.getTipo() == EnumTipo.ARRAY))
						throw new CompException("+: Tipo incompatible " + tipoOp
								, operands[0].fila, operands[0].col);
				result = tipoOp;
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("++: Tipos incompatibles " + tipoOp
								+ ", " + e.getTipo(), e.fila, e.col);
				break;
			case MOD:
				result = new TipoInt();
				for (Exp e: operands)
					if(!e.getTipo().igual(result))
						throw new CompException("%: Tipos incompatibles " + result
								+ ", " + e.getTipo(), e.fila, e.col);
				break;
			case REF:
				if(!(operands[0] instanceof ExpAsig))
					throw new CompException("Referencia a algo que no es un designador",
							operands[0].fila, operands[0].col);
				tipoOp = operands[0].getTipo();
				result = new TipoPunt(tipoOp);
				break;
			default:
				throw new CompException("Esto no debería ocurrir, operador obtenido " + op.name());
		}
		return result;
	}

	private void comprobarBlock(Block b) throws CompException {
		comprobar(b.getProg());
	}

	private void comprobarIf(If i) throws CompException {
		comprobarExp(i.getCond());
		if(i.getCond().getTipo().getTipo() != EnumTipo.TBOOL)
			throw new CompException("If requiere una condición booleana, pero se obtuvo " + i.getCond().getTipo(), i.getCond().fila, i.getCond().col);
		comprobarBlock(i.getBlock());
		if(i.getBlockElse() != null)
			comprobarBlock(i.getBlockElse());
	}

	private void comprobarRepeat(Repeat r) throws CompException {
		comprobarExp(r.getLimit());
		if(r.getLimit().getTipo().getTipo() != EnumTipo.TINT)
			throw new CompException("Repeat requiere un límite booleano, pero se obtuvo " + r.getLimit().getTipo(), r.getLimit().fila, r.getLimit().col);
		if(r.getCond() != null) {
			comprobarExp(r.getCond());
			if(r.getCond().getTipo().getTipo() != EnumTipo.TBOOL)
				throw new CompException("Repeat requiere una condición booleana, pero se obtuvo " + r.getCond().getTipo(), r.getCond().fila, r.getCond().col);
		}
		comprobarBlock(r.getBlock());
	}
}
