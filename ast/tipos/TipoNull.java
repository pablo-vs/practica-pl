package ast.tipos;

public class TipoNull extends TipoBasico {
	public EnumTipo getTipo() {return EnumTipo.TNULL;}
	public String getName() {return "TipoNull";}
	@Override
	public int getSize() {return 1;}
	@Override
	public String print() {return "Null";}
}
