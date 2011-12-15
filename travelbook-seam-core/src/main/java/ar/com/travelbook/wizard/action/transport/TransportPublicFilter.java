package ar.com.travelbook.wizard.action.transport;

import java.io.Serializable;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import ar.com.travelbook.domain.TransportCategory;
import ar.com.travelbook.helpers.RankableFilter;

@Name("transporteFilter")
@Scope(ScopeType.CONVERSATION)
public class TransportPublicFilter extends RankableFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	private String empresa;
	private float minimoPrecio;
	private float maximoPrecio;
	private Date fechaLlegada;
	private Date fechaSalida;
	private TransportCategory medioTransporte;
	
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public float getMinimoPrecio() {
		return minimoPrecio;
	}
	public void setMinimoPrecio(float minimoPrecio) {
		this.minimoPrecio = minimoPrecio;
	}
	public float getMaximoPrecio() {
		return maximoPrecio;
	}
	public void setMaximoPrecio(float maximoPrecio) {
		this.maximoPrecio = maximoPrecio;
	}
	public Date getFechaLlegada() {
		return fechaLlegada;
	}
	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public void setMedioTransporte(TransportCategory medioTransporte) {
		this.medioTransporte = medioTransporte;
	}
	public TransportCategory getMedioTransporte() {
		return medioTransporte;
	}
}
