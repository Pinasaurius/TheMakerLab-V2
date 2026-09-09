package co.edu.eia.makerlab.modelo.equipos;

public class InstrumentoAvanzado extends EquipoLaboratorio {
	public static final String TIPO = "INSTRUMENTO_AVANZADO";
	private String tipoFuncion;

	public InstrumentoAvanzado(String idEquipo, String nombreEquipoLab, String nivelRiesgo,
			String estadoEquipo, String dataSheet, String tipoFuncion) {
		super(idEquipo, nombreEquipoLab, TIPO, nivelRiesgo, estadoEquipo, dataSheet);
		this.tipoFuncion = tipoFuncion;
	}

	public String getTipoFuncion() {
		return tipoFuncion;
	}

	public void setTipoFuncion(String tipoFuncion) {
		if (tipoFuncion != null && tipoFuncion.trim().length() > 0) {
			this.tipoFuncion = tipoFuncion;
		}
	}

	@Override
	public void mostrarDatosEspecificos() {
		System.out.println("   [InstrumentoAvanzado] tipoFuncion=" + tipoFuncion);
	}
}
