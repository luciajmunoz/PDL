public class Pair {
	public String simbolo;
	public Atributos atribs;

	public Pair(String simbolo, Atributos atribs) {
		this.simbolo = simbolo;
		this.atribs = atribs;
	}

	public String toString() {
		return "<" + simbolo + ", " + atribs + ">";
	}
}
