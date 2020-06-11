package stat;

public class CompException extends Exception {
	
	public final int fila, col;

	public CompException(String m, int f, int c) {
		super(m);
		fila = f;
		col = c;
	}

	public CompException(String m) {
		this(m, -1, -1);
	}
}
