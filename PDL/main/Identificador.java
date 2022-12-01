public class Identificador {
	public final String lexema;
	public String tipo;
	public Integer despl;
	public String etiq;
	public Integer params;
	public String[] tipoParam;
	public String tipoRet;

	public Identificador(String lexema) {
		this.lexema = lexema;
	}

	public String toString() {
		String res = "* '" + lexema + "'";
		if (tipo != null)
			res += "\n  + Tipo: '" + tipo + "'";
		if (despl != null)
			res += "\n  + Despl: " + despl;
		if (etiq != null)
			res += "\n  + EtiqFuncion: '" + etiq + "'";
		if (params != null)
			res += "\n  + numParam: " + params;
		if (tipoParam != null)
			for (int i = 0; i < tipoParam.length; i++)
				res += "\n  + TipoParam" + (i + 1) + ": '" + tipoParam[i] + "'";
		if (tipoRet != null)
			res += "\n  + TipoRetorno: '" + tipoRet + "'";
		return res;
	}
}
