package gen;

import java.io.PrintWriter;
import java.io.FileNotFoundException;

import ast.*;
import ast.tipos.*;
import ast.exp.*;

public class GeneracionCodigo {

	private String file;
	private PrintWriter output;
	private int numInst = 0;

	public GeneracionCodigo(String file) {
		this.file = file;
		try {
			output = new PrintWriter(file);
		} catch(FileNotFoundException e) {
			System.err.println("File not found");
		}
	}

	public void generar(Prog p) {
		asignarMemoria(p);
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == null)
				continue;
			switch(i.getInst()) {
				case DEC:
					break;
				case ASIG:
					generarAsig((Asig) i);
					break;
				default:
			}
		}
		printInst("stp");

		output.close();
	}

	private void asignarMemoria(Prog p) {
		int tamAct = 0;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == EnumInst.DEC) {
				tamAct = generarDec((Dec) i, tamAct);
			}
		}
		printInst("ssp " + (tamAct+5));
	}

	private int generarDec(Dec d, int tamAct) {
		int size = d.getTipo().getSize();
		if (size == 1) {
			d.setDir(tamAct + 5);
		} else {
			throw new RuntimeException("Unsupported");
		}
		// TODO Determinar el nivel
		return tamAct + size;
	}

	private void generarAsig(Asig a) {
		generarAsignable(a.getDec());
		generarExp(a.getExp());
		printInst("sto");
	}

	private void generarAsignable(Dec d) {
		//printInst("ldc " + nivel);
		// TODO determinar nivel
		printInst("ldc " + d.getDir());
	}

	private void generarExp(Exp e) {
		if(e.getOp() == Operator.NONE) {
			if(e instanceof Variable) {
				printInst("ldc " + ((Variable) e).getDec().getDir());
				printInst("ind");
			}
			else if(e instanceof Const) {
				if(e instanceof ConstInt) {
					printInst("ldc " + ((ConstInt) e).value);
				}
				else if(e instanceof ConstBool) {
					String val = ((ConstBool) e).value ? "true" : "false";
					printInst("ldc " + val);
				}
				else if(e instanceof ConstDec) {
					// TODO fix this
					throw new RuntimeException("Decimales no soportados");
				}
			}
		}
		switch(e.getOp()) {
			case MAS: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("add");
				break;
			}
			case MENOS: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("sub");
				break;
			}
			case POR: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("mul");
				break;
			}
			case DIV: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("div");
				break;
			}
			case MOD: {
				// Dividir, multiplicar el cociente por el divisor, y restar al dividendo
				Exp[] ops = e.getOperands();
				//dividendo
				generarExp(ops[0]);
				//divisor
				generarExp(ops[1]);
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("div");
				printInst("mul");
				printInst("sub");				
				break;
			}
			case AND: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("and");
				break;
			}
			case OR: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("or");
				break;
			}
			case NOT: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				printInst("not");
				break;
			}
			case ES_IGUAL: {
				// Igualdad por referencia en tipos compuestos
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("equ");
				break;
			}
			case MENOR: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("les");
				break;
			}
			case MAYOR: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("grt");
				break;
			}
			case MENIG: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("leq");
				break;
			}
			case MAYIG: {
				Exp[] ops = e.getOperands();
				generarExp(ops[0]);
				generarExp(ops[1]);
				printInst("geq");
				break;
			}
		}
	}
	

	private void printInst(String inst) {
		output.format("{%s}  \t\t%s;\n", numInst, inst);
		++numInst;
	}
}
