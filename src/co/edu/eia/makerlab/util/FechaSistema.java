package co.edu.eia.makerlab.util;

public final class FechaSistema {
	private static String hoy = "2026-09-05";

	private FechaSistema() {
	}

	public static String hoy() {
		return hoy;
	}

	public static void setHoy(String nuevaFechaIso) {
		if (esFechaValida(nuevaFechaIso)) {
			hoy = nuevaFechaIso;
		}
	}

	public static boolean esFechaValida(String fechaIso) {
		if (fechaIso == null || fechaIso.length() != 10) {
			return false;
		}
		if (fechaIso.charAt(4) != '-' || fechaIso.charAt(7) != '-') {
			return false;
		}
		int anio = enteroDe(fechaIso.substring(0, 4));
		int mes = enteroDe(fechaIso.substring(5, 7));
		int dia = enteroDe(fechaIso.substring(8, 10));
		if (anio < 0 || mes < 1 || mes > 12 || dia < 1 || dia > 31) {
			return false;
		}
		return true;
	}

	public static boolean esHoraValida(String hora) {
		if (hora == null || hora.length() != 5 || hora.charAt(2) != ':') {
			return false;
		}
		int h = enteroDe(hora.substring(0, 2));
		int m = enteroDe(hora.substring(3, 5));
		return h >= 0 && h <= 23 && m >= 0 && m <= 59;
	}

	public static int minutosDelDia(String hora) {
		if (!esHoraValida(hora)) {
			return -1;
		}
		int h = enteroDe(hora.substring(0, 2));
		int m = enteroDe(hora.substring(3, 5));
		return h * 60 + m;
	}

	public static boolean hayCruceHorario(String inicioA, String finA, String inicioB, String finB) {
		int ia = minutosDelDia(inicioA);
		int fa = minutosDelDia(finA);
		int ib = minutosDelDia(inicioB);
		int fb = minutosDelDia(finB);
		if (ia < 0 || fa < 0 || ib < 0 || fb < 0) {
			return true;
		}
		return ia < fb && ib < fa;
	}

	public static boolean dentroDelHorario(String horarioLaboratorio, String inicio, String fin) {
		if (horarioLaboratorio == null || horarioLaboratorio.length() != 11
				|| horarioLaboratorio.charAt(5) != '-') {
			return false;
		}
		int labIni = minutosDelDia(horarioLaboratorio.substring(0, 5));
		int labFin = minutosDelDia(horarioLaboratorio.substring(6, 11));
		int resIni = minutosDelDia(inicio);
		int resFin = minutosDelDia(fin);
		if (labIni < 0 || labFin < 0 || resIni < 0 || resFin < 0) {
			return false;
		}
		return resIni >= labIni && resFin <= labFin;
	}

	public static long diasEntre(String desde, String hasta) {
		return aDiaEpoch(hasta) - aDiaEpoch(desde);
	}

	public static int minutosEntre(String desde, String hasta) {
		int d = minutosDelDia(desde);
		int h = minutosDelDia(hasta);
		if (d < 0 || h < 0) {
			return 0;
		}
		return h - d;
	}

	private static long aDiaEpoch(String fechaIso) {
		int y = enteroDe(fechaIso.substring(0, 4));
		int m = enteroDe(fechaIso.substring(5, 7));
		int d = enteroDe(fechaIso.substring(8, 10));
		long yy = y;
		if (m <= 2) {
			yy = yy - 1;
		}
		long era = (yy >= 0 ? yy : yy - 399) / 400;
		long yoe = yy - era * 400;
		long mp = (m + (m > 2 ? -3 : 9));
		long doy = (153 * mp + 2) / 5 + d - 1;
		long doe = yoe * 365 + yoe / 4 - yoe / 100 + doy;
		return era * 146097 + doe - 719468;
	}

	private static int enteroDe(String texto) {
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
