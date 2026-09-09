package co.edu.eia.makerlab.modelo.certificacion;

import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.usuarios.Usuario;
import co.edu.eia.makerlab.util.FechaSistema;

public class Certificacion {
	private String idCertificacion;
	private String fechaEmision;
	private String vigencia;
	private Usuario usuario;
	private EquipoLaboratorio tipoEquipoAutorizado;

	public Certificacion(String idCertificacion, String fechaEmision, String vigencia, Usuario usuario,
			EquipoLaboratorio tipoEquipoAutorizado) {
		this.idCertificacion = idCertificacion;
		this.fechaEmision = fechaEmision;
		this.vigencia = vigencia;
		this.usuario = usuario;
		this.tipoEquipoAutorizado = tipoEquipoAutorizado;
	}

	public String getIdCertificacion() {
		return idCertificacion;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public String getVigencia() {
		return vigencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public EquipoLaboratorio getTipoEquipoAutorizado() {
		return tipoEquipoAutorizado;
	}

	public void setFechaEmision(String fechaEmision) {
		if (FechaSistema.esFechaValida(fechaEmision)) {
			this.fechaEmision = fechaEmision;
		}
	}

	public void setVigencia(String vigencia) {
		if (FechaSistema.esFechaValida(vigencia)) {
			this.vigencia = vigencia;
		}
	}

	public void setUsuario(Usuario usuario) {
		if (usuario != null) {
			this.usuario = usuario;
		}
	}

	public void setTipoEquipoAutorizado(EquipoLaboratorio tipoEquipoAutorizado) {
		if (tipoEquipoAutorizado != null) {
			this.tipoEquipoAutorizado = tipoEquipoAutorizado;
		}
	}

	public boolean autorizaTipo(String tipoEquipoLab) {
		if (tipoEquipoLab == null || tipoEquipoAutorizado == null) {
			return false;
		}
		return tipoEquipoLab.equals(tipoEquipoAutorizado.getEsTipoEquipoLab());
	}

	public boolean estaVigente() {
		return estaVigente(FechaSistema.hoy());
	}

	public boolean estaVigente(String fechaReferenciaIso) {
		if (!FechaSistema.esFechaValida(fechaReferenciaIso) || !FechaSistema.esFechaValida(vigencia)
				|| !FechaSistema.esFechaValida(fechaEmision)) {
			return false;
		}
		boolean yaEmitida = fechaEmision.compareTo(fechaReferenciaIso) <= 0;
		boolean noVencida = vigencia.compareTo(fechaReferenciaIso) >= 0;
		return yaEmitida && noVencida;
	}

	public long diasParaVencer() {
		return FechaSistema.diasEntre(FechaSistema.hoy(), vigencia);
	}

	public void mostrarDatos() {
		String estado = estaVigente() ? "VIGENTE" : "VENCIDA";
		System.out.println("CERTIFICACION  id=" + idCertificacion
				+ "  usuario=" + (usuario != null ? usuario.getIdUsuario() : "?")
				+ "  tipoAutorizado=" + (tipoEquipoAutorizado != null ? tipoEquipoAutorizado.getEsTipoEquipoLab() : "?")
				+ "  emision=" + fechaEmision + "  vigencia=" + vigencia + "  (" + estado + ")");
	}
}
