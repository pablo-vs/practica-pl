package ast.tipos;
import ast.NodoAst;

public class TipoPunt implements NodoAst, Tipo {
	private Tipo tipoRef;

	public TipoPunt(Tipo t) {
		tipoRef = t;
	}
	
	public EnumTipo getTipo() {return EnumTipo.PUNT;}
	public String getName() {return "TipoPunt";}
	public NodoAst[] getChildren() {return new NodoAst[] {tipoRef};}
	public Tipo getTipoRef() {return tipoRef;}
	public void setTipoRef(Tipo t) {tipoRef = t;}
	@Override
	public int getSize() {return 1;}
}
