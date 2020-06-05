package ast;
import ast.tipos.*;

public class Dec implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.DEC;}
	private Tipo tipo;
	private Iden iden;
	private Asig as;

	public Dec(Tipo tipo, Iden iden) {
		this.tipo = tipo;
		this.iden = iden;
		this.as = null;
	}

	public Dec(Tipo tipo, Asig as) {
		this.tipo = tipo;
		this.iden = null;
		this.as = as;
	}

	public String getName() {return "DecVar";}
	public NodoAst[] getChildren() {
		return new NodoAst[] {tipo, iden == null ? as : iden};
	}
}
