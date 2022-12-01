public class GestorErrores {
	private static String errors;

	public static void load() {
		errors = "";
	}

	public static void error(int error, int lineaActual, Object... detalles) {
		if (error < 7)
			errors += "Error Léxico";
		else if (error > 7)
			errors += "Error Semántico";
		else
			errors += "Error Sintáctico";
		errors += " / Linea: " + lineaActual + "\n- Detalles: ";
		switch (error) {
		case 0:
			errors += "Caracter inválido: " + detalles[0];
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
			errors += "Número fuera de rango: " + detalles[0];
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
			errors += "Solo se permiten expresiones lógicas para if. Recibido: " + detalles[0];
			break;
		case 9:
			errors += "Solo se permiten variables enteras para switch. Recibido: " + detalles[0];
			break;
		case 10:
			errors += "Solo se permiten variables enteras o cadenas para input. Recibido: " + detalles[0];
			break;
		case 11:
			errors += "Sentencia return fuera del cuerpo de una función";
			break;
		case 12:
			errors += "Tipo del return distinto del tipo de retorno de la función. Recibido: " + detalles[0] + ". Esperado: " + detalles[1];
			break;
		case 13:
			errors += "Sentencia break fuera del cuerpo de un switch";
			break;
		case 14:
			errors += "No se pueden comparar 2 expresiones de tipos diferentes. Recibido: " + detalles[0] + " y " + detalles[1];
			break;
		case 15:
			errors += "Las operaciones aritméticas solo se permiten entre expresiones enteras. Recibido: " + detalles[0];
			break;
		case 16:
			errors += "Las operaciones lógicas solo se permiten entre expresiones lógicas. Recibido: " + detalles[0];
			break;
		case 17:
			errors += "Número de parámetros de la función incorrectos. Recibido: " + detalles[0] + ". Esperado: " + detalles[1];
			break;
		case 18:
			errors += "Tipo de los parámetros de la función incorrectos. Recibido: " + detalles[0] + ". Esperado: " + detalles[1];
			break;
		case 19:
			errors += "No se pueden llamar a funciones que retornen vacio en una expresión: Función: " + detalles[0];
			break;
		case 20:
			errors += "Tipo de la asignación y la variable diferentes. Recibido: " + detalles[0] + ". Esperado: " + detalles[1];
			break;
		case 21:
			errors += "La variable no es una función: Variable: " + detalles[0];
			break;
		case 22:
			errors += "A la función le falta la lista de parámetros: Función: " + detalles[0];
			break;
		case 23:
			errors += "No se puede asignar un valor a una función: Función: " + detalles[0];
			break;
		}
		errors += "\n\n";
	}

	public static String getErrors() {
		return errors;
	}
}
