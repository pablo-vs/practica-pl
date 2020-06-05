package stat;

public class VincException extends Exception {
	
	public final String iden;

	public VincException(String id, String m) {
		super(m);
		iden = id;
	}
}
