package ast.tipos;
import ast.HojaAst;
import ast.Iden;

public class TipoNombre implements HojaAst, Tipo {
	private Iden valor;

	public TipoNombre(Iden vl) {
		valor = vl;
	}

	public EnumTipo getTipo() {return EnumTipo.IDENTIPO;}
	public String getName() {return "TipoNombre: " + valor.getName();}
	@Override
	public int getSize() {return -1;}
}
