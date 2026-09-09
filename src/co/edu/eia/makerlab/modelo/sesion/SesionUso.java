package co.edu.eia.makerlab.modelo.sesion;

import co.edu.eia.makerlab.modelo.reserva.Reserva;
import co.edu.eia.makerlab.util.FechaSistema;

public class SesionUso {
	public static final String ESTADO_ABIERTA = "ABIERTA";
	public static final String ESTADO_CERRADA = "CERRADA";
	private String idSesion;
	private Reserva reserva;
	private String horaAperturaReal;
	private String horaCierreReal;
	private String consumibles;
	private String observaciones;
	private String incidentes;
	private String estado;

	public SesionUso(String idSesion, Reserva reserva, String horaAperturaReal) {
		this.idSesion = idSesion;
		this.reserva = reserva;
		this.horaAperturaReal = horaAperturaReal;
		this.horaCierreReal = "";
		this.consumibles = "";
		this.observaciones = "";
		this.incidentes = "";
		this.estado = ESTADO_ABIERTA;
	}

	public String getIdSesion() {
		return idSesion;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public String getHoraAperturaReal() {
		return horaAperturaReal;
	}

	public String getHoraCierreReal() {
		return horaCierreReal;
	}

	public String getConsumibles() {
		return consumibles;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public String getIncidentes() {
		return incidentes;
	}

	public String getEstado() {
		return estado;
	}

	public boolean estaAbierta() {
		return ESTADO_ABIERTA.equals(estado);
	}

	public boolean cerrar(String horaCierreReal, String consumibles, String observaciones, String incidentes) {
		if (!estaAbierta()) {
			return false;
		}
		if (!FechaSistema.esHoraValida(horaCierreReal)) {
			return false;
		}
		this.horaCierreReal = horaCierreReal;
		this.consumibles = (consumibles != null ? consumibles : "");
		this.observaciones = (observaciones != null ? observaciones : "");
		this.incidentes = (incidentes != null && incidentes.trim().length() > 0 ? incidentes : "NINGUNO");
		this.estado = ESTADO_CERRADA;
		return true;
	}

	public boolean tuvoIncidente() {
		return ESTADO_CERRADA.equals(estado) && incidentes.length() > 0 && !"NINGUNO".equals(incidentes);
	}

	public int duracionMinutos() {
		if (estaAbierta()) {
			return 0;
		}
		return FechaSistema.minutosEntre(horaAperturaReal, horaCierreReal);
	}

	public void mostrarDatos() {
		System.out.println("SESION  id=" + idSesion + "  estado=" + estado
				+ "  reserva=" + (reserva != null ? reserva.getIdReserva() : "?"));
		System.out.println("   apertura=" + horaAperturaReal
				+ "  cierre=" + (horaCierreReal.length() > 0 ? horaCierreReal : "(abierta)")
				+ "  duracion=" + duracionMinutos() + " min");
		if (ESTADO_CERRADA.equals(estado)) {
			System.out.println("   consumibles=" + consumibles + "  observaciones=" + observaciones
					+ "  incidentes=" + incidentes);
		}
	}
}
