package ast;
import ast.tipos.Tipo;
import java.util.List;

public class DefFun  implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.FUN_DEF;}
	private Tipo tipo;
	private Iden iden;
	private Argumento[] args;
	private Block block;

	public DefFun(Tipo t,Iden id, Block b, Argumento ... args) {
		tipo = t;
		iden = id;
		block = b;
		this.args = new Argumento[args.length];
		for(int i = 0; i < args.length; ++i)
			this.args[i] = args[i];
	}

	public DefFun(Tipo t, Iden id, Block b, List<Argumento> args) {
		tipo = t;
		iden = id;
		block = b;
		this.args = new Argumento[0];
		this.args = args.toArray(this.args);
	}

	public DefFun(Tipo t,Iden id, Block b) {
		tipo = t;
		iden = id;
		block = b;
		this.args = new Argumento[0];
	}

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
