package ast;
import ast.tipos.Tipo;
import java.util.List;

public class DefFun extends Inst {
	public EnumInst getInst() {return EnumInst.FUN_DEF;}
	private Tipo tipo;
	private Iden iden;
	private Argumento[] args;
	private Block block;
	private int direccion;
	private int profundidad;

	public DefFun(Tipo t,Iden id, Block b, Argumento ... args) {
		tipo = t;
		iden = id;
		block = b;
		this.args = new Argumento[args.length];
		for(int i = 0; i < args.length; ++i)
			this.args[i] = args[i];
	}

	public DefFun(Tipo t, Iden id, Block b, List<Argumento> args, int fila) {
		super(fila, 0);
		tipo = t;
		iden = id;
		block = b;
		this.args = new Argumento[0];
		this.args = args.toArray(this.args);
	}

	public DefFun(Tipo t, Iden id, Block b, List<Argumento> args) {
		this(t, id, b, args, -1);
	}

	public DefFun(Tipo t,Iden id, Block b, int fila) {
		super(fila, 0);
		tipo = t;
		iden = id;
		block = b;
		this.args = new Argumento[0];
	}

	public DefFun(Tipo t,Iden id, Block b) {
		this(t, id, b, -1);
	}

	public Tipo getTipo() {return tipo;}
	public void setTipo(Tipo t) {tipo = t;}
	public Iden getIden() {return iden;}
	public Argumento[] getArgs() {return args;}
	public Block getBlock() {return block;}
	
	public void setDir(int d) {direccion = d;}
	public int getDir() {return direccion;}
	
	public void setProf(int p) {profundidad = p;}
	public int getProf() {return profundidad;}
	
	@Override
	public String getName() {return "FunDef";}
	@Override
	public NodoAst[] getChildren() {
		NodoAst[] res = new NodoAst[args.length+3];
		res[0] = tipo;
		res[1] = iden;
		for(int i = 0; i < args.length; ++i)
			res[i+2] = args[i];
		res[args.length+2] = block;
		return res;
	}
}
