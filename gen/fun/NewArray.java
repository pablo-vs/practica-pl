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

	private static final Tipo TIPO = new TipoNull();

	private static final Argumento[] ARGS = new Argumento[]
	{
		// puntero al array
		new Argumento(new Iden("arr"), new TipoPunt(new TipoArray())),
		new Argumento(new Iden("dim"), new TipoArray(new TipoInt())),	// dimensiones
		// Int Tamaño del tipo
		// Int dimension actual
	};

	private static final int TMB = GeneracionCodigo.TAM_MARCO_BASE;

	public NewArray() {
		super(ID, TIPO, ARGS);
	}

	@Override
	public String[] code(GeneracionCodigo g) {
		int ini = g.getNumInst();
		return new String[]
		{

			// Segunda parte, asignar internamente
			"ssp " + (9+TMB) + "	{newArray}",
			"sep 11",

			// El tamaño actual de los datos se guarda en TMB+6
			// Para el bucle interno
			// El contador del bucle se guarda en TMB+7
			// La posición actual en el array en TMB+8

			// 1. Escribimos en el descriptor el número de elementos
			"lod 0 " + (TMB), // Cargamos la dir del tamaño en el descriptor
			"inc 1",
			"lod 0 " + (TMB+1),   // Cargamos el num de elementos
			"lod 0 " + (TMB+4),
			"add",
			"ind",
			"sto",				// Almacenamos
			

			// 2. Calculamos el tamaño

			// Multiplicamos el num de elems por el tamaño de cada elem
			// el tamaño es 2 salvo que estemos en la última dimensión
			"lod 0 " + (TMB+2),
			"dec 1",
			"lod 0 " + (TMB+4),
			"grt",
			"fjp " + (ini+16),
			"ldc 2",
			"ujp " + (ini+17),
			"lod 0 " + (TMB+3), // ini+16
			"lod 0 " + (TMB), // ini+17
			"inc 1",
			"ind",
			"mul",
			"str 0 " + (TMB+6), // Almacenamos

			// 3. Reservamos memoria y cargamos la dirección de los datos en el descriptor
			"lod 0 " + (TMB),
			"lod 0 " + (TMB+6),
			"new",

			// Si estamos en la última dimensión, paramos
			"lod 0 " + (TMB+2),
			"dec 1",
			"lod 0 " + (TMB+4),
			"grt",
			"fjp " + (ini+58),

			// Cargamos la dirección de los datos en posicion actual
			"lod 0 " + (TMB),
			"ind",
			"str 0 " + (TMB+8),

			// Inicializamos a 0 el contador
			"ldc 0",
			"str 0 " + (TMB+7),
	
			// 4. Iteramos para cada elemento del nuevo array

			"lod 0 " + (TMB+7), 		//ini+35
			"dpl",
			"lod 0 " + (TMB+1),
			"lod 0 " + (TMB+4),
			"add",
			"ind",
			"les",
			"fjp " + (ini+58),
			"inc 1",
			"str 0 " + (TMB+7),
			
			// Cargamos los parámetros
			"mst 1",

			"ldc 0",

			"lod 0 " + (TMB+8), // dir descriptor
			"lda 0 " + (TMB+1),   //dimensiones
			"movs 2",
			"lod 0 " + (TMB+3),	  //tam datos
			"lod 0 " + (TMB+4),	  //dim act +1
			"inc 1",
			"cup 6 " + getDir(),
			
			// Aumentamos la posicion del descriptor en 2
			"lod 0 " + (TMB+8),
			"inc 2",
			"str 0 " + (TMB+8),

			"ujp " + (ini+35),

			"retp		{end newArray}"  //58
		};

	}
	
	@Override
	public String[] preCall(FunCall f, GeneracionCodigo g) {
		TipoArray ta = (TipoArray)((TipoPunt)f.getArgs()[0].getTipo()).getTipoRef();
		while(ta.getTipoElem() instanceof TipoArray)
			ta = (TipoArray) ta.getTipoElem();
		return new String[] {
			//Tam datos
			"ldc " + ta.getTipoElem().getSize(),
			//Dim actual
			"ldc 0",
		};
	}

	@Override
	public String[] call(FunCall f, GeneracionCodigo g) {
		return new String[] {
			"cup 6 " + getDir()
		};
	}
}
