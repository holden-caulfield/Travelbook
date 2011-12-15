package ar.com.travelbook.wizard.summarize;

import java.io.Serializable;
import java.util.HashMap;

import org.joda.time.DateTime;

public class TravelSummaryItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private TravelSummaryItemType tipo;
	private DateTime fecha;
	private String titulo;
	private HashMap<String,String> detalles; 
	
	public TravelSummaryItem(TravelSummaryItemType tipo, DateTime fecha, String titulo) {
		this.tipo = tipo;
		this.fecha = fecha;
		this.titulo = titulo;
		this.detalles = new HashMap<String,String>();
	}
	
	public HashMap<String, String> getDetalles() {
		return detalles;
	}

	public void setDetalles(HashMap<String, String> detalles) {
		this.detalles = detalles;
	}

	public TravelSummaryItemType getTipo() {
		return tipo;
	}

	public DateTime getFecha() {
		return fecha;
	}

	public String getTitle() {
		return titulo;
	}

	
}
