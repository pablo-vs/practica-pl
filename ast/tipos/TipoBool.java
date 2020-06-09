package ast.tipos;
import ast.HojaAst;

public class TipoBool implements HojaAst, Tipo {
	public EnumTipo getTipo() {return EnumTipo.TBOOL;}
	public String getName() {return "TipoBool";}
	@Override
	public int getSize() {return 1;}
}
