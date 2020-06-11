package stat;

public class VincException extends Exception {
	
	public final String iden;
	public final int fila, col;

	public VincException(String id, String m, int f, int c) {
		super(m);
		iden = id;
		fila = f;
		col = c;
	}

	public VincException(String id, String m) {
		this(id, m, -1, -1);
	}

	public VincException(VincException e, int fila, int col) {
		this(e.iden, e.getMessage(), fila, col);
	}
}
