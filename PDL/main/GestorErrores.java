public class GestorErrores {
	private static String errors;

	public static void load() {
		errors = "";
	}

	public static void error(int error, int lineaActual, Object... detalles) {
		if (error < 7)
			errors += "Error L�xico";
		else if (error > 7)
			errors += "Error Sem�ntico";
		else
			errors += "Error Sint�ctico";
		errors += " / Linea: " + lineaActual + "\n- Detalles: ";
		switch (error) {
		case 0:
			errors += "Caracter inv�lido: " + detalles[0];
			break;
		case 1:
			errors += "Los comentarios deben empezar por //";
			break;
		case 2:
			errors += "Una sentencia no puede empezar por _";
			break;
		case 3:
			errors += "Un String no puede contener un salto de linea";
			break;
		case 4:
			errors += "N�mero fuera de rango: " + detalles[0];
			break;
		case 5:
			errors += "Cadena de mas de 64 caracteres: " + detalles[0];
			break;
		case 6:
			errors += "Variable ya declarada: " + detalles[0];
			break;
		case 7:
			Token token = (Token) detalles[0];
			switch (token.codigo) {
			case "identificador":
				errors += "Error en el identificador '" + TablaDeSimbolos.get((int) token.valor).lexema + "'";
				break;
			case "entero":
				errors += "Error en el numero '" + token.valor + "'";
				break;
			case "cadena":
				errors += "Error en la cadena '" + token.valor + "'";
				break;
			case "$":
				errors += "Fin de fichero inesperado";
				break;
			default:
				errors += "Error en " + token.codigo;
			}
			break;
		case 8:
			errors += "Solo se permiten expresiones l�gicas para if. Recibido: " + detalles[0];
			break;
		case 9:
			errors += "Solo se permiten variables enteras para switch. Recibido: " + detalles[0];
			break;
		case 10:
			errors += "Solo se permiten variables enteras o cadenas para input. Recibido: " + detalles[0];
			break;
		case 11:
			errors += "Sentencia return fuera del cuerpo de una funci�n";
			break;
		case 12:
			errors += "Tipo del return distinto del tipo de retorno de la funci�n. Recibido: " + detalles[0] + ". Esperado: " + detalles[1];
			break;
		case 13:
			errors += "Sentencia break fuera del cuerpo de un switch";
			break;
		case 14:
			errors += "No se pueden comparar 2 expresiones de tipos diferentes. Recibido: " + detalles[0] + " y " + detalles[1];
			break;
		case 15:
			errors += "Las operaciones aritm�ticas solo se permiten entre expresiones enteras. Recibido: " + detalles[0];
			break;
		case 16:
			errors += "Las operaciones l�gicas solo se permiten entre expresiones l�gicas. Recibido: " + detalles[0];
			break;
		case 17:
			errors += "N�mero de par�metros de la funci�n incorrectos. Recibido: " + detalles[0] + ". Esperado: " + detalles[1];
			break;
		case 18:
			errors += "Tipo de los par�metros de la funci�n incorrectos. Recibido: " + detalles[0] + ". Esperado: " + detalles[1];
			break;
		case 19:
			errors += "No se pueden llamar a funciones que retornen vacio en una expresi�n: Funci�n: " + detalles[0];
			break;
		case 20:
			errors += "Tipo de la asignaci�n y la variable diferentes. Recibido: " + detalles[0] + ". Esperado: " + detalles[1];
			break;
		case 21:
			errors += "La variable no es una funci�n: Variable: " + detalles[0];
			break;
		case 22:
			errors += "A la funci�n le falta la lista de par�metros: Funci�n: " + detalles[0];
			break;
		case 23:
			errors += "No se puede asignar un valor a una funci�n: Funci�n: " + detalles[0];
			break;
		}
		errors += "\n\n";
	}

	public static String getErrors() {
		return errors;
	}
}
