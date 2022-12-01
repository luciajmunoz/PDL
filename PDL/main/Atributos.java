public class Atributos {
	public String tipo; // atributo para el tipo
	public String tipoRetorno; // atributo para el tipo de retorno
	public int ancho; // atributo para el ancho
	public int posTS;
	public String[] params;
	public boolean enSwitch;
	
	public String toString() {
		String res = "";
		if (tipo != null)
			res += ", Tipo: " + tipo;
		if (tipoRetorno != null)
			res += ", TipoRet: " + tipoRetorno;
		if (ancho != 0)
			res += ", Acho: " + ancho;
		if (posTS != 0)
			res += ", PosTS: " + posTS;
		if (params != null)
			res += ", NParams: " + params.length;
		if (enSwitch)
			res += ", Switch: true";
		return res.length() == 0 ? "" : res.substring(2);
	}
}
