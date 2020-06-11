package ast;
import ast.tipos.Tipo;

public class Argumento implements NodoAst {
	
	private Iden iden;
	private Tipo tipo;
	
	public Argumento(Iden id, Tipo tp) {
		iden = id;
		tipo = tp;
	}

	public Iden getIden() {return iden;}
	public Tipo getTipo() {return tipo;}
	@Override
	public String getName() {return "Argumento";}
	@Override
	public NodoAst[] getChildren() {
		return new NodoAst[] {iden, tipo};
	}
}