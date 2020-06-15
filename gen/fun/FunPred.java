package gen.fun;

import ast.DefFun;
import ast.FunCall;
import ast.Iden;
import ast.Argumento;
import ast.tipos.Tipo;

import java.util.List;

import gen.GeneracionCodigo;

public abstract class FunPred extends DefFun {
	public final String id;

	public FunPred(String id, Tipo t, Argumento ... args) {
		super(t, new Iden(id), null, args);
		this.id = id;
	}

	public FunPred(String id, Tipo t) {
		super(t, new Iden(id), null);
		this.id = id;
	}

	abstract public String[] code(GeneracionCodigo g);

	public String[] preCall(FunCall f, GeneracionCodigo g) {
		return null;
	}

	public String[] call(FunCall f, GeneracionCodigo g) {
		return new String[] {
			"cup " + (getArgs().length+1)  + " " + getDir()
		};
	}

	public String[] postCall(FunCall f, GeneracionCodigo g) {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		if(!(o instanceof FunPred))
			return false;
		return id.equals(((FunPred)o).id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
