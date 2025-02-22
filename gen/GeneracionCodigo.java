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
	private boolean comments = true;

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
		printInst("ssp " + tamMarco, "Inicio bloque");
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
				count += countInnerBlock(((DefFun)i).getBlock().getProg()) + 1;
		}
		if(count > 0) {
			printInst("ujp " + (numInst + count + 1), "Funciones anidadas");
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
		printInst("sto", "Inicio código");

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
			if(i.getInst() == null)
				continue;
			switch(i.getInst()) {
				case DEC:
					tamAct = asignarDec((Dec) i, tamAct);
					break;
				case ASIG:
					tamAct = asignarExp(((Asig) i).getExp(), tamAct);
					break;
				case IF:
					tamAct = asignarExp(((If)i).getCond(), tamAct);
					break;
				case REPEAT:
					Repeat r = (Repeat) i;
					tamAct = asignarExp(r.getLimit(), tamAct);
					if(r.getCond() != null)
						tamAct = asignarExp(r.getCond(), tamAct);
					break;
				case CASE:
					tamAct = asignarExp(((Case) i).getCond(), tamAct);
					break;
				case FUN_CALL:
					for(Exp e: ((FunCall)i).getArgs())
						tamAct = asignarExp(e, tamAct);
					break;
				case RETURN:
					tamAct = asignarExp(((Return)i).getVal(), tamAct);
					break;
				default:
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
		a.setProf(profundidad+1);
		return tamAct + size;
	}

	private int asignarDec(Dec d, int tamAct) {
		int size = d.getTipo().getSize();
		int res = tamAct;
		if(size > 1) {
			Tipo t = d.getTipo();
			asignarTipo(t);
		}
		if(d.getAsig() != null)
			res = asignarExp(d.getAsig().getExp(), tamAct);
		d.setDir(res + TAM_MARCO_BASE);
		return res + size;
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

	private int asignarExp(Exp e, int tamAct) {
		int res = tamAct;
		if (e instanceof ConstArray) {
			ConstArray ca = (ConstArray)e;
			for(Exp v: ca.getValues())
				res = asignarExp(v, res);
			ca.setDir(res + TAM_MARCO_BASE);
			TipoArray ta = (TipoArray) ca.getTipo();
			res += 2 + ta.getTipoElem().getSize()*ca.getValues().length;
		} else if (e instanceof ExpFun) {
			res = tamAct;
			for(Exp arg: ((ExpFun)e).getCall().getArgs())
				res = asignarExp(arg, res);
		}
		return res;
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
					difProf = profundidad + 1 - a.getArg().getProf();
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
				break;
			}
			case PUNT: {
				generarAsignable(a.getChild());
				printInst("ind");
				break;
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
					printInst("ldc " + Math.round(((ConstDec) e).value));
				}
				else if(e instanceof ConstArray) {
					ConstArray ca = (ConstArray) e;
					printInst("ldc " + (2+ca.getDir())); //Dir
					printInst("str 0 " + ca.getDir());
					printInst("ldc " + ca.getValues().length); //Tam
					printInst("str 0 " + (1+ca.getDir()));
					//Valores
					int offset = 2 + ca.getDir();
					for(Exp v: ca.getValues()) {
						if(v.getTipo().getSize() == 1) {
							generarExp(v);
							printInst("str 0 " + offset);
						} else {
							// Llamar a copy()
							printInst("mst " + profundidad);
							printInst("ldc 0");
							printInst("lda 0 " + offset);
							generarExp(v);
							printInst("ldc " + v.getTipo().getSize());
							printInst("cup 4 " + funPred.definidas.get("copy").getDir());
						}
						offset += v.getTipo().getSize();
					}
					printInst("lda 0 " + ca.getDir());
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
		int count = countInnerBlock(prog);
		printInst("ujp " + (count + numInst + 2));
		generar(prog, false);
		printInst("retp");
	}

	public int countOuterBlock(Block b) {
		return 5 + countInnerBlock(b.getProg());
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
				case FUN_CALL: {
					Exp[] args = ((FunCall)i).getArgs();
					for(int j = 0; j < args.length; ++j){
						size = Math.max(size, sizeExp(args[j]));
					}
					break;
				}
				case RETURN: {
					if(((Return)i).getVal() != null) {
						e = ((Return)i).getVal();
						size = sizeExp(e);
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
			size = sizeExpAsignable((ExpAsig)e);
		} else if(e.getOp() == Operator.NONE) {
			if(e instanceof Const) {
				if((e instanceof ConstInt) || (e instanceof ConstBool) || (e instanceof ConstDec)) {
					size += 1;
				}
				else if(e instanceof ConstArray) {
					Exp[] values = ((ConstArray)e).getValues();
					for (int i = 0; i < values.length; ++i)
						size += sizeExp(values[i]);
				}
				else if(e instanceof ConstTupla) {
					throw new RuntimeException("Tuplas no soportadas");
				}
				else if(e instanceof ConstDict) {
					throw new RuntimeException("Diccionarios no soportados");
				} 
				else if(e instanceof ConstString) {
					throw new RuntimeException("Strings no soportados");
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
				case REF: {
					size += sizeAsignable(((ExpAsig)ops[0]).getAsignable());
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
		int count = countInnerBlock(i.getBlock().getProg());
		if(i.getBlockElse() == null) {
			printInst("fjp " + (numInst + count + 6), "if");
			generarBlock(i.getBlock());
		} else {
			printInst("fjp " + (numInst + count + 7), "if");
			generarBlock(i.getBlock());
			count = countInnerBlock(i.getBlockElse().getProg());
			printInst("ujp " + (numInst + count + 6), "else");
			generarBlock(i.getBlockElse());
		}
	}
	
	private void generarRepeat(Repeat r) {
		int count = countInnerBlock(r.getBlock().getProg());

		// Generamos el límite, que queda almacenado en la pila
		generarExp(r.getLimit());

		int ini = numInst; // El bucle volverá a esta instrucción

		// Duplicamos el límite para hacer la comprobación
		printInst("dpl", "repeat");
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
		int count = 0, countCond = countExp(c.getCond());
		CaseMatch[] branches = c.getBranches();

		for(int i = 0; i < branches.length; ++i)
			count += countOuterBlock(branches[i].getBlock())
					+ countExp(branches[i].getValue())
					+ countCond + 3;

		int fin = numInst + count - 1;

		for(int i = 0; i < branches.length; ++i){
			Exp igual = new Exp(Operator.ES_IGUAL, c.getCond(), branches[i].getValue());
			generarExp(igual);
			count = countOuterBlock(branches[i].getBlock());
			if(i == branches.length - 1)
				--count;
			printInst("fjp " + (numInst + count + 2),
					"case " + branches[i].getValue().print());
			generarBlock(branches[i].getBlock());
			if(i < branches.length - 1)
				printInst("ujp " + fin);
		}
	}
	


	public int countInnerBlock(Prog p) {
		int count = 5;
		boolean funcionesAnidadas = false;
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
					count += countOuterBlock(((Block) i));
					break;
				case IF:
					count += countExp(((If) i).getCond())
							+ countOuterBlock(((If) i).getBlock()) + 1;
					if (((If) i).getBlockElse() != null)
						count += countOuterBlock(((If) i).getBlockElse()) + 1;
					break;
				case REPEAT:
					count += 6 + countExp(((Repeat) i).getLimit())
							+ countOuterBlock(((Repeat) i).getBlock());
					if (((Repeat) i).getCond() != null)
						count += 1 + countExp(((Repeat) i).getCond());
					break;
				case CASE:
					CaseMatch[] branches = ((Case) i).getBranches();
					int condIni = countExp(((Case) i).getCond());
					for(int j = 0; j < branches.length; ++j){
						count += 3 + condIni + countExp(branches[j].getValue()) + countOuterBlock(branches[j].getBlock());
					}
					count -= 1;
					break;
				case FUN_DEF:
					funcionesAnidadas = true;
					count += countInnerBlock(((DefFun) i).getBlock().getProg()) + 1;
					break;
				case FUN_CALL:
					count += 3;
					Exp[] args = ((FunCall) i).getArgs();
					for(int j = 0; j < args.length; ++j) {
						count += countExp(args[j]);
						if(args[j].getTipo().getSize() > 1)
							count += 1;
					}
					break;
				case RETURN:
					if (((Return)i).getVal() != null)
						count += 2 + countExp(((Return)i).getVal());
					else count += 1;
					break;
				default:
			}
		}
		if (funcionesAnidadas) ++count;
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
			case PUNT:{
				count += countAsignable(a.getChild());
				break;
			}
		}
		return count;
	}
	
	private int countExp (Exp e){
		int count = 1;
		if(e instanceof ExpAsig) {
			count = countExpAsignable((ExpAsig)e);
		} else if(e instanceof ExpFun) {
			count = 3;
			Exp[] args = ((ExpFun) e).getCall().getArgs();
			for(int j = 0; j < args.length; ++j) {
				count += countExp(args[j]);
				if(args[j].getTipo().getSize() > 1)
					count += 1;
			}
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
			printInst("mst " + difProf, "call " + f.getIden().print());
			printInst("ldc 0");
			Exp[] args = f.getArgs();
			int ini = numInst;
			int paramSize = 0;
			for(int i = 0; i < args.length; ++i) {
				generarExp(args[i]);
				int size = args[i].getTipo().getSize();
				if(size > 1)
					printInst("movs " + size);
				paramSize += size;
			}	
			printInst("cup " + (paramSize+1) + " " + f.getDef().getDir());
		}
	}

	private void generarFunPredCall(FunCall f) {
		FunPred fp = (FunPred) f.getDef();
		printInst("mst " + profundidad, "call " + fp.id);
		printInst("ldc 0");
		Exp[] args = f.getArgs();
		int paramSize = 0;
		for(int i = 0; i < args.length; ++i) {
			generarExp(args[i]);
			int size = args[i].getTipo().getSize();
			if(size > 1)
				printInst("movs " + size);
			paramSize += size;
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
			printInst("ujp " + (numInst + tamTotal + 1), "funciones predefinidas");
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

	private void printInst(String inst, String cmt) {
		if(comments) {
			output.format("{%s}  \t\t%s;\t\\\\%s\n", numInst, inst, cmt);
			++numInst;
		} else {
			printInst(inst);
		}
	}

	private void printAll(String[] inst) {
		if(inst != null)
			for(String i: inst)
				printInst(i);
	}

	public int getNumInst() {return numInst;}
}
