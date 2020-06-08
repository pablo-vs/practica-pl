package ast.exp;
import ast.NodoAst;
import java.util.List;
import ast.tipos.Tipo;

public class Exp implements NodoAst {
	private Operator op;
	private Exp[] operands;

	private Tipo tipo;

	public void setTipo(Tipo t) {
		tipo = t;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Operator getOp() {return op;}
	public Exp[] getOperands() {return operands;}

	public Exp() {
		op = Operator.NONE;
		operands = new Exp[0];
	}

	public Exp(Operator op, Exp ... ops) {
		this.op = op;
		operands = new Exp[ops.length];
		for (int i = 0; i < ops.length; ++i)
			operands[i] = ops[i];
	}

	public Exp(Operator op, List<Exp> ops) {
		this.op = op;
		operands = new Exp[0];
		operands = ops.toArray(operands);
	}

	public String print() {
		return op.print(operands);
	}

	@Override
	public String getName() {return "Exp: " + op.getString();}
	@Override
	public NodoAst[] getChildren() {return operands;}
}
