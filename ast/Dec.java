package ast;
import ast.tipos.*;

public class Dec extends Inst {
	public EnumInst getInst() {return EnumInst.DEC;}
	private Tipo tipo;
	private Iden iden;
	private Asig as;

	private int direccion;
	private int prof;

	public Dec(Tipo tipo, Iden iden) {
		super(tipo.fila, 0);
		this.tipo = tipo;
		this.iden = iden;
		this.as = null;
	}

	public Dec(Tipo tipo, Asig as) {
		super(tipo.fila, 0);
		this.tipo = tipo;
		this.iden = null;
		this.as = as;
	}

	public String getName() {return "DecVar";}
	public NodoAst[] getChildren() {
		return new NodoAst[] {tipo, iden == null ? as : iden};
	}

	public Iden getIden() {return iden;}

	public Tipo getTipo() {return tipo;}
	public void setTipo(Tipo t) {tipo = t;}

	public Asig getAsig() {return as;}

	public void setDir(int d) {direccion = d;}
	public int getDir() {return direccion;}

	public void setProf(int p) {prof = p;}
	public int getProf() {return prof;}
}
