package asint;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoTiny;
import ast.Prog;
import stat.Vinculacion;
import stat.Comprobacion;
import gen.GeneracionCodigo;
import errors.GestionErroresTiny;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws Exception {
	
		boolean printAst = true;
		if(args.length > 0) {
			if(Arrays.asList(args).contains("-noast"))
				printAst = false;
		}
	

		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);

		StringBuilder ast = new StringBuilder();
		Prog p;
		   
		try {
			p = (Prog)asint.parse().value;
			if(printAst) {
				p.printAst(ast, "");
				System.out.println(ast.toString());
			}
				
			int syntErr = asint.getErrores().numErrores();
			
			GestionErroresTiny error = new GestionErroresTiny();

			Vinculacion vinc = new Vinculacion(error);
			vinc.vincular(p);

			if(error.numErrores() > 0) {
				System.out.println((error.numErrores() + syntErr) + " errores encontrados.");
			} else {
		
				Comprobacion comp = new Comprobacion(error);
				comp.comprobar(p);

				if(error.numErrores() > 0) {
					System.out.println((error.numErrores() + syntErr) + " errores encontrados.");
				} else if(syntErr > 0) {
					System.out.println(syntErr + " errores encontrados.");
				} else {
				
					GeneracionCodigo gen = new GeneracionCodigo("programa.txt");
					gen.generarCodigo(p);
					
				}
			}
		} catch(Exception e) {
			System.err.println("Imposible recuperarse de los errores sintácticos");
		}

	}
}   
   
