package co.edu.eia.makerlab.validadores;

import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.usuarios.Usuario;

public class ValidadorCertificacionUsuarioBasico implements ValidadorCertificacionUsuario {
	public ValidadorCertificacionUsuarioBasico() {
	}

	@Override
	public boolean validar(Usuario usuario, EquipoLaboratorio equipo) {
		if (usuario == null || equipo == null) {
			return false;
		}
		String tipoEquipo = equipo.getEsTipoEquipoLab();
		return usuario.tieneCertificacionVigente(tipoEquipo);
	}
}
