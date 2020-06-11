package ast.tipos;

public class TipoBool extends TipoBasico {
	public EnumTipo getTipo() {return EnumTipo.TBOOL;}
	public String getName() {return "TipoBool";}
	@Override
	public int getSize() {return 1;}
}
