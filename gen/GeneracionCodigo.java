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
		int numVar = 0, tamTotal = 0;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == EnumInst.DEC) {
				numVar++;
				tamTotal += ((Dec) i).getTipo().getSize();
			}
		}
	
		int inicioVarLoc = numVar+5;

		int contador = 0, tamAct = 0;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == EnumInst.DEC) {
				tamAct = generarDec((Dec) i, contador, tamAct, inicioVarLoc);
				++contador;
			}
		}
	}

	private int generarDec(Dec d, int contador, int tamAct, int inicioAlm) {
		int size = d.getTipo().getSize();
		if (size == 1) {
			d.setDir(contador + 5);
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
			}
		}
	}
	

	private void printInst(String inst) {
		output.format("{%s}	%s;\n", numInst, inst);
		++numInst;
	}
}
