package ast.tipos;
import ast.HojaAst;

public class TipoDec implements HojaAst, Tipo {
	public EnumTipo getTipo() {return EnumTipo.TDEC;}
	public String getName() {return "TipoDec";}
	@Override
	public int getSize() {return 1;}
}
