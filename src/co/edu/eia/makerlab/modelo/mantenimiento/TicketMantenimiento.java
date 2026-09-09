package co.edu.eia.makerlab.modelo.mantenimiento;

import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.usuarios.Tecnico;
import co.edu.eia.makerlab.util.FechaSistema;

public class TicketMantenimiento {
	public static final String PRIORIDAD_ALTA = "ALTA";
	public static final String PRIORIDAD_MEDIA = "MEDIA";
	public static final String PRIORIDAD_BAJA = "BAJA";
	public static final String ESTADO_ABIERTO = "ABIERTO";
	public static final String ESTADO_EN_PROCESO = "EN_PROCESO";
	public static final String ESTADO_CERRADO = "CERRADO";
	private String idTicket;
	private String prioridad;
	private String descripcion;
	private EquipoLaboratorio equipo;
	private Tecnico tecnicoAsignado;
	private String estado;
	private String accionRealizada;
	private String estadoFinalEquipo;
	private String fecha;
	private String horaInicio;
	private String horaFin;
	private String[] historial;
	private int contadorHistorial;

	public TicketMantenimiento(String idTicket, String prioridad, String descripcion,
			EquipoLaboratorio equipo, Tecnico tecnicoAsignado, String fecha, String horaInicio,
			int capacidadHistorial) {
		this.idTicket = idTicket;
		this.prioridad = prioridad;
		this.descripcion = descripcion;
		this.equipo = equipo;
		this.tecnicoAsignado = tecnicoAsignado;
		this.estado = ESTADO_ABIERTO;
		this.accionRealizada = "";
		this.estadoFinalEquipo = "";
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		int duracion = (equipo != null ? equipo.getDuracionEstimadaMinutos() : 0);
		if (duracion > 0 && FechaSistema.minutosDelDia(horaInicio) >= 0) {
			int finMin = FechaSistema.minutosDelDia(horaInicio) + duracion;
			int hh = (finMin / 60) % 24;
			int mm = finMin % 60;
			this.horaFin = (hh < 10 ? "0" + hh : "" + hh) + ":" + (mm < 10 ? "0" + mm : "" + mm);
		} else {
			this.horaFin = "";
		}
		int capacidad = capacidadHistorial > 0 ? capacidadHistorial : 1;
		this.historial = new String[capacidad];
		this.contadorHistorial = 0;
		registrarCambio("Ticket creado con prioridad " + prioridad + " para el equipo "
				+ (equipo != null ? equipo.getIdEquipo() : "?"));
	}

	public String getIdTicket() {
		return idTicket;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public EquipoLaboratorio getEquipo() {
		return equipo;
	}

	public Tecnico getTecnicoAsignado() {
		return tecnicoAsignado;
	}

	public String getEstado() {
		return estado;
	}

	public String getAccionRealizada() {
		return accionRealizada;
	}

	public String getEstadoFinalEquipo() {
		return estadoFinalEquipo;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public boolean tieneFranjaEstimada() {
		return horaFin != null && horaFin.length() == 5;
	}

	public boolean estaCerrado() {
		return ESTADO_CERRADO.equals(estado);
	}

	public void marcarEnProceso() {
		if (!estaCerrado()) {
			this.estado = ESTADO_EN_PROCESO;
			registrarCambio("Ticket en proceso por el tecnico "
					+ (tecnicoAsignado != null ? tecnicoAsignado.getIdUsuario() : "?"));
		}
	}

	public void registrarAccion(String accion) {
		if (accion != null && accion.trim().length() > 0 && !estaCerrado()) {
			this.accionRealizada = accion;
			registrarCambio("Accion registrada: " + accion);
		}
	}

	public boolean cerrar(String estadoFinalEquipoValido) {
		if (estaCerrado()) {
			return false;
		}
		if (accionRealizada.length() == 0) {
			return false;
		}
		boolean estadoValido = EquipoLaboratorio.ESTADO_OPERATIVO.equals(estadoFinalEquipoValido)
				|| EquipoLaboratorio.ESTADO_FUERA_DE_SERVICIO.equals(estadoFinalEquipoValido)
				|| EquipoLaboratorio.ESTADO_INACTIVO.equals(estadoFinalEquipoValido);
		if (!estadoValido) {
			return false;
		}
		this.estadoFinalEquipo = estadoFinalEquipoValido;
		this.estado = ESTADO_CERRADO;
		if (equipo != null) {
			equipo.setEstadoEquipo(estadoFinalEquipoValido);
		}
		registrarCambio("Ticket cerrado. Equipo queda en estado " + estadoFinalEquipoValido
				+ ". Tecnico responsable: " + (tecnicoAsignado != null ? tecnicoAsignado.getIdUsuario() : "?"));
		return true;
	}

	public int getContadorHistorial() {
		return contadorHistorial;
	}

	public String getEntradaHistorial(int indice) {
		if (indice < 0 || indice >= contadorHistorial) {
			return null;
		}
		return historial[indice];
	}

	private void registrarCambio(String texto) {
		if (contadorHistorial < historial.length) {
			historial[contadorHistorial] = texto;
			contadorHistorial = contadorHistorial + 1;
		} else {
			historial[historial.length - 1] = texto;
		}
	}

	public void mostrarDatos() {
		System.out.println("TICKET  id=" + idTicket + "  prioridad=" + prioridad + "  estado=" + estado
				+ "  equipo=" + (equipo != null ? equipo.getIdEquipo() : "?")
				+ "  tecnico=" + (tecnicoAsignado != null ? tecnicoAsignado.getIdUsuario() : "?"));
		System.out.println("   descripcion=" + descripcion
				+ (tieneFranjaEstimada() ? "  franja=" + fecha + " " + horaInicio + "-" + horaFin : ""));
		if (accionRealizada.length() > 0) {
			System.out.println("   accionRealizada=" + accionRealizada
					+ "  estadoFinalEquipo=" + estadoFinalEquipo);
		}
		for (int i = 0; i < contadorHistorial; i++) {
			System.out.println("   historial[" + i + "]=" + historial[i]);
		}
	}
}
