import java.util.ArrayList;

public class TablaDeSimbolos {
	private static int staticId;
	private static TablaDeSimbolos tsActual;
	private static TablaDeSimbolos tsGlobal;
	private static TablaDeSimbolos tsLocal;
	private static String tablas;
	public static boolean zona_decl;

	private int idTabla;
	private ArrayList<Identificador> tablaSimb;
	private int despl;

	public static void load() {
		staticId = 0;
		tsActual = null;
		tsGlobal = null;
		tsLocal = null;
		zona_decl = false;
		tablas = "";
	}

	public TablaDeSimbolos() {
		idTabla = staticId++;
		tablaSimb = new ArrayList<>();
		despl = 0;
	}

	private void _add(String lexema) {
		tablaSimb.add(new Identificador(lexema));
	}

	private int _pos(String lexema) {
		int res = -1;
		for (int i = 0; i < tablaSimb.size() && res == -1; i++)
			if (tablaSimb.get(i).lexema.equals(lexema))
				res = i;
		return res;
	}

	private void _setTipo(int pos, String tipo) {
		tablaSimb.get(pos).tipo = tipo;
	}

	private void _setTipoFunc(int pos, String tipoRet, String[] params) {
		tablaSimb.get(pos).tipo = "funcion";
		tablaSimb.get(pos).etiq = "fun" + pos;
		tablaSimb.get(pos).tipoRet = tipoRet;
		tablaSimb.get(pos).params = params.length;
		tablaSimb.get(pos).tipoParam = params;
	}

	private void _setDespl(int pos, int inc) {
		tablaSimb.get(pos).despl = despl;
		despl += inc;
	}

	private Identificador _get(int pos) {
		return tablaSimb.get(pos);
	}

	public String toString() {
		String res = "Tabla #" + idTabla + ":\n";
		for (Identificador id : tablaSimb)
			res += id + "\n";
		return res;
	}

	public static void crearTSGlobal() {
		if (tsGlobal != null)
			return;
		tsActual = tsGlobal = new TablaDeSimbolos();
	}

	public static void crearTSLocal() {
		if (tsLocal != null)
			return;
		tsActual = tsLocal = new TablaDeSimbolos();
	}

	public static void destruirTSGlobal() {
		if (tsGlobal == null)
			return;
		tablas += tsGlobal.toString() + "\n";
		tsActual = tsGlobal = null;
	}

	public static void destruirTSLocal() {
		if (tsLocal == null)
			return;
		tablas += tsLocal.toString() + "\n";
		tsLocal = null;
		tsActual = tsGlobal;
	}

	public static void add(String lexema, boolean global) {
		if (tsActual == null)
			crearTSGlobal();
		if (global)
			tsGlobal._add(lexema);
		else
			tsActual._add(lexema);
	}

	public static int pos(String lexema, boolean buscarTodas) {
		if (tsLocal != null) {
			int pos = tsLocal._pos(lexema);
			if (pos != -1)
				return -pos - 1;
			if (!buscarTodas)
				return 0;
		}
		if (tsGlobal != null)
			return tsGlobal._pos(lexema) + 1;
		return 0;
	}

	public static void setTipo(int pos, String tipo) {
		if (pos > 0)
			tsGlobal._setTipo(pos - 1, tipo);
		if (pos < 0)
			tsLocal._setTipo(-pos - 1, tipo);
	}

	public static void setTipoFunc(int pos, String tipoRet, String[] params) {
		if (pos > 0)
			tsGlobal._setTipoFunc(pos - 1, tipoRet, params);
		if (pos < 0)
			tsLocal._setTipoFunc(-pos - 1, tipoRet, params);
	}

	public static void setDespl(int pos, int inc) {
		if (pos > 0)
			tsGlobal._setDespl(pos - 1, inc);
		if (pos < 0)
			tsLocal._setDespl(-pos - 1, inc);
	}

	public static Identificador get(int pos) {
		if (pos > 0)
			return tsGlobal._get(pos - 1);
		if (pos < 0)
			return tsLocal._get(-pos - 1);
		return null;
	}

	public static String getTablas() {
		destruirTSLocal();
		destruirTSGlobal();
		return tablas;
	}
}
