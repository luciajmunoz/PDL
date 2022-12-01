import java.util.Stack;

public class AnalizadorSintactico {
	private static Stack<Pair> pila; // pila para el analizador sintactico
	private static Stack<Pair> pilaux;
	private static String parse;
	private static boolean codigo_ok;

	public static void init() {
		TablaAnalisis.init();
		pila = new Stack<>();
		pilaux = new Stack<>();
	}

	public static void generarParse() {
		pila.clear();
		pilaux.clear();
		pila.push(new Pair("$", new Atributos()));
		pila.push(new Pair("P'", new Atributos())); // ponemos el axioma en la cima
		parse = "D";
		codigo_ok = true;
		Token sig_token = AnalizadorLexico.generarToken();
		Pair cima = pila.peek();
		do {
			if (TablaAnalisis.isTerminal(cima.simbolo)) {
				if (TablaAnalisis.equiparar(sig_token, cima.simbolo)) {
					if (sig_token.codigo.equals("identificador"))
						cima.atribs.posTS = (int) sig_token.valor;
					pilaux.push(pila.pop());
					sig_token = AnalizadorLexico.generarToken();
				} else {
					GestorErrores.error(7, AnalizadorLexico.getLineaActual(), sig_token);
					codigo_ok = false;
				}
			} else if (TablaAnalisis.isNoTerminal(cima.simbolo)) {
				int numeroProduccion = TablaAnalisis.buscarRegla(cima.simbolo, sig_token);
				if (numeroProduccion != -1) {// hay una regla
					parse += " " + (numeroProduccion + 1); // la escribimos en el parse
					pilaux.push(pila.pop()); // meter de la pila normal a la aux
					String[] regla = TablaAnalisis.regla(numeroProduccion); // obtengo la produccion
					for (int i = regla.length - 1; i >= 0; i--) {
						if (!regla[i].equals("lambda")) {
							pila.push(new Pair(regla[i], new Atributos())); // metemos los simbolos al reves
						}
					}
				} else {
					GestorErrores.error(7, AnalizadorLexico.getLineaActual(), sig_token);
					codigo_ok = false;
				}
			} else {
				accionSemantica(Integer.parseInt(pila.pop().simbolo));
			}
			cima = pila.peek();
		} while (!cima.simbolo.equals("$") && codigo_ok);
		if (!TablaAnalisis.equiparar(sig_token, cima.simbolo) && codigo_ok) {
			GestorErrores.error(7, AnalizadorLexico.getLineaActual(), sig_token);
		}
	}

	public static String getParse() {
		return parse;
	}

	public static void accionSemantica(int accion) {
		switch (accion) {
		case 1: // P’->[1] P [2]
			TablaDeSimbolos.crearTSGlobal();
			break;
		case 2:
			TablaDeSimbolos.destruirTSGlobal();
			peekAux(1).tipo = peekAux(0).tipo;
			pilaux.pop();
			break;
		case 3: // P-> B P [3]
		case 4: // P-> F P [4]
			if (peekAux(0).tipo.equals("tipo_ok") && peekAux(1).tipo.equals("tipo_ok")) {
				peekAux(2).tipo = "tipo_ok";
			} else {
				peekAux(2).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			break;
		case 5: // P-> lambda [5]
		case 13: // W->lambda [13]
		case 29: // C-> lambda [29]
			peekAux(0).tipo = "tipo_ok";
			break;
		case 6: // B-> [6] let T id [65] ; [7]
			TablaDeSimbolos.zona_decl = true;
			break;
		case 65:
			TablaDeSimbolos.zona_decl = false;
			break;
		case 7:
			TablaDeSimbolos.setTipo(peekAux(1).posTS, peekAux(2).tipo);
			TablaDeSimbolos.setDespl(peekAux(1).posTS, peekAux(2).ancho);
			peekAux(4).tipo = "tipo_ok";
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 61: // B-> if ( E ) [61] S [8]
			peekPila(0).tipoRetorno = peekAux(4).tipoRetorno;
			peekPila(0).enSwitch = peekAux(4).enSwitch;
			break;
		case 8:
			if (peekAux(2).tipo.equals("logico")) {
				peekAux(5).tipo = peekAux(0).tipo;
			} else {
				if (!peekAux(2).tipo.equals("tipo_error"))
					GestorErrores.error(8, AnalizadorLexico.getLineaActual(), peekAux(2).tipo);
				peekAux(5).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 9: // B->[9] S [10]
			peekPila(0).tipoRetorno = peekAux(0).tipoRetorno;
			peekPila(0).enSwitch = peekAux(0).enSwitch;
			break;
		case 10:
			peekAux(1).tipo = peekAux(0).tipo;
			pilaux.pop();
			break;
		case 62: // B->switch ( id ) [62] { W } [11]
			peekPila(1).tipoRetorno = peekAux(4).tipoRetorno;
			peekPila(1).enSwitch = true;
			break;
		case 11:
			if (TablaDeSimbolos.get(peekAux(4).posTS).tipo.equals("entero")) {
				peekAux(7).tipo = peekAux(1).tipo;
			} else {
				GestorErrores.error(9, AnalizadorLexico.getLineaActual(), TablaDeSimbolos.get(peekAux(4).posTS).tipo);
				peekAux(7).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 63: // W->case D : [63] C W [12]
			peekPila(0).tipoRetorno = peekAux(3).tipoRetorno;
			peekPila(0).enSwitch = peekAux(3).enSwitch;
			peekPila(1).tipoRetorno = peekAux(3).tipoRetorno;
			peekPila(1).enSwitch = peekAux(3).enSwitch;
			break;
		case 12:
			if (peekAux(1).tipo.equals("tipo_ok")) {
				peekAux(5).tipo = peekAux(0).tipo;
			} else {
				peekAux(5).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 64: // W->default : [64] C W [60]
			peekPila(0).tipoRetorno = peekAux(2).tipoRetorno;
			peekPila(0).enSwitch = peekAux(2).enSwitch;
			peekPila(1).tipoRetorno = peekAux(2).tipoRetorno;
			peekPila(1).enSwitch = peekAux(2).enSwitch;
			break;
		case 60:
			if (peekAux(1).tipo.equals("tipo_ok")) {
				peekAux(4).tipo = peekAux(0).tipo;
			} else {
				peekAux(4).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 67: // D -> entero [67]
			pilaux.pop();
			break;
		case 68: // D -> - entero [68]
			pilaux.pop();
			pilaux.pop();
			break;
		case 14: // T->int [14]
			peekAux(1).tipo = "entero";
			peekAux(1).ancho = 1;
			pilaux.pop();
			break;
		case 15: // T->boolean [15]
			peekAux(1).tipo = "logico";
			peekAux(1).ancho = 1;
			pilaux.pop();
			break;
		case 16: // T->string [16]
			peekAux(1).tipo = "cadena";
			peekAux(1).ancho = 64;
			pilaux.pop();
			break;
		case 17: // S->id S’ [17]
			if (!peekAux(0).tipo.equals("tipo_error")) {
				if (TablaDeSimbolos.get(peekAux(1).posTS).tipo.equals(peekAux(0).tipo)) {
					boolean tipo_ok = true;
					if (peekAux(0).tipo.equals("funcion")) {
						if (peekAux(0).params.length != TablaDeSimbolos.get(peekAux(1).posTS).params) {
							tipo_ok = false;
							GestorErrores.error(17, AnalizadorLexico.getLineaActual(), peekAux(0).params.length,
									TablaDeSimbolos.get(peekAux(1).posTS).params);
						}
						for (int n = 0; n < peekAux(0).params.length && tipo_ok; n++) {
							if (!peekAux(0).params[n].equals(TablaDeSimbolos.get(peekAux(1).posTS).tipoParam[n])) {
								tipo_ok = false;
								if (!peekAux(0).params[n].equals("tipo_error"))
									GestorErrores.error(18, AnalizadorLexico.getLineaActual(), peekAux(0).params[n],
											TablaDeSimbolos.get(peekAux(1).posTS).tipoParam[n]);
							}
						}
					}
					if (tipo_ok) {
						peekAux(2).tipo = "tipo_ok";
					} else {
						peekAux(2).tipo = "tipo_error";
					}
				} else {
					if (peekAux(0).tipo.equals("funcion")) {
						GestorErrores.error(21, AnalizadorLexico.getLineaActual(),
								TablaDeSimbolos.get(peekAux(1).posTS).lexema);
					} else if (TablaDeSimbolos.get(peekAux(1).posTS).tipo.equals("funcion")) {
						GestorErrores.error(23, AnalizadorLexico.getLineaActual(),
								TablaDeSimbolos.get(peekAux(1).posTS).lexema);
					} else {
						GestorErrores.error(20, AnalizadorLexico.getLineaActual(), peekAux(0).tipo,
								TablaDeSimbolos.get(peekAux(1).posTS).tipo);
					}
					peekAux(2).tipo = "tipo_error";
				}
			} else {
				peekAux(2).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			break;
		case 18: // S->print ( E ) ; [18]
			if (!peekAux(2).tipo.equals("tipo_error")) {
				peekAux(5).tipo = "tipo_ok";
			} else
				peekAux(5).tipo = "tipo_error"; // no hace falta notificar el error, ya lo hemos notificado antes
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 19: // S->input ( id ) ; [19]
			if (TablaDeSimbolos.get(peekAux(2).posTS).tipo.equals("entero")
					|| TablaDeSimbolos.get(peekAux(2).posTS).tipo.equals("cadena")) {
				peekAux(5).tipo = "tipo_ok";
			} else {
				GestorErrores.error(10, AnalizadorLexico.getLineaActual(), TablaDeSimbolos.get(peekAux(2).posTS).tipo);
				peekAux(5).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 20: // S->return X ; [20]
			if (peekAux(3).tipoRetorno == null) {
				GestorErrores.error(11, AnalizadorLexico.getLineaActual());
				peekAux(3).tipo = "tipo_error";
			} else if (peekAux(1).tipo.equals(peekAux(3).tipoRetorno)) {
				peekAux(3).tipo = "tipo_ok";
			} else {
				if (!peekAux(1).tipo.equals("tipo_error"))
					GestorErrores.error(12, AnalizadorLexico.getLineaActual(), peekAux(1).tipo, peekAux(3).tipoRetorno);
				peekAux(3).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 21: // S->break ;[21]
			if (peekAux(2).enSwitch) {
				peekAux(2).tipo = "tipo_ok";
			} else {
				GestorErrores.error(13, AnalizadorLexico.getLineaActual());
				peekAux(2).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			break;
		case 22: // S’-> += E ;[22]
			if (peekAux(1).tipo.equals("entero")) {
				peekAux(3).tipo = "entero";
			} else {
				if (!peekAux(1).tipo.equals("tipo_error"))
					GestorErrores.error(15, AnalizadorLexico.getLineaActual(), peekAux(1).tipo);
				peekAux(3).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 23: // S’-> = E ; [23]
			peekAux(3).tipo = peekAux(1).tipo;
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 24: // S’-> ( L ) ;[24]
			peekAux(4).tipo = "funcion";
			peekAux(4).params = peekAux(2).params;
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 25: // X-> E [25]
			peekAux(1).tipo = peekAux(0).tipo;
			pilaux.pop();
			break;
		case 26: // X-> lambda [26]
		case 39: // H-> lambda [39]
		case 46: // E’-> lambda [46]
		case 49: // R’-> lambda [49]
		case 59: // V’-> lambda [59]
			peekAux(0).tipo = "vacio";
			break;
		case 27: // C-> [27]B C [28]
			peekPila(0).tipoRetorno = peekAux(0).tipoRetorno;
			peekPila(0).enSwitch = peekAux(0).enSwitch;
			peekPila(1).tipoRetorno = peekAux(0).tipoRetorno;
			peekPila(1).enSwitch = peekAux(0).enSwitch;
			break;
		case 28:
			if (peekAux(1).tipo.equals("tipo_ok")) {
				peekAux(2).tipo = peekAux(0).tipo;
			} else
				peekAux(2).tipo = "tipo_error";
			pilaux.pop();
			pilaux.pop();
			break;
		case 30: // L-> EQ [30]
			peekAux(2).params = new String[peekAux(0).params.length + 1];
			for (int i = 0; i < peekAux(0).params.length; i++)
				peekAux(2).params[i + 1] = peekAux(0).params[i];
			peekAux(2).params[0] = peekAux(1).tipo;
			pilaux.pop();
			pilaux.pop();
			break;
		case 31: // L-> lambda [31]
		case 33: // Q-> lambda [33]
		case 41: // A-> lambda [41]
		case 43: // K->lambda [43]
			peekAux(0).params = new String[0];
			break;
		case 32: // Q-> , EQ[32]
			peekAux(3).params = new String[peekAux(0).params.length + 1];
			for (int i = 0; i < peekAux(0).params.length; i++)
				peekAux(3).params[i + 1] = peekAux(0).params[i];
			peekAux(3).params[0] = peekAux(1).tipo;
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 34: // F-> [6] function id H [34] ( A ) [65] [35] { C [36] } [37]
			TablaDeSimbolos.crearTSLocal();
			break;
		case 35:
			TablaDeSimbolos.setTipoFunc(peekAux(4).posTS, peekAux(3).tipo, peekAux(1).params);
			peekPila(1).tipoRetorno = peekAux(3).tipo;
			break;
		case 36:
			TablaDeSimbolos.destruirTSLocal();
			break;
		case 37:
			peekAux(9).tipo = peekAux(1).tipo;
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 38: // H-> T [38]
		case 52: // U->V[52]
			peekAux(1).tipo = peekAux(0).tipo;
			pilaux.pop();
			break;
		case 66: // A-> T id [66] K [40]
			TablaDeSimbolos.setTipo(peekAux(0).posTS, peekAux(1).tipo);
			TablaDeSimbolos.setDespl(peekAux(0).posTS, peekAux(1).ancho);
			break;
		case 40:
			peekAux(3).params = new String[peekAux(0).params.length + 1];
			for (int i = 0; i < peekAux(0).params.length; i++)
				peekAux(3).params[i + 1] = peekAux(0).params[i];
			peekAux(3).params[0] = peekAux(2).tipo;
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 42: // K-> , T id [66] K [42]
			peekAux(4).params = new String[peekAux(0).params.length + 1];
			for (int i = 0; i < peekAux(0).params.length; i++)
				peekAux(4).params[i + 1] = peekAux(0).params[i];
			peekAux(4).params[0] = peekAux(2).tipo;
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 44: // E-> RE’ [44]
			if (peekAux(0).tipo.equals("vacio")) {
				peekAux(2).tipo = peekAux(1).tipo;
			} else if (peekAux(0).tipo.equals("tipo_error") || peekAux(1).tipo.equals("tipo_error")) {
				peekAux(2).tipo = "tipo_error";
			} else if (peekAux(0).tipo.equals(peekAux(1).tipo)) {
				peekAux(2).tipo = "logico";
			} else {
				GestorErrores.error(14, AnalizadorLexico.getLineaActual(), peekAux(1).tipo, peekAux(0).tipo);
				peekAux(2).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			break;
		case 45: // E’-> ==R [45] // E’-> != R [45]
			peekAux(2).tipo = peekAux(0).tipo;
			pilaux.pop();
			pilaux.pop();
			break;
		case 47: // R-> U R’ [47]
			if (peekAux(0).tipo.equals("vacio")) {
				peekAux(2).tipo = peekAux(1).tipo;
			} else if (peekAux(0).tipo.equals("tipo_error") || peekAux(1).tipo.equals("tipo_error")) {
				peekAux(2).tipo = "tipo_error";
			} else if (peekAux(0).tipo.equals("entero") && peekAux(1).tipo.equals("entero")) {
				peekAux(2).tipo = "entero";
			} else {
				GestorErrores.error(15, AnalizadorLexico.getLineaActual(), peekAux(1).tipo);
				peekAux(2).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			break;
		case 48: // R’-> + U R’ [48] // R’-> - U R’ [48]
			if (peekAux(1).tipo.equals("entero")) {
				if (peekAux(0).tipo.equals("vacio"))
					peekAux(3).tipo = "entero";
				else
					peekAux(3).tipo = peekAux(0).tipo;
			} else {
				if (!peekAux(1).tipo.equals("tipo_error"))
					GestorErrores.error(15, AnalizadorLexico.getLineaActual(), peekAux(1).tipo);
				peekAux(3).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 50: // U-> ! V [50]
			if (peekAux(0).tipo.equals("logico")) {
				peekAux(2).tipo = "logico";
			} else {
				if (!peekAux(0).tipo.equals("tipo_error"))
					GestorErrores.error(16, AnalizadorLexico.getLineaActual(), peekAux(0).tipo);
				peekAux(2).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			break;
		case 51: // U-> - V [51]
			if (peekAux(0).tipo.equals("entero")) {
				peekAux(2).tipo = "entero";
			} else {
				if (!peekAux(0).tipo.equals("tipo_error"))
					GestorErrores.error(15, AnalizadorLexico.getLineaActual(), peekAux(0).tipo);
				peekAux(2).tipo = "tipo_error";
			}
			pilaux.pop();
			pilaux.pop();
			break;
		case 53: // V-> id V’ [53]
			if (TablaDeSimbolos.get(peekAux(1).posTS).tipo.equals("funcion")) {
				if (peekAux(0).tipo.equals("funcion")) {
					boolean tipo_ok = true;
					if (peekAux(0).params.length != TablaDeSimbolos.get(peekAux(1).posTS).params) {
						tipo_ok = false;
						GestorErrores.error(17, AnalizadorLexico.getLineaActual(), peekAux(0).params.length,
								TablaDeSimbolos.get(peekAux(1).posTS).params);
					}
					for (int n = 0; n < peekAux(0).params.length && tipo_ok; n++) {
						if (!peekAux(0).params[n].equals(TablaDeSimbolos.get(peekAux(1).posTS).tipoParam[n])) {
							tipo_ok = false;
							if (!peekAux(0).params[n].equals("tipo_error"))
								GestorErrores.error(18, AnalizadorLexico.getLineaActual(), peekAux(0).params[n],
										TablaDeSimbolos.get(peekAux(1).posTS).tipoParam[n]);
						}
					}
					if (tipo_ok) {
						if (TablaDeSimbolos.get(peekAux(1).posTS).tipoRet.equals("vacio")) {
							GestorErrores.error(19, AnalizadorLexico.getLineaActual(),
									TablaDeSimbolos.get(peekAux(1).posTS).lexema);
							peekAux(2).tipo = "tipo_error";
						} else {
							peekAux(2).tipo = TablaDeSimbolos.get(peekAux(1).posTS).tipoRet;
						}
					} else {
						peekAux(2).tipo = "tipo_error";
					}
				} else {
					GestorErrores.error(22, AnalizadorLexico.getLineaActual(),
							TablaDeSimbolos.get(peekAux(1).posTS).lexema);
					peekAux(2).tipo = "tipo_error";
				}
			} else {
				if (peekAux(0).tipo.equals("funcion")) {
					GestorErrores.error(21, AnalizadorLexico.getLineaActual(),
							TablaDeSimbolos.get(peekAux(1).posTS).lexema);
					peekAux(2).tipo = "tipo_error";
				} else {
					peekAux(2).tipo = TablaDeSimbolos.get(peekAux(1).posTS).tipo;
				}
			}
			pilaux.pop();
			pilaux.pop();
			break;
		case 54: // V-> ( E ) [54]
			peekAux(3).tipo = peekAux(1).tipo;
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		case 55: // V->entero [55]
			peekAux(1).tipo = "entero";
			pilaux.pop();
			break;
		case 56: // V->cadena [56]
			peekAux(1).tipo = "cadena";
			pilaux.pop();
			break;
		case 57: // V->true [57] // V-> false [57]
			peekAux(1).tipo = "logico";
			pilaux.pop();
			break;
		case 58: // V’-> ( L ) [58]
			peekAux(3).tipo = "funcion";
			peekAux(3).params = peekAux(1).params;
			pilaux.pop();
			pilaux.pop();
			pilaux.pop();
			break;
		}
	}

	private static Atributos peekPila(int n) {
		return peek(pila, n);
	}

	private static Atributos peekAux(int n) {
		return peek(pilaux, n);
	}

	private static Atributos peek(Stack<Pair> pila, int n) {
		return pila.get(pila.size() - 1 - n).atribs;
	}
}
