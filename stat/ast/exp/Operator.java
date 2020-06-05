package ast.exp;

public enum Operator {
	AND("and",2), OR("or",2), ES_IGUAL("==",2), MAYOR(">",2), MENOR("<",2),
	MAYIG(">=",2), MENIG("<=",2), MAS("+",2), MENOS("-",2), CONCAT("++",2),
	POR("*",2), DIV("/",2), NOT("not",1), MOD("mod",2), PUNTO(".",2), 
	REF("&",1), DEREF("^",1), ACCESO("[]",2), NONE("",0);
	
	private final String txt;
	private final int arity;

	Operator(String txt, int ar) {
		this.txt = txt;
		arity = ar;
	}

	public String print(Exp[] operands) {
		switch(this) {
			case ACCESO:
				return String.format("%s[%s]", operands[0].print(), operands[1].print());
			case NONE:
				return "";
			default:
				if (arity == 1)
					return txt + operands[0].print();
				else if (arity == 2)
					return String.format("%s %s %s", operands[0].print(),
							txt, operands[1].print());
				else
					return "";
		}
	}

	public String getString() {
		return txt;
	}

	public int getArity() {
		return arity;
	}
}
