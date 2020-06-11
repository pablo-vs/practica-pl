package ast;
import ast.tipos.Tipo;

public class Argumento extends NodoAst {
	
	private Iden iden;
	private Tipo tipo;
	
	public Argumento(Iden id, Tipo tp) {
		iden = id;
		tipo = tp;
	}

	@Override
	public String getName() {return "Argumento";}
	@Override
	public NodoAst[] getChildren() {
		return new NodoAst[] {iden, tipo};
	}
}
