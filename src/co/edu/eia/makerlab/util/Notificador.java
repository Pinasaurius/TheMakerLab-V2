package co.edu.eia.makerlab.util;

public final class Notificador {
	private static int totalNotificaciones = 0;

	private Notificador() {
	}

	public static void enviar(String destinatario, String mensaje) {
		totalNotificaciones = totalNotificaciones + 1;
		System.out.println("   [NOTIFICACION -> " + destinatario + "] " + mensaje);
	}

	public static int getTotalNotificaciones() {
		return totalNotificaciones;
	}

	public static void reiniciarContador() {
		totalNotificaciones = 0;
	}
}
