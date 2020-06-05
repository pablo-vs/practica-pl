package asint;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoTiny;
import ast.Prog;
import stat.Vinculacion;
import errors.GestionErroresTiny;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);

		StringBuilder ast = new StringBuilder();
		Prog p = (Prog)asint.parse().value;

		System.out.println(asint.getErrores().numErrores() + " errores encontrados.");

		p.printAst(ast, "");
		System.out.println(ast.toString());

		Vinculacion vinc = new Vinculacion(new GestionErroresTiny());
		vinc.vincular(p);
	}
}   
   
