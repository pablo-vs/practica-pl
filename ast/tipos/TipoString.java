package ast.tipos;

public class TipoString extends TipoBasico {
	public EnumTipo getTipo() {return EnumTipo.TSTRING;}
	public String getName() {return "TipoString";}
	@Override
	public int getSize() {return 1;}
	@Override
	public String print() {return "String";}
}
