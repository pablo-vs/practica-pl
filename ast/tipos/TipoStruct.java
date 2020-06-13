package ast.tipos;
import ast.NodoAst;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

public class TipoStruct extends  Tipo {
	private CampoStruct[] campos;

	private TreeMap<String, CampoStruct> mapaCampos;

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

	public Map<String, CampoStruct> getMapaCampos() {
		return mapaCampos;
	}

	public void setMapaCampos(Map<String, CampoStruct> m) {
		mapaCampos = new TreeMap<>(m);
	}
	@Override
	public String print() {
		StringBuilder sb = new StringBuilder();
		sb.append("struct [");
		boolean first = true;
	   	for(CampoStruct c: campos) {
			if(first) first = false;
			else sb.append(",");
			sb.append(c.print());
		}
		sb.append("]");
		return sb.toString();
	}
}
