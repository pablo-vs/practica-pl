package gen;

import java.io.PrintWriter;
import java.io.FileNotFoundException;

import ast.*;
import ast.tipos.*;
import ast.exp.*;

public class GeneracionCodigo {

	private String file;
	private PrintWriter output;
	private int numInst = 0, profundidad = -1;

	public GeneracionCodigo(String file) {
		this.file = file;
		try {
			output = new PrintWriter(file);
		} catch(FileNotFoundException e) {
			System.err.println("File not found");
		}
	}

	public void generarCodigo(Prog p) {
		generar(p, true);
		printInst("stp");
		output.close();
	}

	public void generar(Prog p, boolean base) {
		++profundidad;
		int tamMarco = 5+ asignarMemoria(p);
		if(!base) {
		}
		printInst("ssp " + tamMarco);
		printInst("sep " + (extremePointer(p)));
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
		--profundidad;
	}

	// Devuelve el tamaño ocupado por las variables locales
	private int asignarMemoria(Prog p) {
		int tamAct = 0;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == EnumInst.DEC) {
				tamAct = asignarDec((Dec) i, tamAct);
			}
		}
		return tamAct;
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
		d.setProf(profundidad + 1);
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
		switch(a.tipoAs) {
			case VAR: {
				res = a.getDec().getTipo();
				int dir = a.getDec().getDir();
				int difProf = profundidad + 1 - a.getDec().getProf();
				printInst("lda " + difProf  + " " + dir);
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
				Variable v = (Variable) e;
				int dir = v.getDec().getDir();
				int difProf = profundidad + 1 - v.getDec().getProf();
				printInst("lod " + difProf  + " " + dir);
				res = v.getDec().getTipo();
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
		printInst("cup 0 " + (numInst + 2));
		int count = countBlock(prog);
		printInst("ujp " + (count + numInst + 2));
		generar(prog, false);
		printInst("retp");
	}
	
	private int extremePointer(Prog p) {
		int max = 0;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			Exp e = null;
			int size = 0;
			switch(i.getInst()) {
				case DEC:
					if(((Dec)i).getAsig() != null) {
						e = ((Dec)i).getAsig().getExp();
						size = sizeExp(e) + sizeAsignable(((Dec)i).getAsig().getAsignable());
					}
					break;
				case ASIG:
					e = ((Asig)i).getExp();
					size = sizeExp(e) + sizeAsignable(((Asig)i).getAsignable());
					break;
				case IF:
					e = ((If)i).getCond();
					size = sizeExp(e);
					break;
				case REPEAT:
					e = ((Repeat)i).getLimit();
					size = sizeExp(e);
					e = ((Repeat)i).getCond();
					if(e != null)
						size = Math.max(size, sizeExp(e));
					break;
			}
			max = Math.max(max,size);
		}
		return max;
	}
	
	private int sizeExp (Exp e){
		int size = 0;
		if(e.getOp() == Operator.PUNTO || e.getOp() == Operator.ACCESO ||
				(e instanceof Variable)) {
			sizeExpAsignable(e);
		} else if(e.getOp() == Operator.NONE) {
			if(e instanceof Const) {
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

	private int sizeAsignable(Asignable a) {
		int size = 0;
		switch(a.tipoAs) {
			case VAR:
				size = 1;
				break;
			case CAMPO:
				size = 1 + sizeAsignable(a.getStruct());
				break;
		}
		return size;
	}

	private int sizeExpAsignable(Exp e) {
		int size = 1;
		switch(e.getOp()) {
			case PUNTO: {
				size += 1 + sizeExpAsignable(e.getOperands()[0]);
				break;
			}
		}
		return size;
	}
	
	private void generarIf(If i) {
		generarExp(i.getCond());
		int count = countBlock(i.getBlock().getProg());
		if(i.getBlockElse() == null) {
			printInst("fjp " + (numInst + count + 5));
			generarBlock(i.getBlock());
		} else {
			printInst("fjp " + (numInst + count + 6));
			generarBlock(i.getBlock());
			count = countBlock(i.getBlockElse().getProg());
			printInst("ujp " + (numInst + count + 5));
			generarBlock(i.getBlockElse());
		}
	}
	
	private void generarRepeat(Repeat r) {
		int ini = numInst;
		Exp limit = r.getLimit();
		Exp e = new Exp(Operator.MENOR, new ConstInt("0"), limit);
		if (r.getCond() != null) e = new Exp(Operator.OR, e, r.getCond());
		generarExp(e);
		int count = countBlock(r.getBlock().getProg());
		printInst("fjp " + numInst + count);
		generarBlock(r.getBlock());
		generarExp(new Exp(Operator.MENOS, limit, new ConstInt("1")));
		printInst("ujp " + ini);
	}
	
	public int countBlock(Prog p) {
		int count = 2;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == null)
				continue;
			switch(i.getInst()) {
				case DEC:
					Asig a = ((Dec) i).getAsig();
					if (a != null){
						count += 1 + countAsignable(a.getAsignable()) + countExp(a.getExp());
					}
					break;
				case ASIG:
					count += 1 + countAsignable(((Asig) i).getAsignable()) + countExp(((Asig) i).getExp());
					break;
				case BLOCK:
					count += countBlock(((Block) i).getProg()) + 4;
					break;
				case IF:
					count += countExp(((If) i).getCond()) + countBlock(((If) i).getBlock().getProg()) + 5;
					if (((If) i).getBlockElse() != null) count += countBlock(((If) i).getBlockElse().getProg()) + 5;
					break;
				case REPEAT:
					count += 5 + 2*countExp(((Repeat) i).getLimit()) + countBlock(((Repeat) i).getBlock().getProg());
					if (((Repeat) i).getCond() != null) count += 1 + countExp(((Repeat) i).getCond());
					break;
				default:
			}
		}
		return count;
	}
	
	private int countAsignable(Asignable a) {
		int count = 1;
		switch(a.tipoAs) {
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
			count = countExpAsignable(e);
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
		int count = 1;
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
