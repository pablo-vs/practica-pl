package ast;

public class SyntaxError extends Inst {
	public EnumInst getInst() {return null;}

	@Override
	public NodoAst[] getChildren() {return new NodoAst[0];}
	@Override
	public void printAst(StringBuilder result, String prefix) {
		result.append(prefix + "\\__" + getName() + "\n");
	}
	public String getName() {return "Syntax Error";}
	public String print() {return "Syntax Error";}
}
