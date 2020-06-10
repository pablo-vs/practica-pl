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
					generarDec((Dec) i);
					break;
				case ASIG:
					generarAsig((Asig) i);
					break;
				case BLOCK:
					generarBlock((Block) i);
					break;
				case IF:
					generarIf((If) i);
					break;
				case REPEAT:
					generarRepeat((Repeat) i);
					break;
				case TIPO_DEF:
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
				tamAct = asignarDec((Dec) i, tamAct);
			}
		}
		printInst("ssp " + (tamAct+5));
	}

	private int asignarDec(Dec d, int tamAct) {
		int size = d.getTipo().getSize();
		if(size > 1) {
			Tipo t = d.getTipo();
			asignarTipo(t);
		}
		d.setDir(tamAct + 5);
		// TODO Determinar el nivel
		return tamAct + size;
	}

	private void asignarTipo(Tipo t) {
		switch(t.getTipo()) {
			case STRUCT: {
				TipoStruct ts = (TipoStruct) t;
				int totSize = 0;
				for(CampoStruct cs: ts.getMapaCampos().values()) {
					cs.setOffset(totSize);
					totSize += cs.getTipo().getSize();
					asignarTipo(cs.getTipo());
				}
				break;
			}
			default:
		}
	}

	private void generarDec(Dec d) {
		if (d.getAsig() != null)
			generarAsig(d.getAsig());
	}

	private void generarAsig(Asig a) {
		generarAsignable(a.getAsignable());
		generarExp(a.getExp());
		printInst("sto");
	}

	private Tipo generarAsignable(Asignable a) {
		Tipo res = null;
		switch(a.tipo) {
			case VAR: {
				res = a.getDec().getTipo();
				int dir = a.getDec().getDir();
				printInst("ldc " + dir);
				break;
			}
			case CAMPO: {
				TipoStruct t = (TipoStruct) generarAsignable(a.getStruct());
				String idcampo = a.getChild().getBottom().getIden().print();
				int offset = t.getMapaCampos().get(idcampo).getOffset();
				printInst("ldc " + offset);
				printInst("add");
				Tipo tAsig = t.getMapaCampos().get(idcampo).getTipo();
				res = tAsig;
				break;
			}
		}
		return res;
	}

	private void generarExp(Exp e) {
		if(e.getOp() == Operator.PUNTO || e.getOp() == Operator.ACCESO ||
				(e instanceof Variable)) {
			generarExpAsignable(e);
			printInst("ind");
		} else if(e.getOp() == Operator.NONE) {
			if(e instanceof Const) {
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
			
		} else {
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
	}

	private Tipo generarExpAsignable(Exp e) {
		Tipo res = null;
		switch(e.getOp()) {
			case NONE: {
				printInst("ldc " + ((Variable) e).getDec().getDir());
				res = ((Variable)e).getDec().getTipo();
				break;
			}
			case PUNTO: {
				TipoStruct t = (TipoStruct) generarExpAsignable(e.getOperands()[0]);
				String idCampo = ((Variable)e.getOperands()[1].getArray()).getIden().print();
				int offset = t.getMapaCampos().get(idCampo).getOffset();
				printInst("ldc " + offset);
				printInst("add");
				res = t.getMapaCampos().get(idCampo).getTipo();
				break;
			}
		}
		return res;
	}
	
	private void generarBlock(Block b) {
		Prog prog = b.getProg();
		printInst("mst 0");
		asignarMemoria(prog);
		printInst("sep " + (extremePointer(prog)));
		generar(prog);
		printInst("retp");
	}
	
	private int extremePointer(Prog p) {
		int max = 0;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			Exp e = null;
			switch(i.getInst()) {
				case ASIG:
					e = ((Asig)i).getExp();
			}
			int size = 0;
			if(e != null)
				size = sizeExp(e);
			if (max < size) max = size;
		}
		return max;
	}
	
	private int sizeExp (Exp e){
		int size = 0;
		if(e.getOp() == Operator.NONE) {
			if(e instanceof Variable) {
				size += 1;
			}
			else if(e instanceof Const) {
				if(e instanceof ConstInt) {
					size += 1;
				}
				else if(e instanceof ConstBool) {
					size += 1;
				}
				else if(e instanceof ConstDec) {
					// TODO
					throw new RuntimeException("Decimales no soportados");
				}
			}
		}
		else {
			Exp[] ops = e.getOperands();
			switch(e.getOp()) {
				case MOD: {
					size += 2*sizeExp(ops[0]) + 2*sizeExp(ops[1]);
					break;
				}
				case NOT: {
					size += sizeExp(ops[0]);
					break;
				}
				default: {
					size += sizeExp(ops[0]) + sizeExp(ops[1]);
				}
			}
		}
		return size;
	}
	
	private void generarIf(If i) {
		generarExp(i.getCond());
		int count = countBlock(i.getBlock());
		printInst("fjp " + numInst + count);
		generarBlock(i.getBlock()));
		if (i.getBlockElse() != null) {
			count = countBlock(i.getBlockElse());
			printInst("ujp " + numInst + count);
			generarBlock(i.getBlockElse());
		}
	}
	
	private void generarRepeat(Repeat r) {
		int ini = numInst;
		Exp limit = r.getLimit();
		Exp e = new Exp(Operator.MENOR, new ConstInt("0"), limit);
		if (r.getCond() != null) e = new Exp(Operator.OR, e, r.getCond());
		generarExp(e);
		int count = countBlock(i.getBlock());
		printInst("fjp " + numInst + count);
		generarBlock(i.getBlock());
		generarExp(new Exp(Operator.MENOS, limit, new ConstInt("1")););
		printInst("ujp " + ini);
	}
	
	public int countBlock(Prog p) {
		int count = 1;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == null)
				continue;
			switch(i.getInst()) {
				case DEC:
					Asig a = ((Dec) i)).getAsig();
					if (a != null){
						count += 1 + countAsignable(a.getAsignable()) + countExp(a.getExp());
					}
					break;
				case ASIG:
					count += 1 + countAsignable(((Asig) i).getAsignable()) + countExp(((Asig) i).getExp());
					break;
				case BLOCK:
					count += countBlock((Block) i);
					break;
				case IF:
					count += countExp(((If) i).getCond()) + countBlock(((If) i).getBlock());
					if (((If) i).getBlockElse() != null) count += countBlock(((If) i).getBlockElse());
					break;
				case REPEAT:
					count += 5 + 2*countExp(((Repeat) i).getLimit()) + countBlock(((Repeat) i).getBlock());
					if (((Repeat) i).getCond() != null) count += 1 + countExp(((Repeat) i).getCond())
					break;
				default:
			}
		}
		return count;
	}
	
	private int countAsignable(Asig a) {
		int count = 1;
		switch(a.tipo) {
			case CAMPO: {
				count += 1 + countAsignable(a.getStruct());
				break;
			}
		}
		return count;
	}
	
	private int countExp (Exp e){
		int count = 1;
		if(e.getOp() == Operator.PUNTO || e.getOp() == Operator.ACCESO ||
				(e instanceof Variable)) {
			count += countExpAsignable(e);
		} else if(e.getOp() != Operator.NONE) {
			Exp[] ops = e.getOperands();
			switch(e.getOp()) {
				case MOD: {
					count += 2 + 2*countExp(ops[0]) + 2*countExp(ops[1]);		
					break;
				}
				case NOT: {
					count += countExp(ops[0]);
					break;
				}
				default: {
					count += countExp(ops[0]) + countExp(ops[1]);
				}
			}
		}
		return count;
	}
	
	private int countExpAsignable(Exp e){
		count = 1;
		switch(e.getOp()) {
			case PUNTO: {
				count += 1 + countExpAsignable(e.getOperands()[0]);
				break;
			}
		}
		return count;
	}
	
	private void printInst(String inst) {
		output.format("{%s}  \t\t%s;\n", numInst, inst);
		++numInst;
	}
}
