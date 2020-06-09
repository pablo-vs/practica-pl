package ast.tipos;
import ast.HojaAst;

public class TipoNull implements HojaAst, Tipo {
	public EnumTipo getTipo() {return EnumTipo.TNULL;}
	public String getName() {return "TipoNull";}
	@Override
	public int getSize() {return 1;}
}
