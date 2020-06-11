package ast;
import ast.tipos.*;

public class DefTipo extends Inst {
	public EnumInst getInst() {return EnumInst.TIPO_DEF;}
	private Tipo tipo;
	private Iden iden;

	public DefTipo(Tipo tipo, Iden iden, int fila) {
		super(fila, 0);
		this.tipo = tipo;
		this.iden = iden;
	}

	public DefTipo(Tipo tipo, Iden iden) {
		this(tipo, iden, -1);
	}

	public Iden getIden() {return iden;}
	public Tipo getTipo() {return tipo;}
	@Override
	public String getName() {return "DefTipo";}
	@Override
	public NodoAst[] getChildren() {
		return new NodoAst[] {tipo, iden};
	}
}
