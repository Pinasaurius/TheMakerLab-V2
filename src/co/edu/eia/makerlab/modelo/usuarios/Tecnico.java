package co.edu.eia.makerlab.modelo.usuarios;

public class Tecnico extends Usuario {
	private String certificadoEducacion;
	private String certificadoLaboral;
	private boolean esActivo;

	public Tecnico(String idUsuario, String nombreUsuario, String correoUsuario, String esTipo,
			String certificadoEducacion, String certificadoLaboral, boolean esActivo,
			int capacidadCertificaciones) {
		super(idUsuario, nombreUsuario, correoUsuario, esTipo, capacidadCertificaciones);
		this.certificadoEducacion = certificadoEducacion;
		this.certificadoLaboral = certificadoLaboral;
		this.esActivo = esActivo;
	}

	public String getCertificadoEducacion() {
		return certificadoEducacion;
	}

	public String getCertificadoLaboral() {
		return certificadoLaboral;
	}

	public boolean isEsActivo() {
		return esActivo;
	}

	public void setData(String certificadoEducacion, String certificadoLaboral, boolean esActivo) {
		if (certificadoEducacion != null && certificadoEducacion.trim().length() > 0) {
			this.certificadoEducacion = certificadoEducacion;
		}
		if (certificadoLaboral != null && certificadoLaboral.trim().length() > 0) {
			this.certificadoLaboral = certificadoLaboral;
		}
		this.esActivo = esActivo;
	}

	@Override
	public void mostrarDatosRoles() {
		System.out.println("TECNICO  id=" + getIdUsuario() + "  nombre=" + getNombreUsuario());
		System.out.println("   correo=" + getCorreoUsuario());
		System.out.println("   certificadoEducacion=" + certificadoEducacion
				+ "  certificadoLaboral=" + certificadoLaboral);
		System.out.println("   disponible para mantenimientos=" + (esActivo ? "SI" : "NO"));
	}
}
