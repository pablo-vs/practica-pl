package ast;
import ast.exp.Exp;
import java.util.List;

public class Case implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.CASE;}
	private Exp cond;
	private CaseMatch[] branches;

	public Case(Exp e, CaseMatch ... branches) {
		cond = e;
		this.branches = new CaseMatch[branches.length];
		for(int i = 0; i < branches.length; ++i)
			this.branches[i] = branches[i];
	}

	public Case(Exp e, List<CaseMatch> branches) {
		cond = e;
		this.branches = new CaseMatch[0];
		this.branches = branches.toArray(this.branches);
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
