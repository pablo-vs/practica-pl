package ast.tipos;
import ast.NodoAst;
import java.util.List;

public class TipoTupla extends Tipo {
	private Tipo[] tiposElems;

	public TipoTupla(Tipo ... tipos) {
		tiposElems = new Tipo[tipos.length];
		for (int i = 0; i < tipos.length; ++i)
			tiposElems[i] = tipos[i];
	}

	public TipoTupla(List<Tipo> tipos) {
		tiposElems = new Tipo[0];
		tiposElems = tipos.toArray(tiposElems);
	}
	
	public EnumTipo getTipo() {return EnumTipo.TUPLA;}
	public String getName() {return "TipoTupla";}
	public NodoAst[] getChildren() {return tiposElems;}
	public Tipo[] getTiposElems() {return tiposElems;}
	@Override
	public int getSize() {
		int res = 0;
		for(Tipo t: tiposElems) {
			res += t.getSize();
		}
		return res;
	}
}
