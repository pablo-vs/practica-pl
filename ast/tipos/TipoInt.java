package ast.tipos;
import ast.HojaAst;

public class TipoInt implements HojaAst, Tipo {
	public EnumTipo getTipo() {return EnumTipo.TINT;}
	public String getName() {return "TipoInt";}
}
