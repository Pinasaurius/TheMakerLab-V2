package co.edu.eia.makerlab.modelo.reserva;

import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.usuarios.Usuario;
import co.edu.eia.makerlab.util.FechaSistema;

public class Reserva {
	public static final String ESTADO_PENDIENTE = "PENDIENTE";
	public static final String ESTADO_CONFIRMADA = "CONFIRMADA";
	public static final String ESTADO_RECHAZADA = "RECHAZADA";
	public static final String ESTADO_EN_ESPERA = "EN_ESPERA";
	public static final String ESTADO_CANCELADA = "CANCELADA";
	private String idReserva;
	private String fechaReserva;
	private String horaInicio;
	private String horaFinalizacion;
	private String proposito;
	private String estado;
	private String motivoRechazo;
	private Usuario usuario;
	private EquipoLaboratorio equipo;

	public Reserva(String idReserva, String fechaReserva, String horaInicio, String horaFinalizacion,
			String proposito, Usuario usuario, EquipoLaboratorio equipo) {
		this.idReserva = idReserva;
		this.fechaReserva = fechaReserva;
		this.horaInicio = horaInicio;
		this.horaFinalizacion = horaFinalizacion;
		this.proposito = proposito;
		this.usuario = usuario;
		this.equipo = equipo;
		this.estado = ESTADO_PENDIENTE;
		this.motivoRechazo = "";
	}

	public String getIdReserva() {
		return idReserva;
	}

	public String getFechaReserva() {
		return fechaReserva;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public String getHoraFinalizacion() {
		return horaFinalizacion;
	}

	public String getProposito() {
		return proposito;
	}

	public String getEstado() {
		return estado;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public EquipoLaboratorio getEquipo() {
		return equipo;
	}

	public void setFechaReserva(String fechaReserva) {
		if (FechaSistema.esFechaValida(fechaReserva)) {
			this.fechaReserva = fechaReserva;
		}
	}

	public void setHoraInicio(String horaInicio) {
		if (FechaSistema.esHoraValida(horaInicio)) {
			this.horaInicio = horaInicio;
		}
	}

	public void setHoraFinalizacion(String horaFinalizacion) {
		if (FechaSistema.esHoraValida(horaFinalizacion)) {
			this.horaFinalizacion = horaFinalizacion;
		}
	}

	public void setProposito(String proposito) {
		if (proposito != null && proposito.trim().length() > 0) {
			this.proposito = proposito;
		}
	}

	public void setUsuario(Usuario usuario) {
		if (usuario != null) {
			this.usuario = usuario;
		}
	}

	public void setEquipo(EquipoLaboratorio equipo) {
		if (equipo != null) {
			this.equipo = equipo;
		}
	}

	public void confirmar() {
		this.estado = ESTADO_CONFIRMADA;
		this.motivoRechazo = "";
	}

	public void rechazar(String motivo) {
		this.estado = ESTADO_RECHAZADA;
		this.motivoRechazo = (motivo != null ? motivo : "sin especificar");
	}

	public void ponerEnEspera() {
		this.estado = ESTADO_EN_ESPERA;
	}

	public void cancelar(String motivo) {
		this.estado = ESTADO_CANCELADA;
		this.motivoRechazo = (motivo != null ? motivo : "cancelada por el sistema");
	}

	public boolean estaConfirmada() {
		return ESTADO_CONFIRMADA.equals(estado);
	}

	public boolean seCruzaCon(Reserva otra) {
		if (otra == null || this.equipo == null || otra.equipo == null) {
			return false;
		}
		boolean mismoEquipo = this.equipo.getIdEquipo().equals(otra.equipo.getIdEquipo());
		boolean mismaFecha = this.fechaReserva.equals(otra.fechaReserva);
		if (!mismoEquipo || !mismaFecha) {
			return false;
		}
		return FechaSistema.hayCruceHorario(this.horaInicio, this.horaFinalizacion,
				otra.horaInicio, otra.horaFinalizacion);
	}

	public void mostrarDatos() {
		System.out.println("RESERVA  id=" + idReserva + "  estado=" + estado
				+ "  usuario=" + (usuario != null ? usuario.getIdUsuario() : "?")
				+ "  equipo=" + (equipo != null ? equipo.getIdEquipo() : "?"));
		System.out.println("   fecha=" + fechaReserva + "  " + horaInicio + " a " + horaFinalizacion
				+ "  proposito=" + proposito
				+ (motivoRechazo.length() > 0 ? "  motivo=" + motivoRechazo : ""));
	}
}
