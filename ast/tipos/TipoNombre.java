package ast.tipos;
import ast.Iden;
import ast.DefTipo;

public class TipoNombre extends TipoBasico {
	private Iden valor;

	private DefTipo def;

	public TipoNombre(Iden vl) {
		valor = vl;
	}

	public EnumTipo getTipo() {return EnumTipo.IDENTIPO;}
	public String getName() {return "TipoNombre: " + valor.getName();}
	@Override
	public int getSize() {return def.getTipo().getSize();}
	public Iden getValor() {return valor;}
	public DefTipo getDef() {return def;}
	public void setDef(DefTipo d) {def = d;}
	@Override
	public String print() {return def == null ? valor.print() : def.getTipo().print();}
}
