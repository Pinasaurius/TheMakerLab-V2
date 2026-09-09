package co.edu.eia.makerlab.validadores;

import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.usuarios.Usuario;

public interface ValidadorCertificacionUsuario {
	boolean validar(Usuario usuario, EquipoLaboratorio equipo);
}
