package ast.tipos;
import ast.NodoAst;
import java.util.List;

public class TipoStruct implements NodoAst, Tipo {
	private CampoStruct[] campos;

	public TipoStruct(CampoStruct ... cps) {
		campos = new CampoStruct[cps.length];
		for (int i = 0; i < cps.length; ++i)
			campos[i] = cps[i];
	}

	public TipoStruct(List<CampoStruct> cps) {
		campos = new CampoStruct[0];
		campos = cps.toArray(campos);
	}
	
	public EnumTipo getTipo() {return EnumTipo.STRUCT;}
	public String getName() {return "TipoStruct";}
	public NodoAst[] getChildren() {return campos;}
	public CampoStruct[] getCampos() {return campos;}
	@Override
	public int getSize() {
		int res = 0;
		for(CampoStruct c : campos) {
			res += c.getTipo().getSize();
		}
		return res;
	}
}
