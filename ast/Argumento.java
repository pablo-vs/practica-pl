package ast;
import ast.tipos.Tipo;

public class Argumento extends NodoAst {
	
	private Iden iden;
	private Tipo tipo;

	private int direccion;
	
	public Argumento(Iden id, Tipo tp) {
		super(id.fila, id.col);
		iden = id;
		tipo = tp;
	}

	public int getDir() {return direccion;}
	public void setDir(int d) {direccion = d;}

	public Iden getIden() {return iden;}
	public Tipo getTipo() {return tipo;}
	@Override
	public String getName() {return "Argumento";}
	@Override
	public NodoAst[] getChildren() {
		return new NodoAst[] {iden, tipo};
	}
}
