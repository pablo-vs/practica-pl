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
		// Addr Dirección del tamaño de marco
	};

	private static final int TMB = GeneracionCodigo.TAM_MARCO_BASE;

	public Malloc() {
		super(ID, TIPO, ARGS);
	}
	
	@Override
	public String[] code(GeneracionCodigo g) {
		return new String[] {
			"ssp " + (TMB+2) + "	{malloc}",
			"sep 1",
			
			"lod 0 " + TMB,
			"lod 0 " + (TMB+1),
			"ind",
			"sto",
			
			"lod 0 " + TMB,
			"str 0 0",
			
			"lod 0 " + (TMB+1),
			"dpl",
			"ind",
			
			"lod 0 0",
			"add",
			"sto",
			
			"retf		{end malloc}"
		};
	}
}