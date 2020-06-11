package ast.tipos;

public class TipoInt extends TipoBasico {
	@Override
	public EnumTipo getTipo() {return EnumTipo.TINT;}
	@Override
	public String getName() {return "TipoInt";}
	@Override
	public int getSize() {return 1;}
}
