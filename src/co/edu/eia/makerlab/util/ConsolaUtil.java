package co.edu.eia.makerlab.util;

import java.util.Scanner;

public final class ConsolaUtil {
	private static final Scanner ENTRADA = new Scanner(System.in);

	private ConsolaUtil() {
	}

	public static int leerCapacidad(String mensaje, int valorPorDefecto, int minimo) {
		while (true) {
			System.out.print(mensaje + " (minimo " + minimo + "): ");
			if (!ENTRADA.hasNextLine()) {
				System.out.println();
				System.out.println("   (Sin entrada por teclado: se usa capacidad por defecto n = "
						+ valorPorDefecto + ")");
				return valorPorDefecto;
			}
			String linea = ENTRADA.nextLine().trim();
			int valor = aEnteroPositivo(linea);
			if (valor >= minimo) {
				return valor;
			}
			System.out.println("   Valor invalido. Escriba un numero entero mayor o igual a " + minimo + ".");
		}
	}

	public static String leerTexto(String mensaje, String valorPorDefecto) {
		System.out.print(mensaje + ": ");
		if (!ENTRADA.hasNextLine()) {
			System.out.println(valorPorDefecto + "  (valor por defecto)");
			return valorPorDefecto;
		}
		String linea = ENTRADA.nextLine().trim();
		if (linea.length() == 0) {
			return valorPorDefecto;
		}
		return linea;
	}

	public static void separador() {
		System.out.println("------------------------------------------------------------");
	}

	public static void titulo(String texto) {
		separador();
		System.out.println("  " + texto.toUpperCase());
		separador();
	}

	private static int aEnteroPositivo(String texto) {
		if (texto == null || texto.length() == 0) {
			return -1;
		}
		int resultado = 0;
		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);
			if (c < '0' || c > '9') {
				return -1;
			}
			resultado = resultado * 10 + (c - '0');
		}
		return resultado;
	}
}
