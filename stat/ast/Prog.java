package ast;

public class Prog implements NodoAst {
	private Inst[] children;
	
	public Prog() {
		children = new Inst[0];
	}

	public Prog(Inst ... inst_list) {
		children = new Inst[inst_list.length];
		for (int i = 0; i < inst_list.length; ++i) {
			children[i] = inst_list[i];
		}
	}

	public Prog(Inst i, Prog p) {
		children = new Inst[p.getChildren().length+1];
		children[0] = i;
		for (int j = 1; j < children.length; ++j) {
			children[j] = (Inst)(p.getChildren()[j-1]);
		}
	}

	public String getName() {return "Prog";}
	public NodoAst[] getChildren() {return children;}
}
