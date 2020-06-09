package ast.tipos;
import ast.NodoAst;

public class TipoDict implements NodoAst, Tipo {
	private Tipo tipoClave, tipoValor;

	public TipoDict(Tipo tc, Tipo tv) {
		tipoClave = tc;
		tipoValor = tv;
	}
	
	public EnumTipo getTipo() {return EnumTipo.DICT;}
	public String getName() {return "TipoDict";}
	public NodoAst[] getChildren() {return new NodoAst[] {tipoClave, tipoValor};}
	public Tipo getTipoClave() {return tipoClave;}
	public Tipo getTipoValor() {return tipoValor;}
	@Override
	public int getSize() {return 1;}
}
