package ast.tipos;
import ast.HojaAst;

public class TipoString implements HojaAst, Tipo {
	public EnumTipo getTipo() {return EnumTipo.TSTRING;}
	public String getName() {return "TipoString";}
}
