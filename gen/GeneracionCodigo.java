package gen;

import java.io.PrintWriter;
import java.io.FileNotFoundException;

import ast.*;
import ast.tipos.*;
import ast.exp.*;

import gen.fun.FuncionesPredefinidas;
import gen.fun.FunPred;

public class GeneracionCodigo {

	public static final int TAM_MARCO_BASE = 6;

	private String file;
	private PrintWriter output;
	private int numInst = 0, profundidad = -1;

	private FuncionesPredefinidas funPred;

	public GeneracionCodigo(String file, FuncionesPredefinidas fp) {
		this.file = file;
		funPred = fp;
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

	public void generar(Prog p, boolean base, Argumento ... args) {
		++profundidad;

		int tamMarco = TAM_MARCO_BASE + asignarMemoria(p, args);
		printInst("ssp " + tamMarco);
		printInst("sep " + (extremePointer(p)));
		if(base) {
			generarFunPred();
		}

		int count = 0;
		// Genera las funciones anidadas
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == null)
				continue;
			if(i.getInst() == EnumInst.FUN_DEF)
				count += countBlock(((DefFun)i).getBlock().getProg()) + 1;
		}
		if(count > 0) {
			printInst("ujp " + (numInst + count + 1));
			for(NodoAst n: p.getChildren()) {
				Inst i = (Inst) n;
				if(i.getInst() == null)
					continue;
				if(i.getInst() == EnumInst.FUN_DEF)
					generarDefFun((DefFun)i);
			}
		}

		// Guarda el tamaño del marco estático
		printInst("lda 0 5");
		printInst("ldc " + tamMarco);
		printInst("sto");

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
				case CASE:
					generarCase((Case) i);
					break;
				case FUN_DEF:
					break;
				case FUN_CALL:
					generarFunCall((FunCall) i);
					break;
				case RETURN:
					generarReturn((Return) i);
					break;
				case TIPO_DEF:
					break;
				default:
			}
		}
		--profundidad;
	}

	// Devuelve el tamaño ocupado por las variables locales
	private int asignarMemoria(Prog p, Argumento ... args) {
		int tamAct = 0;
		for(Argumento a: args) 
			tamAct = asignarArg(a, tamAct);

		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == EnumInst.DEC) {
				tamAct = asignarDec((Dec) i, tamAct);
			}
		}
		return tamAct;
	}

	private int asignarArg(Argumento a, int tamAct) {
		int size = a.getTipo().getSize();
		if(size > 1) {
			Tipo t = a.getTipo();
			asignarTipo(t);
		}
		a.setDir(tamAct + TAM_MARCO_BASE);
		return tamAct + size;
	}

	private int asignarDec(Dec d, int tamAct) {
		int size = d.getTipo().getSize();
		if(size > 1) {
			Tipo t = d.getTipo();
			asignarTipo(t);
		}
		d.setDir(tamAct + TAM_MARCO_BASE);
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
		if(a.getAsignable().getTipo().getSize() > 1) {
			generarAsigMulti(a);
		} else {
			generarAsignable(a.getAsignable());
			generarExp(a.getExp());
			printInst("sto");
		}
	}

	private void generarAsigMulti(Asig a) {
		// Llamar a copy()
		printInst("mst " + profundidad);
		printInst("ldc 0");
		generarAsignable(a.getAsignable());
		generarExp(a.getExp());
		printInst("ldc " + a.getExp().getTipo().getSize());
		printInst("cup 4 " + funPred.definidas.get("copy").getDir());
	}

	private void generarAsignable(Asignable a) {
		switch(a.tipoAs) {
			case VAR: {
				int dir, difProf;
				if(a.getDec() != null) {
					dir = a.getDec().getDir();
					difProf = profundidad + 1 - a.getDec().getProf();
				} else {
					dir = a.getArg().getDir();
					difProf = 0;
				}
				printInst("lda " + difProf  + " " + dir);
				break;
			}
			case CAMPO: {
				generarAsignable(a.getChild());
				TipoStruct t = (TipoStruct) a.getChild().getTipo();
				String idcampo = a.getIden().print();
				int offset = t.getMapaCampos().get(idcampo).getOffset();
				printInst("ldc " + offset);
				printInst("add");
				break;
			}
			case ACCESOR: {
				generarAsignable(a.getChild());
				printInst("ind");
				generarExp(a.getExp());
				printInst("ixa " + a.getTipo().getSize());
			}
		}
	}

	private void generarExp(Exp e) {
		if(e instanceof ExpAsig) {
			generarExpAsignable((ExpAsig)e);
		} else if(e instanceof ExpFun) {
			generarFunCall(((ExpFun)e).getCall());
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
				case REF: {
					ExpAsig as = (ExpAsig) e.getOperands()[0];
					generarAsignable(as.getAsignable());
					break;
				}
			}
		}
	}

	private void generarExpAsignable(ExpAsig e) {
		generarAsignable(e.getAsignable());
		if(e.getAsignable().getTipo().getSize() == 1)
			printInst("ind");
	}
	
	private void generarBlock(Block b) {
		Prog prog = b.getProg();
		printInst("mst 0");
		printInst("ldc 0");
		printInst("cup 1 " + (numInst + 2));
		int count = countBlock(prog);
		printInst("ujp " + (count + numInst + 2));
		generar(prog, true);
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
				case CASE: {
					CaseMatch[] branches = ((Case)i).getBranches();
					e = ((Case)i).getCond();
					int sizeIni = sizeExp(e);
					for(int j = 0; j < branches.length; ++j){
						size = Math.max(size, sizeExp(branches[j].getValue()));
					}
					size += sizeIni;
					break;
				}
				case BLOCK:
					size = extremePointer(((Block)i).getProg());
					break;
				case FUN_DEF:
					size = extremePointer(((DefFun)i).getBlock().getProg());
					break;
				case FUN_CALL: {
					Exp[] args = ((FunCall)i).getArgs();
					for(int j = 0; j < args.length; ++j){
						size = Math.max(size, sizeExp(args[j]));
					}
					break;
				}
			}
			max = Math.max(max,size);
		}
		return max;
	}
	
	private int sizeExp (Exp e){
		int size = 0;
		if(e instanceof ExpAsig) {
			sizeExpAsignable((ExpAsig)e);
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
				case REF:
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
				size = 1 + sizeAsignable(a.getChild());
				break;
		}
		return size;
	}

	private int sizeExpAsignable(ExpAsig e) {
		return sizeAsignable(e.getAsignable())+1;
	}
	
	private void generarIf(If i) {
		generarExp(i.getCond());
		int count = countBlock(i.getBlock().getProg());
		if(i.getBlockElse() == null) {
			printInst("fjp " + (numInst + count + 6));
			generarBlock(i.getBlock());
		} else {
			printInst("fjp " + (numInst + count + 7));
			generarBlock(i.getBlock());
			count = countBlock(i.getBlockElse().getProg());
			printInst("ujp " + (numInst + count + 6));
			generarBlock(i.getBlockElse());
		}
	}
	
	private void generarRepeat(Repeat r) {
		int count = countBlock(r.getBlock().getProg());

		// Generamos el límite, que queda almacenado en la pila
		generarExp(r.getLimit());

		int ini = numInst; // El bucle volverá a esta instrucción

		// Duplicamos el límite para hacer la comprobación
		printInst("dpl");
		// Comprobamos si el límite es >0
		printInst("ldc 0");
		printInst("grt");

		if (r.getCond() != null) {
			generarExp(r.getCond());
			// Comprobamos que se cumple la condición
			printInst("and");
		}
		// Si lo anterior no se cumple, saltamos
		printInst("fjp " + (numInst + count + 6 + 2));

		generarBlock(r.getBlock());

		printInst("dec 1"); //Decrementamos el valor almacenado del límite
		printInst("ujp " + ini);
	}
		
	private void generarCase(Case c) {
		int countBloque[], count = 0, countExpIni = countExp(c.getCond());
		CaseMatch[] branches = c.getBranches();
		for(int i = 0; i < branches.length; ++i){
			Exp igual = new Exp(Operator.ES_IGUAL, c.getCond(), branches[i].getValue());
			generarExp(igual);
			printInst("fjp " + (numInst + count + branches.length - i + 1));
			count += countBlock(branches[i].getBlock().getProg());
		}
		int fin = numInst + count;
		for(int i = 0; i < branches.length-1; ++i){
			generarBlock(branches[i].getBlock());
			printInst("ujp " + fin);
		}
		generarBlock(branches[branches.length-1].getBlock());
	}
	
	public int countBlock(Prog p) {
		int count = 5;
		for(NodoAst n: p.getChildren()) {
			Inst i = (Inst) n;
			if(i.getInst() == null)
				continue;
			switch(i.getInst()) {
				case DEC:
					Asig a = ((Dec) i).getAsig();
					if (a != null){
						count += 1 + countAsignable(a.getAsignable())
								+ countExp(a.getExp());
					}
					break;
				case ASIG:
					if(((Asig)i).getExp().getTipo().getSize() == 1) 
						count += 1 + countAsignable(((Asig) i).getAsignable())
								+ countExp(((Asig) i).getExp());
					else
						count += 4 + countAsignable(((Asig) i).getAsignable())
								+ countExp(((Asig) i).getExp());

					break;
				case BLOCK:
					count += countBlock(((Block) i).getProg()) + 5;
					break;
				case IF:
					count += countExp(((If) i).getCond())
							+ countBlock(((If) i).getBlock().getProg()) + 6;
					if (((If) i).getBlockElse() != null)
						count += countBlock(((If) i).getBlockElse().getProg()) + 6;
					break;
				case REPEAT:
					count += 6 + countExp(((Repeat) i).getLimit()) + 5
							+ countBlock(((Repeat) i).getBlock().getProg());
					if (((Repeat) i).getCond() != null)
						count += 1 + countExp(((Repeat) i).getCond());
					break;
				case CASE:
					CaseMatch[] branches = ((Case) i).getBranches();
					int condIni = countExp(((Case) i).getCond());
					for(int j = 0; j < branches.length; ++j){
						count += 3 + condIni + countExp(branches[j].getValue()) + countBlock(branches[j].getBlock().getProg());
					}
					count -= 3;
					break;
				case FUN_DEF:
					count += countBlock(((DefFun) i).getBlock().getProg()) + 1;
					break;
				case FUN_CALL:
					Exp[] args = ((FunCall) i).getArgs();
					for(int j = 0; j < args.length; ++j)
						count += countExp(args[j]);
					break;
				case RETURN:
					count += 2 + countExp(((Return)i).getVal());
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
				count += 1 + countAsignable(a.getChild());
				break;
			}
			case ACCESOR:{
				count += 1 + countAsignable(a.getChild()) + countExp(a.getExp());
				break;
			}

		}
		return count;
	}
	
	private int countExp (Exp e){
		int count = 1;
		if(e instanceof ExpAsig) {
			count = countExpAsignable((ExpAsig)e);
		} else if(e.getOp() != Operator.NONE) {
			Exp[] ops = e.getOperands();
			switch(e.getOp()) {
				case MOD: {
					count += 2 + 2*countExp(ops[0]) + 2*countExp(ops[1]);		
					break;
				}
				case REF:
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
	
	private int countExpAsignable(ExpAsig e){
		int count = countAsignable(e.getAsignable());
		if(e.getTipo().getSize() == 1) ++count;
		return count;
	}
	
	private void generarDefFun(DefFun d) {
		d.setProf(profundidad + 1);
		d.setDir(numInst);
		Prog prog = d.getBlock().getProg();
		generar(prog, false, d.getArgs());
		printInst("retf");
	}
	
	private void generarFunCall(FunCall f) {
		if(funPred.invocadas.contains(f.getDef()))
			generarFunPredCall(f);
		else {
			int difProf = profundidad + 1 - f.getDef().getProf();
			printInst("mst " + difProf);
			printInst("ldc 0");
			Exp[] args = f.getArgs();
			int ini = numInst;
			int paramSize = 0;
			for(int i = 0; i < args.length; ++i) {
				generarExp(args[i]);
				paramSize += args[i].getTipo().getSize();
			}	
			printInst("cup " + (paramSize+1) + " " + f.getDef().getDir());
		}
	}

	private void generarFunPredCall(FunCall f) {
		FunPred fp = (FunPred) f.getDef();
		printInst("mst " + profundidad);
		printInst("ldc 0");
		Exp[] args = f.getArgs();
		int paramSize = 0;
		for(int i = 0; i < args.length; ++i) {
			generarExp(args[i]);
			paramSize += args[i].getTipo().getSize();
		}
		printAll(fp.preCall(f, this));
		printAll(fp.call(f, this));
		printAll(fp.postCall(f, this));
	}

	private void generarFunPred() {
		if(funPred.invocadas.size() > 0) {
			int tamTotal = 0;
			for(FunPred f: funPred.invocadas)
				tamTotal += f.code(this).length;
			printInst("ujp " + (numInst + tamTotal + 1));
			for(FunPred f: funPred.invocadas) {
				f.setDir(numInst);
				f.setProf(1);
				printAll(f.code(this));
			}
		}
	}

	private void generarReturn(Return r) {
		if(r.getVal() != null) {
			generarExp(((Return)r).getVal());
			printInst("str 0 0");
		}
		printInst("retf");
	}
	
	private void printInst(String inst) {
		output.format("{%s}  \t\t%s;\n", numInst, inst);
		++numInst;
	}

	private void printAll(String[] inst) {
		for(String i: inst)
			printInst(i);
	}

	public int getNumInst() {return numInst;}
}
