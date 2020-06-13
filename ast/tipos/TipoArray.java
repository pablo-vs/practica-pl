package ast.tipos;
import ast.NodoAst;
import ast.exp.Exp;

public class TipoArray extends Tipo {
	private Tipo tipoElems;
	private Exp size;

	public TipoArray(Tipo t, Exp s) {
		tipoElems = t;
		size = s;
	}

	public TipoArray() {
		this(null, null);
	}
	
	public EnumTipo getTipo() {return EnumTipo.ARRAY;}
	public String getName() {return "TipoArray";}
	public NodoAst[] getChildren() {return new NodoAst[] {tipoElems, size};}
	public Tipo getTipoElem() {return tipoElems;}
	public void setTipoElem(Tipo t) {tipoElems = t;}
	public Exp getSizeExp() {return size;}
	@Override
	public int getSize() {return 2;}
	@Override
	public String print() {return "[" + tipoElems.print() + "," + size.print() + "]";}
}
