package gen.fun;

import ast.tipos.Tipo;
import ast.tipos.TipoPunt;
import ast.tipos.TipoInt;
import ast.tipos.TipoNull;

import ast.Argumento;
import ast.Iden;

import gen.GeneracionCodigo;


public class Length extends FunPred {

	public static final String ID = "length";

	private static final Tipo TIPO = new TipoInt();

	private static final Argumento[] ARGS = new Argumento[]
	{
		new Argumento(new Iden("array"), new TipoArray()),
		// Int Tamaño
	};

	private static final int TMB = GeneracionCodigo.TAM_MARCO_BASE;

	public Length() {
		super(ID, TIPO, ARGS);
	}
	
	@Override
	public String[] code(GeneracionCodigo g) {
		return new String[] {
			"ssp " + (TMB+2) + "	{length}",
			"sep 1",
			"lod 0 " + TMB,
			"inc 1",
			
			"lod 0 " + (TMB+1),
			"str 0 0";
			
			"retf		{end length}"
		};
	}
}