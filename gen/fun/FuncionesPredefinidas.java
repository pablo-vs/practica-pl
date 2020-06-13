package gen.fun;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class FuncionesPredefinidas {
	
	// Tabla de funciones predefinidas
	public Map<String, FunPred> definidas;

	// Lista de funciones invocadas en un programa
	public Set<FunPred> invocadas;

	public FuncionesPredefinidas() {
		definidas = new HashMap<>();
		invocadas = new HashSet<>();

		AsignarArray asArr = new AsignarArray();
		definidas.put(asArr.id, asArr);
		Copy cp = new Copy();
		definidas.put(cp.id, cp);
	}

	public void addInvocada(String id) {
		invocadas.add(definidas.get(id));
	}

	public static final String ASIGNAR_ARRAY = AsignarArray.ID;
	public static final String COPY = Copy.ID;
}
