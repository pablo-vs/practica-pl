package ast;

public class Iden implements HojaAst {
	private String value;
	public String getName() {return "Iden: " + value;}
	public String print() {return value;}

	public Iden(String val) {
		value = val;
	}
}
