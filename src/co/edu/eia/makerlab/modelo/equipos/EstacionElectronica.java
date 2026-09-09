package co.edu.eia.makerlab.modelo.equipos;

public class EstacionElectronica extends EquipoLaboratorio {
	public static final String TIPO = "ESTACION_ELECTRONICA";
	private int numeroEstacion;

	public EstacionElectronica(String idEquipo, String nombreEquipoLab, String nivelRiesgo,
			String estadoEquipo, String dataSheet, int numeroEstacion) {
		super(idEquipo, nombreEquipoLab, TIPO, nivelRiesgo, estadoEquipo, dataSheet);
		this.numeroEstacion = numeroEstacion;
	}

	public int getNumeroEstacion() {
		return numeroEstacion;
	}

	public void setNumeroEstacion(int numeroEstacion) {
		if (numeroEstacion > 0) {
			this.numeroEstacion = numeroEstacion;
		}
	}

	@Override
	public void mostrarDatosEspecificos() {
		System.out.println("   [EstacionElectronica] numeroEstacion=" + numeroEstacion);
	}
}
