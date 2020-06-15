package gen.fun;

import ast.tipos.Tipo;
import ast.tipos.TipoInt;
import ast.tipos.TipoArray;
import ast.tipos.TipoNull;
import ast.tipos.TipoPunt;

import ast.Argumento;
import ast.FunCall;
import ast.Iden;

import gen.GeneracionCodigo;


public class NewArray extends FunPred {

	public static final String ID = "newArray";

	private static final Tipo TIPO = new TipoArray();

	private static final Argumento[] ARGS = new Argumento[]
	{
		new Argumento(new Iden("dim"), new TipoArray(new TipoInt())),	// dimensiones
		// Int Tamaño del tipo
		// Addr Dirección de los datos
		// Addr Dirección del tamaño de marco
	};

	private static final int TMB = GeneracionCodigo.TAM_MARCO_BASE;

	private static final String[] CODE = new String[]
	{
		"ssp " + (4+TMB) + "	{newArray}",
		"sep 3",

		// 1. Escribimos en el descriptor el número de elementos
		"lod 0 " + TMB, // Cargamos la dir del tamaño en el descriptor
		"inc 1",
		"lod 0 " + (TMB+1), // Cargamos el num de elementos
		"sto",				// Almacenamos
		
		// 2. Almacenaremos los datos al final del marco actual
		// Cargamos esa dirección en el descriptor
		"lod 0 " + TMB,
		"lod 0 " + (TMB+4),
		"ind",
		"sto",

		// 3. Aumentamos el tamaño del marco

		// Multiplicamos el num de elems por el tamaño de cada elem
		"lod 0 " + (TMB+1),
		"lod 0 " + (TMB+2),
		"mul",

		// Ese es el valor que devolvemos
		"str 0 0",

		// Cargamos el tam marco actual
		"lod 0 " + (TMB+4),
		"dpl",
		"ind",

		// Sumamos el tam total de los datos y guardamos
		"lod 0 0",
		"add",
		"sto",

		"retf		{end newArray}"
	};


	public NewArray() {
		super(ID, TIPO, ARGS);
	}

	@Override
	public String[] code(GeneracionCodigo g) {
		return CODE;
	}
	
	@Override
	public String[] preCall(FunCall f, GeneracionCodigo g) {
		return new String[] {
			"ldc " + ((TipoArray)((TipoPunt)f.getArgs()[0].getTipo())
					.getTipoRef()).getTipoElem().getSize(),
			"lda 0 0",
			"lod 0 " + (TMB-1),
			"add",
			"lda 0 " + (TMB-1),
		};
	}

	@Override
	public String[] call(FunCall f, GeneracionCodigo g) {
		return new String[] {
			"cup 6 " + getDir()
		};
	}

	@Override
	public String[] postCall(FunCall f, GeneracionCodigo g) {
		int ini = g.getNumInst();
		return new String[] {
			"dpl",
			"ldc 1",
			"grt",
			"fjp " + (ini + 5 + 3),
			"dpl",
			"dec 1",
			"dpl",
			"ujp " + (ini + 1)
		};
	}
}
