package alex;

import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol {
   private int fila, col;
   public UnidadLexica(int fila, int col, int clase, String lexema) {
     super(clase,lexema);
	 this.fila = fila;
	 this.col = col;
   }
   public int clase () {return sym;}
   public String lexema() {return (String)value;}
   public int fila() {return fila;}
   public int col() {return col;}
}
