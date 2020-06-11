package alex;

public class TokenValue {
    public String lexema;
    public int fila;
    public int col;
    public TokenValue(String lexema, int fila, int col) {
    	this.lexema = lexema;
    	this.fila = fila;
		this.col = col;
    }
}

