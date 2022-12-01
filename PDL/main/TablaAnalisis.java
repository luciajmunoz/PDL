
public class TablaAnalisis {
	private static String[][] reglas;
	private static String[] tokens;
	private static String[] terminales;
	private static String[] noTerminales;
	private static int[][] tabla;

	public static void init() {
		reglas = new String[][] { { "1", "P", "2" }, { "B", "P", "3" }, { "F", "P", "4" }, { "lambda", "5" },
				{ "6", "let", "T", "id", "65", ";", "7" }, { "if", "(", "E", ")", "61", "S", "8" }, { "9", "S", "10" },
				{ "switch", "(", "id", ")", "62", "{", "W", "}", "11" }, { "case", "D", ":", "63", "C", "W", "12" },
				{ "default", ":", "64", "C", "W", "60" }, { "lambda", "13" }, { "int", "14" }, { "boolean", "15" },
				{ "string", "16" }, { "id", "S'", "17" }, { "print", "(", "E", ")", ";", "18" },
				{ "input", "(", "id", ")", ";", "19" }, { "return", "X", ";", "20" }, { "break", ";", "21" },
				{ "+=", "E", ";", "22" }, { "=", "E", ";", "23" }, { "(", "L", ")", ";", "24" }, { "E", "25" },
				{ "lambda", "26" }, { "27", "B", "C", "28" }, { "lambda", "29" }, { "E", "Q", "30" },
				{ "lambda", "31" }, { ",", "E", "Q", "32" }, { "lambda", "33" },
				{ "6", "function", "id", "H", "34", "(", "A", ")", "65", "35", "{", "C", "36", "}", "37" },
				{ "T", "38" }, { "lambda", "39" }, { "T", "id", "66", "K", "40" }, { "lambda", "41" },
				{ ",", "T", "id", "66", "K", "42" }, { "lambda", "43" }, { "R", "E'", "44" }, { "==", "R", "45" },
				{ "!=", "R", "45" }, { "lambda", "46" }, { "U", "R'", "47" }, { "+", "U", "R'", "48" },
				{ "-", "U", "R'", "48" }, { "lambda", "49" }, { "!", "V", "50" }, { "-", "V", "51" }, { "V", "52" },
				{ "id", "V'", "53" }, { "(", "E", ")", "54" }, { "entero", "55" }, { "cadena", "56" }, { "true", "57" },
				{ "false", "57" }, { "(", "L", ")", "58" }, { "lambda", "59" }, { "entero", "67" },
				{ "-", "entero", "68" } };

		tokens = new String[] { "let", "identificador", "int", "boolean", "string", "if", "switch", "case", "default",
				"break", "print", "input", "function", "return", "asignacion", "puntoycoma", "coma", "dospuntos",
				"parentesisAbierto", "parentesisCerrado", "llaveAbierta", "llaveCerrada", "igual", "asignacionSuma",
				"distinto", "suma", "resta", "negacion", "entero", "cadena", "true", "false", "$" };
		terminales = new String[] { "let", "id", "int", "boolean", "string", "if", "switch", "case", "default", "break",
				"print", "input", "function", "return", "=", ";", ",", ":", "(", ")", "{", "}", "==", "+=", "!=", "+",
				"-", "!", "entero", "cadena", "true", "false", "$" };
		noTerminales = new String[] { "P'", "P", "B", "W", "T", "S", "S'", "X", "C", "L", "Q", "F", "H", "A", "K", "E",
				"E'", "R", "R'", "U", "V", "V'", "D" };

		tabla = new int[noTerminales.length][terminales.length];
		for (int[] arr : tabla)
			for (int i = 0; i < arr.length; i++)
				arr[i] = -1;

		tabla[0][0] = 0;
		tabla[0][1] = 0;
		tabla[0][5] = 0;
		tabla[0][6] = 0;
		tabla[0][9] = 0;
		tabla[0][10] = 0;
		tabla[0][11] = 0;
		tabla[0][12] = 0;
		tabla[0][13] = 0;
		tabla[0][32] = 0;

		tabla[1][0] = 1;
		tabla[1][1] = 1;
		tabla[1][5] = 1;
		tabla[1][6] = 1;
		tabla[1][9] = 1;
		tabla[1][10] = 1;
		tabla[1][11] = 1;
		tabla[1][12] = 2;
		tabla[1][13] = 1;
		tabla[1][32] = 3;

		tabla[2][0] = 4;
		tabla[2][1] = 6;
		tabla[2][5] = 5;
		tabla[2][6] = 7;
		tabla[2][9] = 6;
		tabla[2][10] = 6;
		tabla[2][11] = 6;
		tabla[2][13] = 6;

		tabla[3][7] = 8;
		tabla[3][8] = 9;
		tabla[3][21] = 10;

		tabla[4][2] = 11;
		tabla[4][3] = 12;
		tabla[4][4] = 13;

		tabla[5][1] = 14;
		tabla[5][9] = 18;
		tabla[5][10] = 15;
		tabla[5][11] = 16;
		tabla[5][13] = 17;

		tabla[6][14] = 20;
		tabla[6][18] = 21;
		tabla[6][23] = 19;

		tabla[7][1] = 22;
		tabla[7][15] = 23;
		tabla[7][18] = 22;
		tabla[7][26] = 22;
		tabla[7][27] = 22;
		tabla[7][28] = 22;
		tabla[7][29] = 22;
		tabla[7][30] = 22;
		tabla[7][31] = 22;

		tabla[8][0] = 24;
		tabla[8][1] = 24;
		tabla[8][5] = 24;
		tabla[8][6] = 24;
		tabla[8][7] = 25;
		tabla[8][8] = 25;
		tabla[8][9] = 24;
		tabla[8][10] = 24;
		tabla[8][11] = 24;
		tabla[8][13] = 24;
		tabla[8][21] = 25;

		tabla[9][1] = 26;
		tabla[9][18] = 26;
		tabla[9][19] = 27;
		tabla[9][26] = 26;
		tabla[9][27] = 26;
		tabla[9][28] = 26;
		tabla[9][29] = 26;
		tabla[9][30] = 26;
		tabla[9][31] = 26;

		tabla[10][16] = 28;
		tabla[10][19] = 29;

		tabla[11][12] = 30;

		tabla[12][2] = 31;
		tabla[12][3] = 31;
		tabla[12][4] = 31;
		tabla[12][18] = 32;

		tabla[13][2] = 33;
		tabla[13][3] = 33;
		tabla[13][4] = 33;
		tabla[13][19] = 34;

		tabla[14][16] = 35;
		tabla[14][19] = 36;

		tabla[15][1] = 37;
		tabla[15][18] = 37;
		tabla[15][26] = 37;
		tabla[15][27] = 37;
		tabla[15][28] = 37;
		tabla[15][29] = 37;
		tabla[15][30] = 37;
		tabla[15][31] = 37;

		tabla[16][15] = 40;
		tabla[16][16] = 40;
		tabla[16][19] = 40;
		tabla[16][22] = 38;
		tabla[16][24] = 39;

		tabla[17][1] = 41;
		tabla[17][18] = 41;
		tabla[17][26] = 41;
		tabla[17][27] = 41;
		tabla[17][28] = 41;
		tabla[17][29] = 41;
		tabla[17][30] = 41;
		tabla[17][31] = 41;

		tabla[18][15] = 44;
		tabla[18][16] = 44;
		tabla[18][19] = 44;
		tabla[18][22] = 44;
		tabla[18][24] = 44;
		tabla[18][25] = 42;
		tabla[18][26] = 43;

		tabla[19][1] = 47;
		tabla[19][18] = 47;
		tabla[19][26] = 46;
		tabla[19][27] = 45;
		tabla[19][28] = 47;
		tabla[19][29] = 47;
		tabla[19][30] = 47;
		tabla[19][31] = 47;

		tabla[20][1] = 48;
		tabla[20][18] = 49;
		tabla[20][28] = 50;
		tabla[20][29] = 51;
		tabla[20][30] = 52;
		tabla[20][31] = 53;

		tabla[21][15] = 55;
		tabla[21][16] = 55;
		tabla[21][18] = 54;
		tabla[21][19] = 55;
		tabla[21][22] = 55;
		tabla[21][24] = 55;
		tabla[21][25] = 55;
		tabla[21][26] = 55;

		tabla[22][26] = 57;
		tabla[22][28] = 56;
	}

	public static int buscarRegla(String noTerminal, Token token) {
		int fila = numeroNoTerminal(noTerminal);
		int columna = numeroToken(token.codigo);
		return tabla[fila][columna]; // retornamos el numero de regla a aplicar, nos sirve para guardarla en el parse
	}

	public static String[] regla(int numero) {
		String[] regla = new String[reglas[numero].length];
		for (int i = 0; i < regla.length; i++)
			regla[i] = reglas[numero][i]; // los numeros que devolvemos en buscar regla empiezan por 1
		return regla;
	}

	public static boolean isTerminal(String palabra) {
		return numeroTerminal(palabra) != -1;
	}

	public static boolean isNoTerminal(String palabra) {
		return numeroNoTerminal(palabra) != -1;
	}

	public static boolean equiparar(Token token, String terminal) {
		return numeroTerminal(terminal) == numeroToken(token.codigo);
	}

	private static int numeroToken(String token) {
		int numero = -1;
		for (int i = 0; i < tokens.length; i++)
			if (tokens[i].equals(token))
				numero = i;
		return numero;
	}

	private static int numeroTerminal(String terminal) {
		int numero = -1;
		for (int i = 0; i < terminales.length; i++)
			if (terminales[i].equals(terminal))
				numero = i;
		return numero;
	}

	private static int numeroNoTerminal(String noTerminal) {
		int numero = -1;
		for (int i = 0; i < noTerminales.length; i++)
			if (noTerminales[i].equals(noTerminal))
				numero = i;
		return numero;
	}
}