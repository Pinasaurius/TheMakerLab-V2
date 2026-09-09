package co.edu.eia.makerlab.modelo.equipos;

public class OtroInstrumento extends EquipoLaboratorio {
	public static final String TIPO = "OTRO_INSTRUMENTO";
	private String especificacion;
	private String clasificacion;

	public OtroInstrumento(String idEquipo, String nombreEquipoLab, String nivelRiesgo,
			String estadoEquipo, String dataSheet, String especificacion, String clasificacion) {
		super(idEquipo, nombreEquipoLab, TIPO, nivelRiesgo, estadoEquipo, dataSheet);
		this.especificacion = especificacion;
		this.clasificacion = clasificacion;
	}

	public String getEspecificacion() {
		return especificacion;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setEspecificacion(String especificacion) {
		if (especificacion != null && especificacion.trim().length() > 0) {
			this.especificacion = especificacion;
		}
	}

	public void setClasificacion(String clasificacion) {
		if (clasificacion != null && clasificacion.trim().length() > 0) {
			this.clasificacion = clasificacion;
		}
	}

	@Override
	public void mostrarDatosEspecificos() {
		System.out.println("   [OtroInstrumento] especificacion=" + especificacion
				+ "  clasificacion=" + clasificacion);
	}
}
