package co.edu.eia.makerlab.modelo.usuarios;

import co.edu.eia.makerlab.modelo.certificacion.Certificacion;

public abstract class Usuario {
	private String idUsuario;
	private String nombreUsuario;
	private String correoUsuario;
	private String esTipo;
	private Certificacion[] certificaciones;
	private int contadorCertificaciones;

	protected Usuario(String idUsuario, String nombreUsuario, String correoUsuario, String esTipo,
			int capacidadCertificaciones) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.correoUsuario = correoUsuario;
		this.esTipo = esTipo;
		int capacidad = capacidadCertificaciones > 0 ? capacidadCertificaciones : 1;
		this.certificaciones = new Certificacion[capacidad];
		this.contadorCertificaciones = 0;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public String getEsTipo() {
		return esTipo;
	}

	public void setNombreUsuario(String nombreUsuario) {
		if (nombreUsuario != null && nombreUsuario.trim().length() > 0) {
			this.nombreUsuario = nombreUsuario;
		}
	}

	public void setIdUsuario(String idUsuario) {
		if (idUsuario != null && idUsuario.trim().length() > 0) {
			this.idUsuario = idUsuario;
		}
	}

	public void setCorreoUsuario(String correoUsuario) {
		if (correoUsuario != null && correoUsuario.contains("@")) {
			this.correoUsuario = correoUsuario;
		}
	}

	public void setEsTipo(String esTipo) {
		if (esTipo != null && esTipo.trim().length() > 0) {
			this.esTipo = esTipo;
		}
	}

	public int getContadorCertificaciones() {
		return contadorCertificaciones;
	}

	public Certificacion getCertificacion(int indice) {
		if (indice < 0 || indice >= contadorCertificaciones) {
			return null;
		}
		return certificaciones[indice];
	}

	public boolean agregarCertificacion(Certificacion certificacion) {
		if (certificacion == null) {
			return false;
		}
		if (contadorCertificaciones >= certificaciones.length) {
			return false;
		}
		certificaciones[contadorCertificaciones] = certificacion;
		contadorCertificaciones = contadorCertificaciones + 1;
		return true;
	}

	public boolean tieneCertificacionVigente(String tipoEquipoLab) {
		for (int i = 0; i < contadorCertificaciones; i++) {
			Certificacion c = certificaciones[i];
			boolean mismoTipo = c.autorizaTipo(tipoEquipoLab);
			boolean vigente = c.estaVigente();
			if (mismoTipo && vigente) {
				return true;
			}
		}
		return false;
	}

	public abstract void mostrarDatosRoles();

	@Override
	public String toString() {
		return esTipo + " " + idUsuario + " - " + nombreUsuario + " (" + correoUsuario + ")";
	}
}
