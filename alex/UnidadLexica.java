package alex;

import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol {
   private int fila, chr;
   public UnidadLexica(int fila, int chr, int clase, String lexema) {
     super(clase,lexema);
	 this.fila = fila;
	 this.chr = chr;
   }
   public int clase () {return sym;}
   public String lexema() {return (String)value;}
   public int fila() {return fila;}
   public int chr() {return chr;}
}
