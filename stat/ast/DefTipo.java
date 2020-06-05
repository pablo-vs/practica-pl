package ast;
import ast.tipos.*;

public class DefTipo implements NodoAst, Inst {
	public EnumInst getInst() {return EnumInst.TIPO_DEF;}
	private Tipo tipo;
	private Iden iden;

	public DefTipo(Tipo tipo, Iden iden) {
		this.tipo = tipo;
		this.iden = iden;
	}

	@Override
	public String getName() {return "DefTipo";}
	@Override
	public NodoAst[] getChildren() {
		return new NodoAst[] {tipo, iden};
	}
}
