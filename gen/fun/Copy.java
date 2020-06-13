package gen.fun;

import ast.tipos.Tipo;
import ast.tipos.TipoPunt;
import ast.tipos.TipoInt;
import ast.tipos.TipoNull;

import ast.Argumento;
import ast.Iden;

import gen.GeneracionCodigo;


// Copia una cantidad dada de elementos de una dirección a otra
public class Copy extends FunPred {

	public static final String ID = "copy";

	private static final Tipo TIPO = new TipoNull();

	private static final Argumento[] ARGS = new Argumento[]
	{
		new Argumento(new Iden("punt1"), new TipoPunt()),
		new Argumento(new Iden("punt2"), new TipoPunt()),
		new Argumento(new Iden("size"), new TipoInt()),
	};

	private static final int TMB = GeneracionCodigo.TAM_MARCO_BASE;

	public Copy() {
		super(ID, TIPO, ARGS);
	}
	
	@Override
	public String[] code(GeneracionCodigo g) {
		int ini = g.getNumInst()+2;
		return new String[] {
			"ssp " + (TMB+3),
			"sep 2",
			"lod 0 " + TMB,
			"lod 0 " + (TMB+1),
			"ind",
			"sto",
			"lda 0 " + TMB,
			"lod 0 " + TMB,
			"inc 1",
			"sto",
			"lda 0 " + (TMB+1),
			"lod 0 " + (TMB+1),
			"inc 1",
			"sto",
			"lda 0 " + (TMB+2),
			"lod 0 " + (TMB+2),
			"dec 1",
			"sto",
			"lod 0 " + (TMB+2),
			"ldc 0",
			"leq",
			"fjp " + ini,
			"retp"
		};
	}
}
