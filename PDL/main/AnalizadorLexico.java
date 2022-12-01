public class AnalizadorLexico {
	private static String codigoFuente;
	private static int lineaActual;
	private static boolean codigo_ok;
	private static String listaTokens;
	private static AutomataFinitoDeterminista automata;
	private static String[] palabrasReservadas;

	public static void init() {
		automata = new AutomataFinitoDeterminista();
		palabrasReservadas = new String[] { "let", "int", "boolean", "string", "if", "switch", "case", "default",
				"break", "print", "input", "function", "return", "true", "false" };
	}

	public static void load(String codigo) {
		lineaActual = 1;
		codigoFuente = codigo;
		listaTokens = "";
	}

	public static Token generarToken() {
		automata.reset();
		codigo_ok = true;
		Token token = new Token();
		String palabra = "";
		int numero = 0;
		while (!automata.estadoFinal() && !codigoFuente.isEmpty() && codigo_ok) {
			int[] celda = automata.transitar(codigoFuente.charAt(0));
			if (celda[0] == -1) {
				GestorErrores.error(celda[1], lineaActual, codigoFuente.charAt(0));
				codigo_ok = false;
				codigoFuente = codigoFuente.substring(1);
			} else {
				for (int i = 1; i < celda.length; i++) {
					switch (celda[i]) { // se ejecuta la accion semantica que haya en la posicion i
					case 1:
						codigoFuente = codigoFuente.substring(1); // lee y hace mas pequeï¿½o el string
						break;
					case 2:
						palabra += codigoFuente.charAt(0); // concatenar
						break;
					case 3:
						numero = (int) codigoFuente.charAt(0) - 48; // primer digito del numero
						break;
					case 4:
						numero = numero * 10 + (int) codigoFuente.charAt(0) - 48; // concatenar digitos
						break;
					case 5: // token para numero entero
						if (numero > 32767) { // comprobar si el numero esta en el rango y generar token
							GestorErrores.error(4, lineaActual, numero); // FUERA RANGO
							codigo_ok = false;
						}
						token.codigo = "entero";
						token.valor = numero;
						break;
					case 6: // token para cadena de caracteres
						if (palabra.length() > 64) {
							GestorErrores.error(5, lineaActual, palabra); // ERROR FUERA DE RANGO
							codigo_ok = false;
						}
						token.codigo = "cadena";
						token.valor = palabra;
						break;
					case 7: // token para identificador o palabra reservada
						if (isPalabraReservada(palabra)) { // mirar si es palabra reservada
							token.codigo = palabra;
							token.valor = null;
						} else {
							if (TablaDeSimbolos.zona_decl) {
								int pos = TablaDeSimbolos.pos(palabra, false);
								if (pos == 0) {
									TablaDeSimbolos.add(palabra, false);
									pos = TablaDeSimbolos.pos(palabra, false);
								} else {
									GestorErrores.error(6, lineaActual, palabra); // ERROR VAR YA DECLARADA
									codigo_ok = false;
								}
								token.codigo = "identificador";
								token.valor = pos;
							} else {
								int pos = TablaDeSimbolos.pos(palabra, true);
								if (pos == 0) {
									TablaDeSimbolos.add(palabra, true);
									pos = TablaDeSimbolos.pos(palabra, true);
									TablaDeSimbolos.setTipo(pos, "entero");
									TablaDeSimbolos.setDespl(pos, 1);
								}
								token.codigo = "identificador";
								token.valor = pos;
							}
						}
						break;
					case 8: // token asignacion suma
						token.codigo = "asignacionSuma";
						break;
					case 9: // token suma
						token.codigo = "suma";
						break;
					case 10: // token igual
						token.codigo = "igual";
						break;
					case 11: // token para =
						token.codigo = "asignacion";
						break;
					case 12: // token para !=
						token.codigo = "distinto";
						break;
					case 13: // token para !
						token.codigo = "negacion";
						break;
					case 14: // token para (
						token.codigo = "parentesisAbierto";
						break;
					case 15: // token para )
						token.codigo = "parentesisCerrado";
						break;
					case 16: // token para {
						token.codigo = "llaveAbierta";
						break;
					case 17: // token para }
						token.codigo = "llaveCerrada";
						break;
					case 18: // token para -
						token.codigo = "resta";
						break;
					case 19: // token para ;
						token.codigo = "puntoycoma";
						break;
					case 20: // token para :
						token.codigo = "dospuntos";
						break;
					case 21: // token para ,
						token.codigo = "coma";
						break;
					case 22: // salto de linea
						lineaActual += 1;
						break;
					}
				}
			}
		}
		if (!codigo_ok && token.codigo == null)
			return generarToken();
		if (!automata.estadoFinal() && codigoFuente.isEmpty())
			token.codigo = "$";
		listaTokens += token.toString() + "\n";
		return token;
	}

	public static boolean isPalabraReservada(String palabra) {
		boolean esReservada = false;
		for (int i = 0; i < palabrasReservadas.length && !esReservada; i++)
			if (palabrasReservadas[i].equals(palabra))
				esReservada = true;
		return esReservada;
	}

	public static String getCodigoFuente() {
		return codigoFuente;
	}

	public static int getLineaActual() {
		return lineaActual;
	}

	public static String getListaTokens() {
		return listaTokens;
	}
}
