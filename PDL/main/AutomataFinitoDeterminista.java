public class AutomataFinitoDeterminista {
	private final int estadoInicial;
	private final int estadoFinal;
	private final int nEstadosFinales;
	private final int[][][] matrizTransiciones;
	private int estado;

	public void reset() {
		estado = estadoInicial;
	}

	public AutomataFinitoDeterminista() {
		estadoInicial = 0;
		estadoFinal = 26;
		nEstadosFinales = 17;
		matrizTransiciones = new int[estadoFinal - nEstadosFinales + 1][19][];
		// 27 estados (de los cuales 17 son finales), 19 tipos de caracter

		// Ahora meter la matriz de transiciones a la fuerza:
		// - Transicion, acciones semanticas...
		// - Error, código error
		matrizTransiciones[0][0] = new int[] { 1, 1 };
		matrizTransiciones[0][1] = new int[] { 2, 3, 1 };
		matrizTransiciones[0][2] = new int[] { 8, 2, 1 };
		matrizTransiciones[0][3] = new int[] { 0, 1, 22 };
		matrizTransiciones[0][4] = new int[] { 3, 1 };
		matrizTransiciones[0][5] = new int[] { 4, 1 };
		matrizTransiciones[0][6] = new int[] { 5, 0, 1 };
		matrizTransiciones[0][7] = new int[] { 6, 1 };
		matrizTransiciones[0][8] = new int[] { -1, 2 };
		matrizTransiciones[0][9] = new int[] { 19, 14, 1 };
		matrizTransiciones[0][10] = new int[] { 20, 15, 1 };
		matrizTransiciones[0][11] = new int[] { 21, 16, 1 };
		matrizTransiciones[0][12] = new int[] { 22, 17, 1 };
		matrizTransiciones[0][13] = new int[] { 23, 18, 1 };
		matrizTransiciones[0][14] = new int[] { 24, 19, 1 };
		matrizTransiciones[0][15] = new int[] { 25, 20, 1 };
		matrizTransiciones[0][16] = new int[] { 26, 21, 1 };
		matrizTransiciones[0][17] = new int[] { 0, 1 };
		matrizTransiciones[0][18] = new int[] { -1, 0 };

		matrizTransiciones[1][0] = new int[] { 10, 1, 6 };
		matrizTransiciones[1][1] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][2] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][3] = new int[] { -1, 3 };
		matrizTransiciones[1][4] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][5] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][6] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][7] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][8] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][9] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][10] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][11] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][12] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][13] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][14] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][15] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][16] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][17] = new int[] { 1, 2, 1 };
		matrizTransiciones[1][18] = new int[] { 1, 2, 1 };

		matrizTransiciones[2][0] = new int[] { 11, 5 };
		matrizTransiciones[2][1] = new int[] { 2, 4, 1 };
		matrizTransiciones[2][2] = new int[] { 11, 5 };
		matrizTransiciones[2][3] = new int[] { 11, 5 };
		matrizTransiciones[2][4] = new int[] { 11, 5 };
		matrizTransiciones[2][5] = new int[] { 11, 5 };
		matrizTransiciones[2][6] = new int[] { 11, 5 };
		matrizTransiciones[2][7] = new int[] { 11, 5 };
		matrizTransiciones[2][8] = new int[] { 11, 5 };
		matrizTransiciones[2][9] = new int[] { 11, 5 };
		matrizTransiciones[2][10] = new int[] { 11, 5 };
		matrizTransiciones[2][11] = new int[] { 11, 5 };
		matrizTransiciones[2][12] = new int[] { 11, 5 };
		matrizTransiciones[2][13] = new int[] { 11, 5 };
		matrizTransiciones[2][14] = new int[] { 11, 5 };
		matrizTransiciones[2][15] = new int[] { 11, 5 };
		matrizTransiciones[2][16] = new int[] { 11, 5 };
		matrizTransiciones[2][17] = new int[] { 11, 5 };
		matrizTransiciones[2][18] = new int[] { 11, 5 };

		matrizTransiciones[3][0] = new int[] { 14, 9 };
		matrizTransiciones[3][1] = new int[] { 14, 9 };
		matrizTransiciones[3][2] = new int[] { 14, 9 };
		matrizTransiciones[3][3] = new int[] { 14, 9 };
		matrizTransiciones[3][4] = new int[] { 14, 9 };
		matrizTransiciones[3][5] = new int[] { 13, 1, 8 };
		matrizTransiciones[3][6] = new int[] { 14, 9 };
		matrizTransiciones[3][7] = new int[] { 14, 9 };
		matrizTransiciones[3][8] = new int[] { 14, 9 };
		matrizTransiciones[3][9] = new int[] { 14, 9 };
		matrizTransiciones[3][10] = new int[] { 14, 9 };
		matrizTransiciones[3][11] = new int[] { 14, 9 };
		matrizTransiciones[3][12] = new int[] { 14, 9 };
		matrizTransiciones[3][13] = new int[] { 14, 9 };
		matrizTransiciones[3][14] = new int[] { 14, 9 };
		matrizTransiciones[3][15] = new int[] { 14, 9 };
		matrizTransiciones[3][16] = new int[] { 14, 9 };
		matrizTransiciones[3][17] = new int[] { 14, 9 };
		matrizTransiciones[3][18] = new int[] { 14, 9 };

		matrizTransiciones[4][0] = new int[] { 16, 11 };
		matrizTransiciones[4][1] = new int[] { 16, 11 };
		matrizTransiciones[4][2] = new int[] { 16, 11 };
		matrizTransiciones[4][3] = new int[] { 16, 11 };
		matrizTransiciones[4][4] = new int[] { 16, 11 };
		matrizTransiciones[4][5] = new int[] { 15, 1, 10 };
		matrizTransiciones[4][6] = new int[] { 16, 11 };
		matrizTransiciones[4][7] = new int[] { 16, 11 };
		matrizTransiciones[4][8] = new int[] { 16, 11 };
		matrizTransiciones[4][9] = new int[] { 16, 11 };
		matrizTransiciones[4][10] = new int[] { 16, 11 };
		matrizTransiciones[4][11] = new int[] { 16, 11 };
		matrizTransiciones[4][12] = new int[] { 16, 11 };
		matrizTransiciones[4][13] = new int[] { 16, 11 };
		matrizTransiciones[4][14] = new int[] { 16, 11 };
		matrizTransiciones[4][15] = new int[] { 16, 11 };
		matrizTransiciones[4][16] = new int[] { 16, 11 };
		matrizTransiciones[4][17] = new int[] { 16, 11 };
		matrizTransiciones[4][18] = new int[] { 16, 11 };

		matrizTransiciones[5][0] = new int[] { 18, 13 };
		matrizTransiciones[5][1] = new int[] { 18, 13 };
		matrizTransiciones[5][2] = new int[] { 18, 13 };
		matrizTransiciones[5][3] = new int[] { 18, 13 };
		matrizTransiciones[5][4] = new int[] { 18, 13 };
		matrizTransiciones[5][5] = new int[] { 17, 1, 12 };
		matrizTransiciones[5][6] = new int[] { 18, 13 };
		matrizTransiciones[5][7] = new int[] { 18, 13 };
		matrizTransiciones[5][8] = new int[] { 18, 13 };
		matrizTransiciones[5][9] = new int[] { 18, 13 };
		matrizTransiciones[5][10] = new int[] { 18, 13 };
		matrizTransiciones[5][11] = new int[] { 18, 13 };
		matrizTransiciones[5][12] = new int[] { 18, 13 };
		matrizTransiciones[5][13] = new int[] { 18, 13 };
		matrizTransiciones[5][14] = new int[] { 18, 13 };
		matrizTransiciones[5][15] = new int[] { 18, 13 };
		matrizTransiciones[5][16] = new int[] { 18, 13 };
		matrizTransiciones[5][17] = new int[] { 18, 13 };
		matrizTransiciones[5][18] = new int[] { 18, 13 };

		matrizTransiciones[6][0] = new int[] { -1, 1 };
		matrizTransiciones[6][1] = new int[] { -1, 1 };
		matrizTransiciones[6][2] = new int[] { -1, 1 };
		matrizTransiciones[6][3] = new int[] { -1, 1 };
		matrizTransiciones[6][4] = new int[] { -1, 1 };
		matrizTransiciones[6][5] = new int[] { -1, 1 };
		matrizTransiciones[6][6] = new int[] { -1, 1 };
		matrizTransiciones[6][7] = new int[] { 7, 1 };
		matrizTransiciones[6][8] = new int[] { -1, 1 };
		matrizTransiciones[6][9] = new int[] { -1, 1 };
		matrizTransiciones[6][10] = new int[] { -1, 1 };
		matrizTransiciones[6][11] = new int[] { -1, 1 };
		matrizTransiciones[6][12] = new int[] { -1, 1 };
		matrizTransiciones[6][13] = new int[] { -1, 1 };
		matrizTransiciones[6][14] = new int[] { -1, 1 };
		matrizTransiciones[6][15] = new int[] { -1, 1 };
		matrizTransiciones[6][16] = new int[] { -1, 1 };
		matrizTransiciones[6][17] = new int[] { -1, 1 };
		matrizTransiciones[6][18] = new int[] { -1, 1 };

		matrizTransiciones[7][0] = new int[] { 7, 1 };
		matrizTransiciones[7][1] = new int[] { 7, 1 };
		matrizTransiciones[7][2] = new int[] { 7, 1 };
		matrizTransiciones[7][3] = new int[] { 0, 1, 22 };
		matrizTransiciones[7][4] = new int[] { 7, 1 };
		matrizTransiciones[7][5] = new int[] { 7, 1 };
		matrizTransiciones[7][6] = new int[] { 7, 1 };
		matrizTransiciones[7][7] = new int[] { 7, 1 };
		matrizTransiciones[7][8] = new int[] { 7, 1 };
		matrizTransiciones[7][9] = new int[] { 7, 1 };
		matrizTransiciones[7][10] = new int[] { 7, 1 };
		matrizTransiciones[7][11] = new int[] { 7, 1 };
		matrizTransiciones[7][12] = new int[] { 7, 1 };
		matrizTransiciones[7][13] = new int[] { 7, 1 };
		matrizTransiciones[7][14] = new int[] { 7, 1 };
		matrizTransiciones[7][15] = new int[] { 7, 1 };
		matrizTransiciones[7][16] = new int[] { 7, 1 };
		matrizTransiciones[7][17] = new int[] { 7, 1 };
		matrizTransiciones[7][18] = new int[] { 7, 1 };

		matrizTransiciones[8][0] = new int[] { 12, 7 };
		matrizTransiciones[8][1] = new int[] { 8, 2, 1 };
		matrizTransiciones[8][2] = new int[] { 8, 2, 1 };
		matrizTransiciones[8][3] = new int[] { 12, 7 };
		matrizTransiciones[8][4] = new int[] { 12, 7 };
		matrizTransiciones[8][5] = new int[] { 12, 7 };
		matrizTransiciones[8][6] = new int[] { 12, 7 };
		matrizTransiciones[8][7] = new int[] { 12, 7 };
		matrizTransiciones[8][8] = new int[] { 8, 2, 1 };
		matrizTransiciones[8][9] = new int[] { 12, 7 };
		matrizTransiciones[8][10] = new int[] { 12, 7 };
		matrizTransiciones[8][11] = new int[] { 12, 7 };
		matrizTransiciones[8][12] = new int[] { 12, 7 };
		matrizTransiciones[8][13] = new int[] { 12, 7 };
		matrizTransiciones[8][14] = new int[] { 12, 7 };
		matrizTransiciones[8][15] = new int[] { 12, 7 };
		matrizTransiciones[8][16] = new int[] { 12, 7 };
		matrizTransiciones[8][17] = new int[] { 12, 7 };
		matrizTransiciones[8][18] = new int[] { 12, 7 };

		// no hay estado 9, pero no influye

	}

	public int[] transitar(char caracter) {
		int columna = listaCaracter(caracter);
		int[] resultado = new int[matrizTransiciones[estado][columna].length];
		for (int i = 0; i < resultado.length; i++)
			resultado[i] = matrizTransiciones[estado][columna][i];
		if (resultado[0] != -1)
			estado = resultado[0];
		return resultado;
	}

	public boolean estadoFinal() {
		return estado >= estadoFinal - nEstadosFinales + 1 && estado <= estadoFinal;
	}

	private static int listaCaracter(char caracter) {
		if (caracter == '\'')
			return 0;
		for (char c = '0'; c <= '9'; c++)
			if (caracter == c)
				return 1;
		for (char c = 'a'; c <= 'z'; c++)
			if (caracter == c)
				return 2;
		for (char c = 'A'; c <= 'Z'; c++)
			if (caracter == c)
				return 2;
		if (caracter == '\n')
			return 3;
		if (caracter == '+')
			return 4;
		if (caracter == '=')
			return 5;
		if (caracter == '!')
			return 6;
		if (caracter == '/')
			return 7;
		if (caracter == '_')
			return 8;
		if (caracter == '(')
			return 9;
		if (caracter == ')')
			return 10;
		if (caracter == '{')
			return 11;
		if (caracter == '}')
			return 12;
		if (caracter == '-')
			return 13;
		if (caracter == ';')
			return 14;
		if (caracter == ':')
			return 15;
		if (caracter == ',')
			return 16;
		if (caracter == ' ' || caracter == '\r' || caracter == '\t')
			return 17; // caracter delimitador :)
		return 18; // caracter desconocido
	}
}
