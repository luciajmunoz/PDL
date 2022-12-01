public class Token {
	public String codigo;
	public Object valor;

	public String toString() {
		String resultado = "<" + codigo + ", ";
		if (valor instanceof String)
			resultado += "\"" + valor + "\"";
		else if (valor != null)
			resultado += valor.toString();
		resultado += ">";
		return resultado;
	}
}
