package co.edu.eia.makerlab.modelo.usuarios;

public class Profesor extends Usuario {
	private String certificadoProfesion;
	private boolean esActivo;

	public Profesor(String idUsuario, String nombreUsuario, String correoUsuario, String esTipo,
			String certificadoProfesion, boolean esActivo, int capacidadCertificaciones) {
		super(idUsuario, nombreUsuario, correoUsuario, esTipo, capacidadCertificaciones);
		this.certificadoProfesion = certificadoProfesion;
		this.esActivo = esActivo;
	}

	public String getCertificadoProfesion() {
		return certificadoProfesion;
	}

	public boolean isEsActivo() {
		return esActivo;
	}

	public void setCertificadoProfesion(String certificadoProfesion) {
		if (certificadoProfesion != null && certificadoProfesion.trim().length() > 0) {
			this.certificadoProfesion = certificadoProfesion;
		}
	}

	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}

	@Override
	public void mostrarDatosRoles() {
		System.out.println("PROFESOR  id=" + getIdUsuario() + "  nombre=" + getNombreUsuario());
		System.out.println("   correo=" + getCorreoUsuario());
		System.out.println("   certificadoProfesion=" + certificadoProfesion
				+ "  activo=" + (esActivo ? "SI" : "NO"));
		System.out.println("   certificaciones cargadas=" + getContadorCertificaciones());
	}
}
