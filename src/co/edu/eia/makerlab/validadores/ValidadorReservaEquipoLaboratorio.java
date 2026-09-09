package co.edu.eia.makerlab.validadores;

import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.reserva.Reserva;

public interface ValidadorReservaEquipoLaboratorio {
	boolean verificar(Reserva[] reservas, int contadorReservas, EquipoLaboratorio equipo, String fecha,
			String horaInicio, String horaFin);
}
