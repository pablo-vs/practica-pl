package ast.tipos;
import ast.NodoAst;

public class TipoArray extends Tipo {
	private Tipo tipoElems;

	public TipoArray(Tipo t) {
		tipoElems = t;
	}
	
	public EnumTipo getTipo() {return EnumTipo.ARRAY;}
	public String getName() {return "TipoArray";}
	public NodoAst[] getChildren() {return new NodoAst[] {tipoElems};}
	public Tipo getTipoElem() {return tipoElems;}
	public void setTipoElem(Tipo t) {tipoElems = t;}
	@Override
	public int getSize() {return 1;}
}
