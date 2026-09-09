package co.edu.eia.makerlab.modelo.usuarios;

public class Estudiante extends Usuario {
	private String codigoEstudiante;
	private String gradoEducacion;

	public Estudiante(String idUsuario, String nombreUsuario, String correoUsuario, String esTipo,
			String codigoEstudiante, String gradoEducacion, int capacidadCertificaciones) {
		super(idUsuario, nombreUsuario, correoUsuario, esTipo, capacidadCertificaciones);
		this.codigoEstudiante = codigoEstudiante;
		this.gradoEducacion = gradoEducacion;
	}

	public String getCodigoEstudiante() {
		return codigoEstudiante;
	}

	public String getGradoEducacion() {
		return gradoEducacion;
	}

	public void setCodigoEstudiante(String codigoEstudiante) {
		if (codigoEstudiante != null && codigoEstudiante.trim().length() > 0) {
			this.codigoEstudiante = codigoEstudiante;
		}
	}

	public void setGradoEducacion(String gradoEducacion) {
		if (gradoEducacion != null && gradoEducacion.trim().length() > 0) {
			this.gradoEducacion = gradoEducacion;
		}
	}

	@Override
	public void mostrarDatosRoles() {
		System.out.println("ESTUDIANTE  id=" + getIdUsuario() + "  nombre=" + getNombreUsuario());
		System.out.println("   correo=" + getCorreoUsuario());
		System.out.println("   codigoEstudiante=" + codigoEstudiante + "  grado=" + gradoEducacion);
		System.out.println("   certificaciones cargadas=" + getContadorCertificaciones());
	}
}
