package stat;

import errors.GestionErroresTiny;
import ast.*;
import ast.exp.*;
import ast.tipos.*;

public class Comprobacion {


	private GestionErroresTiny err;


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

	public void comprobarExp(Exp e) {
		if(e.getOp() == Operator.NONE) {
			if(e instanceof Variable) {
				Variable var = (Variable) e;
				e.setTipo(var.getDec().getTipo());
			}
			else if(e instanceof Const) {
				// Arrays, Dicts, Tuplas...
			}
		}
		
	}

	// Tipos de los operadores
}
