package ast.tipos;
import ast.HojaAst;

public class TipoInt implements HojaAst, Tipo {
	@Override
	public EnumTipo getTipo() {return EnumTipo.TINT;}
	@Override
	public String getName() {return "TipoInt";}
	@Override
	public int getSize() {return 1;}
}
