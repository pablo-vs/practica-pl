package ast;
import ast.exp.Exp;
import java.util.List;

public class FunCall extends Inst {
	public EnumInst getInst() {return EnumInst.FUN_CALL;}
	private Iden iden;
	private Exp[] args;

	public FunCall(Iden id, Exp ... args) {
		iden = id;
		this.args = new Exp[args.length];
		for(int i = 0; i < args.length; ++i)
			this.args[i] = args[i];
	}

	public FunCall(Iden id, List<Exp> args) {
		iden = id;
		this.args = new Exp[0];
		this.args = args.toArray(this.args);
	}



	public String print() {
		StringBuilder res = new StringBuilder();
		res.append(iden.print());
		res.append("(");
		for(Exp e : args)
			res.append(e.print());
			res.append(", ");
		res.append(")");
		return res.toString();
	}

	@Override
	public String getName() {return "FunCall";}
	@Override
	public NodoAst[] getChildren() {
		NodoAst[] res = new NodoAst[args.length+1];
		res[0] = iden;
		for(int i = 0; i < args.length; ++i)
			res[i+1] = args[i];
		return res;
	}
}
