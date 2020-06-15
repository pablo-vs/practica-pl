package gen.fun;

import ast.tipos.Tipo;
import ast.tipos.TipoPunt;
import ast.tipos.TipoInt;
import ast.tipos.TipoNull;

import ast.Argumento;
import ast.Iden;

import gen.GeneracionCodigo;

public class Malloc extends FunPred {

	public static final String ID = "malloc";

	private static final Tipo TIPO = new TipoPunt();

	private static final Argumento[] ARGS = new Argumento[]
	{
		new Argumento(new Iden("size"), new TipoInt()),
	};

	private static final int TMB = GeneracionCodigo.TAM_MARCO_BASE;

	public Malloc() {
		super(ID, TIPO, ARGS);
	}
	
	@Override
	public String[] code(GeneracionCodigo g) {
		return new String[] {
			"ssp " + (TMB+1) + "	{malloc}",
			"sep 2",
			
			"lda 0 0",
			"lod 0 " + TMB,
			"new",

			"retf		{end malloc}"
		};
	}
}
