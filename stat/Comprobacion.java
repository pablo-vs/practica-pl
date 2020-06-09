package stat;

import errors.GestionErroresTiny;
import ast.*;
import ast.exp.*;
import ast.tipos.*;

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
					default:
				}
			} catch (CompException e) {
				err.errorComprob(e);
			}
		}	
	}

	public void comprobarDec(Dec d) throws CompException {}

	public void comprobarAsig(Asig a) throws CompException {
		comprobarExp(a.getExp());
		if (!a.getDec().getTipo().compatibleCon(a.getExp().getTipo())) {
			throw new CompException("Se esperaba " + a.getDec().getTipo() + " pero se ha encontrado " + a.getExp().getTipo());
		}
	}

	public void comprobarExp(Exp e) throws CompException {
		if(e.getOp() == Operator.NONE) {
			if(e instanceof Variable) {
				Variable var = (Variable) e;
				e.setTipo(var.getDec().getTipo());
			}
			else if(e instanceof Const) {
				// Arrays, Dicts, Tuplas...
			}
		} else {
			Tipo tipoOp = comprobarOp(e.getOp(), e.getOperands());
			e.setTipo(tipoOp);
		}
	}

	// Tipos de los operadores
	public Tipo comprobarOp(Operator op, Exp [] operands) throws CompException {
		for (Exp e: operands) {
			comprobarExp(e);
		}
		Tipo result, tipoOp;
		switch(op) {
			case AND:
				result = new TipoBool();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("Se esperaba " + result + " pero se ha encontrado " + e.getTipo());
				break;
			case OR:
				result = new TipoBool();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("Se esperaba " + result + " pero se ha encontrado " + e.getTipo());
				break;
			case NOT:
				result = new TipoBool();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("Se esperaba " + result + " pero se ha encontrado " + e.getTipo());
				break;
			case ES_IGUAL:
				result = new TipoBool();
				tipoOp = operands[0].getTipo();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException("==: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				break;
			case MENOR:
				result = new TipoBool();
				tipoOp = new TipoDec();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException("<: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				break;
			case MAYOR:
				result = new TipoBool();
				tipoOp = new TipoDec();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException(">: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				break;
			case MENIG:
				result = new TipoBool();
				tipoOp = new TipoDec();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException("<=: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				break;
			case MAYIG:
				result = new TipoBool();
				tipoOp = new TipoDec();
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(tipoOp))
						throw new CompException(">=: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				break;
			case MAS: {
				tipoOp = operands[0].getTipo();
				if (!tipoOp.compatibleCon(new TipoDec()))
						throw new CompException("+: Tipo incompatible " + tipoOp);
				boolean isDec = false;
				result = new TipoInt();
				for (Exp e: operands) {
					if(e.getTipo().igual(new TipoDec()))
						isDec = true;
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("+: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				}
				if (isDec)
					result = new TipoDec();
				break;
			}
			case MENOS: {
				tipoOp = operands[0].getTipo();
				if (!tipoOp.compatibleCon(new TipoDec()))
						throw new CompException("-: Tipo incompatible " + tipoOp);
				boolean isDec = false;
				result = new TipoInt();
				for (Exp e: operands) {
					if(e.getTipo().igual(new TipoDec()))
						isDec = true;
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("-: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				}
				if (isDec)
					result = new TipoDec();
				break;
			}
			case POR: {
				tipoOp = operands[0].getTipo();
				if (!tipoOp.compatibleCon(new TipoDec()))
						throw new CompException("*: Tipo incompatible " + tipoOp);
				boolean isDec = false;
				result = new TipoInt();
				for (Exp e: operands) {
					if(e.getTipo().igual(new TipoDec()))
						isDec = true;
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("*: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				}
				if (isDec)
					result = new TipoDec();
				break;
			}
			case DIV: {
				tipoOp = operands[0].getTipo();
				if (!tipoOp.compatibleCon(new TipoDec()))
						throw new CompException("/: Tipo incompatible " + tipoOp);
				boolean isDec = false;
				result = new TipoInt();
				for (Exp e: operands) {
					if(e.getTipo().igual(new TipoDec()))
						isDec = true;
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("/: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				}
				if (isDec)
					result = new TipoDec();
				break;
			}
			case CONCAT:
				tipoOp = operands[0].getTipo();
				if(!(tipoOp.getTipo() == EnumTipo.ARRAY))
						throw new CompException("++: Tipo incompatible " + tipoOp);
				result = tipoOp;
				for (Exp e: operands)
					if(!e.getTipo().compatibleCon(result))
						throw new CompException("++: Tipos incompatibles " + tipoOp + ", " + e.getTipo());
				break;
			case MOD:
				result = new TipoInt();
				for (Exp e: operands)
					if(!e.getTipo().igual(result))
						throw new CompException("%: Tipos incompatibles " + result + ", " + e.getTipo());
				break;
			default:
				throw new CompException("Esto no debería ocurrir, operador obtenido " + op.name());
		}
		return result;
	}
}
