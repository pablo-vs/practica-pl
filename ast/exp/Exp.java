package ast.exp;
import ast.NodoAst;
import java.util.List;
import ast.tipos.Tipo;

public class Exp extends NodoAst {
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

	public Exp(int fila, int col) {
		super(fila, col);
		op = Operator.NONE;
		operands = new Exp[0];
	}

	public Exp() {
		this(-1,-1);
	}

	public Exp(Operator op, Exp ... ops) {
		this.op = op;
		operands = new Exp[ops.length];
		for (int i = 0; i < ops.length; ++i)
			operands[i] = ops[i];
	}

	public Exp(Operator op, List<Exp> ops, int fila, int col) {
		super(fila, col);
		this.op = op;
		operands = new Exp[0];
		operands = ops.toArray(operands);
	}

	public Exp(Operator op, List<Exp> ops) {
		this(op, ops, ops.get(0).fila, ops.get(0).col);
	}

	public String print() {
		return op.print(operands);
	}
/*
	public Exp getArray() {
		if(op == Operator.ACCESO) {
		   return operands[0];
		} else if (op == Operator.NONE) {
			return this;
		} else {
			throw new UnsupportedOperationException("Llamada a getArray en algo que no es un accesor");
		}

	}
*/
	@Override
	public String getName() {return "Exp: " + op.getString();}
	@Override
	public NodoAst[] getChildren() {return operands;}
}
