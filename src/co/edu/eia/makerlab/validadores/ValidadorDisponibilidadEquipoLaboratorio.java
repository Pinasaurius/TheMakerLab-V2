package co.edu.eia.makerlab.validadores;

import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.reserva.Reserva;
import co.edu.eia.makerlab.util.FechaSistema;

public class ValidadorDisponibilidadEquipoLaboratorio implements ValidadorReservaEquipoLaboratorio {
	public ValidadorDisponibilidadEquipoLaboratorio() {
	}

	@Override
	public boolean verificar(Reserva[] reservas, int contadorReservas, EquipoLaboratorio equipo,
			String fecha, String horaInicio, String horaFin) {
		if (equipo == null) {
			return false;
		}
		if (!equipo.estaOperativo()) {
			return false;
		}
		for (int i = 0; i < contadorReservas; i++) {
			Reserva r = reservas[i];
			if (r == null || !r.estaConfirmada()) {
				continue;
			}
			boolean mismoEquipo = equipo.getIdEquipo().equals(r.getEquipo().getIdEquipo());
			boolean mismaFecha = fecha.equals(r.getFechaReserva());
			if (mismoEquipo && mismaFecha) {
				boolean cruce = FechaSistema.hayCruceHorario(horaInicio, horaFin,
						r.getHoraInicio(), r.getHoraFinalizacion());
				if (cruce) {
					return false;
				}
			}
		}
		return true;
	}
}
