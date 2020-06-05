package ast.tipos;
import ast.NodoAst;
import ast.Iden;

public class CampoStruct implements NodoAst {
	private Iden name;
	private Tipo tipo;

	public CampoStruct(Iden nm, Tipo tp) {
		name = nm;
		tipo = tp;
	}
	
	public String getName() {return "CampoStruct";}
	public NodoAst[] getChildren() {return new NodoAst[] {name, tipo};}
}
