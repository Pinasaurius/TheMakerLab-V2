package co.edu.eia.makerlab.modelo.laboratorio;

import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;

public class Laboratorio {
	public static final String ESTADO_ACTIVO = "ACTIVO";
	public static final String ESTADO_INACTIVO = "INACTIVO";
	private String idLaboratorio;
	private String nombreLaboratorio;
	private String ubicacion;
	private String horarioLaboratorio;
	private String estado;
	private EquipoLaboratorio[] equipos;
	private int contadorEquipos;

	public Laboratorio(String idLaboratorio, String nombreLaboratorio, String ubicacion,
			String horarioLaboratorio, String estado, int capacidadEquipos) {
		this.idLaboratorio = idLaboratorio;
		this.nombreLaboratorio = nombreLaboratorio;
		this.ubicacion = ubicacion;
		this.horarioLaboratorio = horarioLaboratorio;
		this.estado = estado;
		int capacidad = capacidadEquipos > 0 ? capacidadEquipos : 1;
		this.equipos = new EquipoLaboratorio[capacidad];
		this.contadorEquipos = 0;
	}

	public String getIdLaboratorio() {
		return idLaboratorio;
	}

	public String getNombreLaboratorio() {
		return nombreLaboratorio;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public String getHorarioLaboratorio() {
		return horarioLaboratorio;
	}

	public String getEstado() {
		return estado;
	}

	public int getContadorEquipos() {
		return contadorEquipos;
	}

	public void setIdLaboratorio(String idLaboratorio) {
		if (idLaboratorio != null && idLaboratorio.trim().length() > 0) {
			this.idLaboratorio = idLaboratorio;
		}
	}

	public void setNombreLaboratorio(String nombreLaboratorio) {
		if (nombreLaboratorio != null && nombreLaboratorio.trim().length() > 0) {
			this.nombreLaboratorio = nombreLaboratorio;
		}
	}

	public void setUbicacion(String ubicacion) {
		if (ubicacion != null && ubicacion.trim().length() > 0) {
			this.ubicacion = ubicacion;
		}
	}

	public void setHorarioLaboratorio(String horarioLaboratorio) {
		if (horarioLaboratorio != null && horarioLaboratorio.length() == 11
				&& horarioLaboratorio.charAt(5) == '-') {
			this.horarioLaboratorio = horarioLaboratorio;
		}
	}

	public void setEstado(String estado) {
		if (estado != null && estado.trim().length() > 0) {
			this.estado = estado;
		}
	}

	public boolean estaActivo() {
		return ESTADO_ACTIVO.equals(estado);
	}

	public boolean agregarEquipo(EquipoLaboratorio equipo) {
		if (equipo == null) {
			return false;
		}
		if (contadorEquipos >= equipos.length) {
			return false;
		}
		if (buscarEquipo(equipo.getIdEquipo()) != null) {
			return false;
		}
		equipos[contadorEquipos] = equipo;
		contadorEquipos = contadorEquipos + 1;
		return true;
	}

	public EquipoLaboratorio buscarEquipo(String idEquipo) {
		if (idEquipo == null) {
			return null;
		}
		for (int i = 0; i < contadorEquipos; i++) {
			if (idEquipo.equals(equipos[i].getIdEquipo())) {
				return equipos[i];
			}
		}
		return null;
	}

	public EquipoLaboratorio getEquipo(int indice) {
		if (indice < 0 || indice >= contadorEquipos) {
			return null;
		}
		return equipos[indice];
	}

	public void mostrarDatos() {
		System.out.println("LABORATORIO  id=" + idLaboratorio + "  nombre=" + nombreLaboratorio);
		System.out.println("   ubicacion=" + ubicacion + "  horario=" + horarioLaboratorio
				+ "  estado=" + estado);
		System.out.println("   equipos registrados=" + contadorEquipos + " (capacidad " + equipos.length + ")");
		for (int i = 0; i < contadorEquipos; i++) {
			equipos[i].mostrarFichaCompleta();
		}
	}
}
