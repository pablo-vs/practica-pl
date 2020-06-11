package ast;
import ast.exp.Exp;
import java.util.List;

public class Case extends Inst {
	public EnumInst getInst() {return EnumInst.CASE;}
	private Exp cond;
	private CaseMatch[] branches;

	public Case(Exp e, CaseMatch ... branches) {
		cond = e;
		this.branches = new CaseMatch[branches.length];
		for(int i = 0; i < branches.length; ++i)
			this.branches[i] = branches[i];
	}

	public Case(Exp e, List<CaseMatch> branches, int fila) {
		super(fila, 0);
		cond = e;
		this.branches = new CaseMatch[0];
		this.branches = branches.toArray(this.branches);
	}

	public Case(Exp e, List<CaseMatch> branches) {
		this(e,branches,-1);
	}

	@Override
	public String getName() {return "Case";}
	@Override
	public NodoAst[] getChildren() {
		NodoAst[] res = new NodoAst[branches.length+1];
		res[0] = cond;
		for(int i = 0; i < branches.length; ++i)
			res[i+1] = branches[i];
		return res;
	}
}
