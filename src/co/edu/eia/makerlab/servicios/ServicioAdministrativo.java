package co.edu.eia.makerlab.servicios;

import co.edu.eia.makerlab.modelo.certificacion.Certificacion;
import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.laboratorio.Laboratorio;
import co.edu.eia.makerlab.modelo.mantenimiento.TicketMantenimiento;
import co.edu.eia.makerlab.modelo.reserva.Reserva;
import co.edu.eia.makerlab.modelo.sesion.SesionUso;
import co.edu.eia.makerlab.modelo.usuarios.Tecnico;
import co.edu.eia.makerlab.modelo.usuarios.Usuario;
import co.edu.eia.makerlab.util.FechaSistema;
import co.edu.eia.makerlab.util.Notificador;
import co.edu.eia.makerlab.validadores.ValidadorCertificacionUsuario;
import co.edu.eia.makerlab.validadores.ValidadorCertificacionUsuarioBasico;
import co.edu.eia.makerlab.validadores.ValidadorDisponibilidadEquipoLaboratorio;
import co.edu.eia.makerlab.validadores.ValidadorReservaEquipoLaboratorio;

public class ServicioAdministrativo {
	private int capacidadMaxima;
	private Usuario[] usuarios;
	private int contadorUsuarios;
	private Laboratorio[] laboratorios;
	private int contadorLaboratorios;
	private Certificacion[] certificaciones;
	private int contadorCertificaciones;
	private Reserva[] reservas;
	private int contadorReservas;
	private Reserva[] listaEspera;
	private int contadorEspera;
	private SesionUso[] sesiones;
	private int contadorSesiones;
	private TicketMantenimiento[] tickets;
	private int contadorTickets;
	private ValidadorReservaEquipoLaboratorio verificadorDisponibilidad;
	private ValidadorCertificacionUsuario verificadorCertificacion;

	public ServicioAdministrativo(int capacidadN) {
		this(capacidadN, new ValidadorDisponibilidadEquipoLaboratorio(),
				new ValidadorCertificacionUsuarioBasico());
	}

	public ServicioAdministrativo(int capacidadN,
			ValidadorReservaEquipoLaboratorio verificadorDisponibilidad,
			ValidadorCertificacionUsuario verificadorCertificacion) {
		this.capacidadMaxima = capacidadN > 0 ? capacidadN : 1;
		this.usuarios = new Usuario[capacidadMaxima];
		this.contadorUsuarios = 0;
		this.laboratorios = new Laboratorio[capacidadMaxima];
		this.contadorLaboratorios = 0;
		this.certificaciones = new Certificacion[capacidadMaxima];
		this.contadorCertificaciones = 0;
		this.reservas = new Reserva[capacidadMaxima];
		this.contadorReservas = 0;
		this.listaEspera = new Reserva[capacidadMaxima];
		this.contadorEspera = 0;
		this.sesiones = new SesionUso[capacidadMaxima];
		this.contadorSesiones = 0;
		this.tickets = new TicketMantenimiento[capacidadMaxima];
		this.contadorTickets = 0;
		this.verificadorDisponibilidad = verificadorDisponibilidad;
		this.verificadorCertificacion = verificadorCertificacion;
	}

	public int getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public boolean registrarUsuario(Usuario usuario) {
		if (usuario == null) {
			return false;
		}
		if (contadorUsuarios >= usuarios.length) {
			System.out.println("   [ERROR] Capacidad de usuarios agotada (n=" + capacidadMaxima + ").");
			return false;
		}
		if (buscarUsuario(usuario.getIdUsuario()) != null) {
			System.out.println("   [ERROR] Identificador de usuario repetido: " + usuario.getIdUsuario());
			return false;
		}
		usuarios[contadorUsuarios] = usuario;
		contadorUsuarios = contadorUsuarios + 1;
		return true;
	}

	public boolean agregarLaboratorio(Laboratorio laboratorio) {
		if (laboratorio == null) {
			return false;
		}
		if (contadorLaboratorios >= laboratorios.length) {
			System.out.println("   [ERROR] Capacidad de laboratorios agotada (n=" + capacidadMaxima + ").");
			return false;
		}
		if (buscarLaboratorio(laboratorio.getIdLaboratorio()) != null) {
			System.out.println("   [ERROR] Identificador de laboratorio repetido: "
					+ laboratorio.getIdLaboratorio());
			return false;
		}
		laboratorios[contadorLaboratorios] = laboratorio;
		contadorLaboratorios = contadorLaboratorios + 1;
		return true;
	}

	public boolean registrarEquipoEnLaboratorio(String idLaboratorio, EquipoLaboratorio equipo) {
		if (equipo == null) {
			return false;
		}
		Laboratorio lab = buscarLaboratorio(idLaboratorio);
		if (lab == null) {
			System.out.println("   [ERROR] No existe el laboratorio " + idLaboratorio);
			return false;
		}
		if (buscarEquipoGlobal(equipo.getIdEquipo()) != null) {
			System.out.println("   [ERROR] Identificador de equipo repetido: " + equipo.getIdEquipo());
			return false;
		}
		boolean agregado = lab.agregarEquipo(equipo);
		if (!agregado) {
			System.out.println("   [ERROR] Capacidad de equipos agotada en el laboratorio " + idLaboratorio);
		}
		return agregado;
	}

	public boolean registrarCertificacion(Certificacion certificacion) {
		if (certificacion == null || certificacion.getUsuario() == null) {
			return false;
		}
		if (contadorCertificaciones >= certificaciones.length) {
			System.out.println("   [ERROR] Capacidad de certificaciones agotada (n=" + capacidadMaxima + ").");
			return false;
		}
		if (existeIdCertificacion(certificacion.getIdCertificacion())) {
			System.out.println("   [ERROR] Identificador de certificacion repetido: "
					+ certificacion.getIdCertificacion());
			return false;
		}
		boolean agregadaAlUsuario = certificacion.getUsuario().agregarCertificacion(certificacion);
		if (!agregadaAlUsuario) {
			System.out.println("   [ERROR] El usuario " + certificacion.getUsuario().getIdUsuario()
					+ " no admite mas certificaciones (capacidad llena).");
			return false;
		}
		certificaciones[contadorCertificaciones] = certificacion;
		contadorCertificaciones = contadorCertificaciones + 1;
		return true;
	}

	public Usuario buscarUsuario(String idUsuario) {
		if (idUsuario == null) {
			return null;
		}
		for (int i = 0; i < contadorUsuarios; i++) {
			if (idUsuario.equals(usuarios[i].getIdUsuario())) {
				return usuarios[i];
			}
		}
		return null;
	}

	public Laboratorio buscarLaboratorio(String idLaboratorio) {
		if (idLaboratorio == null) {
			return null;
		}
		for (int i = 0; i < contadorLaboratorios; i++) {
			if (idLaboratorio.equals(laboratorios[i].getIdLaboratorio())) {
				return laboratorios[i];
			}
		}
		return null;
	}

	public EquipoLaboratorio buscarEquipoGlobal(String idEquipo) {
		for (int i = 0; i < contadorLaboratorios; i++) {
			EquipoLaboratorio e = laboratorios[i].buscarEquipo(idEquipo);
			if (e != null) {
				return e;
			}
		}
		return null;
	}

	public Laboratorio laboratorioDelEquipo(String idEquipo) {
		for (int i = 0; i < contadorLaboratorios; i++) {
			if (laboratorios[i].buscarEquipo(idEquipo) != null) {
				return laboratorios[i];
			}
		}
		return null;
	}

	public Reserva buscarReserva(String idReserva) {
		if (idReserva == null) {
			return null;
		}
		for (int i = 0; i < contadorReservas; i++) {
			if (idReserva.equals(reservas[i].getIdReserva())) {
				return reservas[i];
			}
		}
		return null;
	}

	public boolean existeIdCertificacion(String idCertificacion) {
		for (int i = 0; i < contadorCertificaciones; i++) {
			if (certificaciones[i].getIdCertificacion().equals(idCertificacion)) {
				return true;
			}
		}
		return false;
	}

	public boolean existeIdTicket(String idTicket) {
		for (int i = 0; i < contadorTickets; i++) {
			if (tickets[i].getIdTicket().equals(idTicket)) {
				return true;
			}
		}
		return false;
	}

	public boolean existeIdSesion(String idSesion) {
		for (int i = 0; i < contadorSesiones; i++) {
			if (sesiones[i].getIdSesion().equals(idSesion)) {
				return true;
			}
		}
		return false;
	}

	public Reserva registrarReserva(String idReserva, String fecha, String horaInicio, String horaFinalizacion,
			String proposito, Usuario usuario, EquipoLaboratorio equipo) {
		if (usuario == null || equipo == null) {
			System.out.println("   [ERROR] Usuario o equipo nulo: no se crea la reserva.");
			return null;
		}
		if (contadorReservas >= reservas.length) {
			System.out.println("   [ERROR] Capacidad de reservas agotada (n=" + capacidadMaxima + ").");
			return null;
		}
		if (buscarReserva(idReserva) != null) {
			System.out.println("   [ERROR] Identificador de reserva repetido: " + idReserva);
			return null;
		}

		Reserva reserva = new Reserva(idReserva, fecha, horaInicio, horaFinalizacion, proposito, usuario, equipo);
		reservas[contadorReservas] = reserva;
		contadorReservas = contadorReservas + 1;

		if (!FechaSistema.esFechaValida(fecha) || !FechaSistema.esHoraValida(horaInicio)
				|| !FechaSistema.esHoraValida(horaFinalizacion)) {
			reserva.rechazar("fecha u hora con formato invalido");
			return reserva;
		}
		if (FechaSistema.minutosDelDia(horaFinalizacion) <= FechaSistema.minutosDelDia(horaInicio)) {
			reserva.rechazar("la hora final no es posterior a la hora inicial");
			return reserva;
		}

		Laboratorio lab = laboratorioDelEquipo(equipo.getIdEquipo());
		if (lab == null) {
			reserva.rechazar("el equipo no pertenece a ningun laboratorio registrado");
			return reserva;
		}
		if (!lab.estaActivo()) {
			reserva.rechazar("el laboratorio " + lab.getIdLaboratorio() + " no esta activo");
			return reserva;
		}
		if (!FechaSistema.dentroDelHorario(lab.getHorarioLaboratorio(), horaInicio, horaFinalizacion)) {
			reserva.rechazar("la franja esta fuera del horario del laboratorio ("
					+ lab.getHorarioLaboratorio() + ")");
			return reserva;
		}

		if (!verificadorCertificacion.validar(usuario, equipo)) {
			reserva.rechazar("el usuario no tiene certificacion vigente para el tipo "
					+ equipo.getEsTipoEquipoLab());
			return reserva;
		}

		if (!equipo.estaOperativo()) {
			reserva.rechazar("el equipo no esta operativo (estado " + equipo.getEstadoEquipo() + ")");
			return reserva;
		}
		boolean disponible = verificadorDisponibilidad.verificar(reservas, contadorReservas, equipo, fecha,
				horaInicio, horaFinalizacion);
		if (!disponible) {
			reserva.rechazar("cruce de horario con otra reserva confirmada del mismo equipo");
			return reserva;
		}

		reserva.confirmar();
		Notificador.enviar(usuario.getCorreoUsuario(), "Reserva " + idReserva + " CONFIRMADA para el equipo "
				+ equipo.getIdEquipo() + " el " + fecha + " de " + horaInicio + " a " + horaFinalizacion);
		return reserva;
	}

	public boolean moverAListaDeEspera(String idReserva) {
		Reserva reserva = buscarReserva(idReserva);
		if (reserva == null) {
			return false;
		}
		String motivo = reserva.getMotivoRechazo();
		if (!motivo.contains("cruce de horario")) {
			System.out.println("   [INFO] La reserva " + idReserva
					+ " no fue rechazada por disponibilidad; no entra a lista de espera.");
			return false;
		}
		if (contadorEspera >= listaEspera.length) {
			System.out.println("   [ERROR] Capacidad de lista de espera agotada (n=" + capacidadMaxima + ").");
			return false;
		}
		for (int i = 0; i < contadorEspera; i++) {
			Reserva e = listaEspera[i];
			boolean mismaPersona = e.getUsuario().getIdUsuario().equals(reserva.getUsuario().getIdUsuario());
			boolean mismoEquipo = e.getEquipo().getIdEquipo().equals(reserva.getEquipo().getIdEquipo());
			boolean mismaFranja = e.getFechaReserva().equals(reserva.getFechaReserva())
					&& e.getHoraInicio().equals(reserva.getHoraInicio())
					&& e.getHoraFinalizacion().equals(reserva.getHoraFinalizacion());
			if (mismaPersona && mismoEquipo && mismaFranja) {
				System.out.println("   [INFO] Ya existe una solicitud en espera identica; no se duplica.");
				return false;
			}
		}
		reserva.ponerEnEspera();
		listaEspera[contadorEspera] = reserva;
		contadorEspera = contadorEspera + 1;
		Notificador.enviar(reserva.getUsuario().getCorreoUsuario(), "Su solicitud " + idReserva
				+ " quedo en LISTA DE ESPERA para el equipo " + reserva.getEquipo().getIdEquipo());
		return true;
	}

	public void consultarReservas(String idUsuario) {
		System.out.println("Reservas del usuario " + idUsuario + ":");
		int encontradas = 0;
		for (int i = 0; i < contadorReservas; i++) {
			if (reservas[i].getUsuario().getIdUsuario().equals(idUsuario)) {
				reservas[i].mostrarDatos();
				encontradas = encontradas + 1;
			}
		}
		if (encontradas == 0) {
			System.out.println("   (sin reservas registradas)");
		}
	}

	public SesionUso abrirSesion(String idSesion, Reserva reserva, String horaAperturaReal) {
		if (reserva == null) {
			System.out.println("   [ERROR] Reserva nula: no se abre la sesion.");
			return null;
		}
		if (contadorSesiones >= sesiones.length) {
			System.out.println("   [ERROR] Capacidad de sesiones agotada (n=" + capacidadMaxima + ").");
			return null;
		}
		if (existeIdSesion(idSesion)) {
			System.out.println("   [ERROR] Identificador de sesion repetido: " + idSesion);
			return null;
		}
		if (!reserva.estaConfirmada()) {
			System.out.println("   [ERROR] La reserva " + reserva.getIdReserva()
					+ " no esta CONFIRMADA (estado " + reserva.getEstado() + ").");
			return null;
		}
		for (int i = 0; i < contadorSesiones; i++) {
			if (sesiones[i].getReserva() == reserva) {
				System.out.println("   [ERROR] Ya existe una sesion para la reserva "
						+ reserva.getIdReserva() + ".");
				return null;
			}
		}
		SesionUso sesion = new SesionUso(idSesion, reserva, horaAperturaReal);
		sesiones[contadorSesiones] = sesion;
		contadorSesiones = contadorSesiones + 1;
		Notificador.enviar(reserva.getUsuario().getCorreoUsuario(), "Sesion " + idSesion
				+ " ABIERTA para el equipo " + reserva.getEquipo().getIdEquipo());
		return sesion;
	}

	public boolean cerrarSesion(String idSesion, String horaCierreReal, String consumibles,
			String observaciones, String incidentes) {
		SesionUso sesion = null;
		for (int i = 0; i < contadorSesiones; i++) {
			if (sesiones[i].getIdSesion().equals(idSesion)) {
				sesion = sesiones[i];
				break;
			}
		}
		if (sesion == null) {
			System.out.println("   [ERROR] No existe la sesion " + idSesion);
			return false;
		}
		boolean cerrada = sesion.cerrar(horaCierreReal, consumibles, observaciones, incidentes);
		if (!cerrada) {
			System.out.println("   [ERROR] No se pudo cerrar la sesion " + idSesion
					+ " (ya estaba cerrada o la hora es invalida).");
			return false;
		}
		Notificador.enviar(sesion.getReserva().getUsuario().getCorreoUsuario(), "Sesion " + idSesion
				+ " CERRADA. Incidentes: " + sesion.getIncidentes());
		return true;
	}

	public TicketMantenimiento crearTicket(String idTicket, String prioridad, String descripcion,
			EquipoLaboratorio equipo, Tecnico tecnico, String fecha, String horaInicio) {
		if (equipo == null || tecnico == null) {
			System.out.println("   [ERROR] Equipo o tecnico nulo: no se crea el ticket.");
			return null;
		}
		if (contadorTickets >= tickets.length) {
			System.out.println("   [ERROR] Capacidad de tickets agotada (n=" + capacidadMaxima + ").");
			return null;
		}
		if (existeIdTicket(idTicket)) {
			System.out.println("   [ERROR] Identificador de ticket repetido: " + idTicket);
			return null;
		}

		TicketMantenimiento ticket = new TicketMantenimiento(idTicket, prioridad, descripcion, equipo, tecnico,
				fecha, horaInicio, capacidadMaxima);

		if (ticket.tieneFranjaEstimada()) {
			for (int i = 0; i < contadorTickets; i++) {
				TicketMantenimiento t = tickets[i];
				boolean mismoTecnico = t.getTecnicoAsignado() != null
						&& t.getTecnicoAsignado().getIdUsuario().equals(tecnico.getIdUsuario());
				boolean activo = !t.estaCerrado();
				boolean mismaFecha = t.getFecha() != null && t.getFecha().equals(fecha);
				if (mismoTecnico && activo && mismaFecha && t.tieneFranjaEstimada()) {
					boolean cruce = FechaSistema.hayCruceHorario(ticket.getHoraInicio(), ticket.getHoraFin(),
							t.getHoraInicio(), t.getHoraFin());
					if (cruce) {
						System.out.println("   [ERROR] El tecnico " + tecnico.getIdUsuario()
								+ " ya tiene el ticket " + t.getIdTicket() + " en esa franja.");
						return null;
					}
				}
			}
		}

		tickets[contadorTickets] = ticket;
		contadorTickets = contadorTickets + 1;
		equipo.setEstadoEquipo(EquipoLaboratorio.ESTADO_EN_MANTENIMIENTO);
		Notificador.enviar(tecnico.getCorreoUsuario(), "Se le asigno el ticket " + idTicket
				+ " (" + prioridad + ") del equipo " + equipo.getIdEquipo());
		int afectadas = bloquearReservasFuturas(equipo, "equipo en mantenimiento por ticket " + idTicket);
		System.out.println("   [INFO] Reservas futuras bloqueadas por el ticket " + idTicket + ": " + afectadas);
		return ticket;
	}

	public boolean cerrarTicket(String idTicket, String accionRealizada, String estadoFinalEquipo) {
		TicketMantenimiento ticket = null;
		for (int i = 0; i < contadorTickets; i++) {
			if (tickets[i].getIdTicket().equals(idTicket)) {
				ticket = tickets[i];
				break;
			}
		}
		if (ticket == null) {
			System.out.println("   [ERROR] No existe el ticket " + idTicket);
			return false;
		}
		ticket.marcarEnProceso();
		ticket.registrarAccion(accionRealizada);
		boolean cerrado = ticket.cerrar(estadoFinalEquipo);
		if (!cerrado) {
			System.out.println("   [ERROR] No se pudo cerrar el ticket " + idTicket
					+ " (falta accion o el estado final no es valido).");
			return false;
		}
		Notificador.enviar(ticket.getTecnicoAsignado().getCorreoUsuario(), "Ticket " + idTicket
				+ " CERRADO. Equipo " + ticket.getEquipo().getIdEquipo()
				+ " queda en estado " + estadoFinalEquipo);
		return true;
	}

	public int bloquearReservasFuturas(EquipoLaboratorio equipo, String motivo) {
		int canceladas = 0;
		for (int i = 0; i < contadorReservas; i++) {
			Reserva r = reservas[i];
			boolean mismoEquipo = r.getEquipo().getIdEquipo().equals(equipo.getIdEquipo());
			boolean futura = r.getFechaReserva().compareTo(FechaSistema.hoy()) >= 0;
			if (mismoEquipo && futura && r.estaConfirmada()) {
				r.cancelar(motivo);
				Notificador.enviar(r.getUsuario().getCorreoUsuario(), "Su reserva " + r.getIdReserva()
						+ " fue CANCELADA: " + motivo);
				canceladas = canceladas + 1;
			}
		}
		return canceladas;
	}

	public void consultarDisponibilidad(String idLaboratorio, String fecha, String horaInicio, String horaFin) {
		Laboratorio lab = buscarLaboratorio(idLaboratorio);
		if (lab == null) {
			System.out.println("   (no existe el laboratorio " + idLaboratorio + ")");
			return;
		}
		System.out.println("Disponibilidad en " + idLaboratorio + " el " + fecha + " de " + horaInicio
				+ " a " + horaFin + ":");
		for (int i = 0; i < lab.getContadorEquipos(); i++) {
			EquipoLaboratorio e = lab.getEquipo(i);
			boolean libre = verificadorDisponibilidad.verificar(reservas, contadorReservas, e, fecha,
					horaInicio, horaFin);
			System.out.println("   " + e.getIdEquipo() + " (" + e.getEsTipoEquipoLab() + ") estado="
					+ e.getEstadoEquipo() + " -> " + (libre ? "DISPONIBLE" : "NO DISPONIBLE"));
		}
	}

	public void consultarEquiposFueraDeServicio() {
		System.out.println("Equipos fuera de servicio (no operativos):");
		int total = 0;
		for (int i = 0; i < contadorLaboratorios; i++) {
			Laboratorio lab = laboratorios[i];
			for (int j = 0; j < lab.getContadorEquipos(); j++) {
				EquipoLaboratorio e = lab.getEquipo(j);
				if (!e.estaOperativo()) {
					System.out.println("   " + lab.getIdLaboratorio() + " / " + e.getIdEquipo()
							+ " -> " + e.getEstadoEquipo());
					total = total + 1;
				}
			}
		}
		if (total == 0) {
			System.out.println("   (todos los equipos estan operativos)");
		}
	}

	public void consultarCertificacionesPorVencer(int dias) {
		System.out.println("Certificaciones que vencen en los proximos " + dias + " dias:");
		int total = 0;
		for (int i = 0; i < contadorCertificaciones; i++) {
			long faltan = certificaciones[i].diasParaVencer();
			if (faltan >= 0 && faltan <= dias) {
				System.out.println("   " + certificaciones[i].getIdCertificacion() + " (usuario "
						+ certificaciones[i].getUsuario().getIdUsuario() + ") vence en " + faltan + " dias");
				total = total + 1;
			}
		}
		if (total == 0) {
			System.out.println("   (no hay certificaciones proximas a vencer)");
		}
	}

	public void consultarTicketsPorPrioridad(String prioridad) {
		System.out.println("Tickets con prioridad " + prioridad + ":");
		int total = 0;
		for (int i = 0; i < contadorTickets; i++) {
			if (tickets[i].getPrioridad().equals(prioridad)) {
				System.out.println("   " + tickets[i].getIdTicket() + " estado=" + tickets[i].getEstado()
						+ " equipo=" + tickets[i].getEquipo().getIdEquipo());
				total = total + 1;
			}
		}
		if (total == 0) {
			System.out.println("   (no hay tickets con esa prioridad)");
		}
	}

	public void mostrarIndicadores() {
		int minInstrumentoAvanzado = 0;
		int minEstacionElectronica = 0;
		int minOtroInstrumento = 0;
		int incidentes = 0;
		for (int i = 0; i < contadorSesiones; i++) {
			SesionUso s = sesiones[i];
			int dur = s.duracionMinutos();
			String tipo = s.getReserva().getEquipo().getEsTipoEquipoLab();
			if ("INSTRUMENTO_AVANZADO".equals(tipo)) {
				minInstrumentoAvanzado = minInstrumentoAvanzado + dur;
			} else if ("ESTACION_ELECTRONICA".equals(tipo)) {
				minEstacionElectronica = minEstacionElectronica + dur;
			} else {
				minOtroInstrumento = minOtroInstrumento + dur;
			}
			if (s.tuvoIncidente()) {
				incidentes = incidentes + 1;
			}
		}

		int canceladas = 0;
		int confirmadas = 0;
		for (int i = 0; i < contadorReservas; i++) {
			String estado = reservas[i].getEstado();
			if (Reserva.ESTADO_CANCELADA.equals(estado)) {
				canceladas = canceladas + 1;
			} else if (Reserva.ESTADO_CONFIRMADA.equals(estado)) {
				confirmadas = confirmadas + 1;
			}
		}
		int baseCancelacion = canceladas + confirmadas;
		double tasaCancelacion = baseCancelacion > 0 ? (canceladas * 100.0 / baseCancelacion) : 0.0;

		int ticketsAbiertos = 0;
		for (int i = 0; i < contadorTickets; i++) {
			if (!tickets[i].estaCerrado()) {
				ticketsAbiertos = ticketsAbiertos + 1;
			}
		}

		System.out.println("Horas de uso por tipo de equipo:");
		System.out.println("   INSTRUMENTO_AVANZADO : " + formatearHoras(minInstrumentoAvanzado));
		System.out.println("   ESTACION_ELECTRONICA : " + formatearHoras(minEstacionElectronica));
		System.out.println("   OTRO_INSTRUMENTO     : " + formatearHoras(minOtroInstrumento));
		System.out.println("Cantidad de incidentes reportados: " + incidentes);
		System.out.println("Tasa de cancelacion: " + canceladas + " canceladas / " + baseCancelacion
				+ " (confirmadas+canceladas) = " + redondear(tasaCancelacion) + " %");
		System.out.println("Carga de mantenimiento: " + ticketsAbiertos + " tickets abiertos de "
				+ contadorTickets + " totales");
	}

	private String formatearHoras(int minutos) {
		int h = minutos / 60;
		int m = minutos % 60;
		return h + " h " + (m < 10 ? "0" + m : "" + m) + " min  (" + minutos + " min)";
	}

	private double redondear(double valor) {
		return Math.round(valor * 10.0) / 10.0;
	}

	public void mostrarResumenGeneral() {
		System.out.println("Resumen del sistema (capacidad n = " + capacidadMaxima + "):");
		System.out.println("   usuarios=" + contadorUsuarios + "  laboratorios=" + contadorLaboratorios
				+ "  certificaciones=" + contadorCertificaciones);
		System.out.println("   reservas=" + contadorReservas + "  enEspera=" + contadorEspera
				+ "  sesiones=" + contadorSesiones + "  tickets=" + contadorTickets);
	}

	public int getContadorUsuarios() {
		return contadorUsuarios;
	}

	public Usuario getUsuario(int indice) {
		if (indice < 0 || indice >= contadorUsuarios) {
			return null;
		}
		return usuarios[indice];
	}

	public int getContadorLaboratorios() {
		return contadorLaboratorios;
	}

	public Laboratorio getLaboratorio(int indice) {
		if (indice < 0 || indice >= contadorLaboratorios) {
			return null;
		}
		return laboratorios[indice];
	}

	public int getContadorReservas() {
		return contadorReservas;
	}

	public Reserva getReserva(int indice) {
		if (indice < 0 || indice >= contadorReservas) {
			return null;
		}
		return reservas[indice];
	}

	public int getContadorEspera() {
		return contadorEspera;
	}

	public int getContadorSesiones() {
		return contadorSesiones;
	}

	public SesionUso getSesion(int indice) {
		if (indice < 0 || indice >= contadorSesiones) {
			return null;
		}
		return sesiones[indice];
	}

	public int getContadorTickets() {
		return contadorTickets;
	}

	public TicketMantenimiento getTicket(int indice) {
		if (indice < 0 || indice >= contadorTickets) {
			return null;
		}
		return tickets[indice];
	}
}
