package ast.tipos;
import ast.NodoAst;
import ast.Iden;

public class CampoStruct extends NodoAst {
	private Iden name;
	private Tipo tipo;

	private int offset;

	public CampoStruct(Iden nm, Tipo tp) {
		name = nm;
		tipo = tp;
	}
	
	public String getName() {return "CampoStruct";}
	public NodoAst[] getChildren() {return new NodoAst[] {name, tipo};}
	public Iden getIden() {return name;}
	public Tipo getTipo() {return tipo;}
	public void setTipo(Tipo t) {tipo = t;}
	public int getOffset() {return offset;}
	public void setOffset(int o) {offset = o;}
	public String print() {return name.print() + ":" + tipo.print();}
}
