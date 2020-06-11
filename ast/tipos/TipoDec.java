package ast.tipos;

public class TipoDec extends TipoBasico {
	public EnumTipo getTipo() {return EnumTipo.TDEC;}
	public String getName() {return "TipoDec";}
	@Override
	public int getSize() {return 1;}
	@Override
	public String print() {return "Dec";}
}
