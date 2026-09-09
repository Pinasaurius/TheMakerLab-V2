package co.edu.eia.makerlab.modelo.equipos;

public abstract class EquipoLaboratorio {
	public static final String ESTADO_OPERATIVO = "OPERATIVO";
	public static final String ESTADO_EN_MANTENIMIENTO = "EN_MANTENIMIENTO";
	public static final String ESTADO_FUERA_DE_SERVICIO = "FUERA_DE_SERVICIO";
	public static final String ESTADO_INACTIVO = "INACTIVO";
	private String idEquipo;
	private String nombreEquipoLab;
	private String esTipoEquipoLab;
	private String nivelRiesgo;
	private String estadoEquipo;
	private String dataSheet;
	private int duracionEstimadaMinutos;

	protected EquipoLaboratorio(String idEquipo, String nombreEquipoLab, String esTipoEquipoLab,
			String nivelRiesgo, String estadoEquipo, String dataSheet) {
		this.idEquipo = idEquipo;
		this.nombreEquipoLab = nombreEquipoLab;
		this.esTipoEquipoLab = esTipoEquipoLab;
		this.nivelRiesgo = nivelRiesgo;
		this.estadoEquipo = estadoEquipo;
		this.dataSheet = dataSheet;
		this.duracionEstimadaMinutos = 0;
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public String getNombreEquipoLab() {
		return nombreEquipoLab;
	}

	public String getEsTipoEquipoLab() {
		return esTipoEquipoLab;
	}

	public String getNivelRiesgo() {
		return nivelRiesgo;
	}

	public String getEstadoEquipo() {
		return estadoEquipo;
	}

	public String getDataSheet() {
		return dataSheet;
	}

	public int getDuracionEstimadaMinutos() {
		return duracionEstimadaMinutos;
	}

	public void setIdEquipo(String idEquipo) {
		if (idEquipo != null && idEquipo.trim().length() > 0) {
			this.idEquipo = idEquipo;
		}
	}

	public void setNombreEquipoLab(String nombreEquipoLab) {
		if (nombreEquipoLab != null && nombreEquipoLab.trim().length() > 0) {
			this.nombreEquipoLab = nombreEquipoLab;
		}
	}

	public void setEsTipoEquipoLab(String esTipoEquipoLab) {
		if (esTipoEquipoLab != null && esTipoEquipoLab.trim().length() > 0) {
			this.esTipoEquipoLab = esTipoEquipoLab;
		}
	}

	public void setNivelRiesgo(String nivelRiesgo) {
		if (nivelRiesgo != null && nivelRiesgo.trim().length() > 0) {
			this.nivelRiesgo = nivelRiesgo;
		}
	}

	public void setEstadoEquipo(String estadoEquipo) {
		if (estadoEquipo != null && estadoEquipo.trim().length() > 0) {
			this.estadoEquipo = estadoEquipo;
		}
	}

	public void setDataSheet(String dataSheet) {
		this.dataSheet = dataSheet;
	}

	public void setDuracionEstimadaMinutos(int duracionEstimadaMinutos) {
		if (duracionEstimadaMinutos >= 0) {
			this.duracionEstimadaMinutos = duracionEstimadaMinutos;
		}
	}

	public boolean estaOperativo() {
		return ESTADO_OPERATIVO.equals(estadoEquipo);
	}

	public abstract void mostrarDatosEspecificos();

	public void mostrarFichaCompleta() {
		System.out.println("EQUIPO  id=" + idEquipo + "  nombre=" + nombreEquipoLab
				+ "  tipo=" + esTipoEquipoLab);
		System.out.println("   riesgo=" + nivelRiesgo + "  estado=" + estadoEquipo
				+ "  dataSheet=" + dataSheet);
		mostrarDatosEspecificos();
	}

	@Override
	public String toString() {
		return esTipoEquipoLab + " " + idEquipo + " - " + nombreEquipoLab + " [" + estadoEquipo + "]";
	}
}
