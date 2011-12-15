package ar.com.travelbook.wizard.summarize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import ar.com.travelbook.domain.Destination;
import ar.com.travelbook.domain.AccomodationTicket;
import ar.com.travelbook.domain.AccomodationTicketNull;
import ar.com.travelbook.domain.Transport;
import ar.com.travelbook.domain.TransportTicket;
import ar.com.travelbook.domain.Travel;

@Name ("travelSummary")
@Scope(ScopeType.CONVERSATION) 
public class TravelSummary {

	//private List<ItemResumenViaje> items;
	private HashMap<DateMidnight, List<TravelSummaryItem>> mapa;
	
	@In Travel travel;
	@Create
	public void initialize() {
		this.mapa = new HashMap<DateMidnight, List<TravelSummaryItem>>();
		
		Destination destinoAnterior = null;
		TravelSummaryItem item = null;
		for (Destination destino : travel.getDestinations()) {
			/* TRASLADOS */
			TransportTicket traslado = destino.getTransportTicket();
			if(traslado != null) {
				String detalleTrasladoSalida = destinoAnterior.getPlace().getName() + " - " + destino.getPlace().getName();
				String detalleTrasladoLlegada = "Arribo a " + destino.getPlace().getName();
				item = new TravelSummaryItem(TravelSummaryItemType.TRANSPORTE_INICIO,new DateTime(traslado.getDepartureDateTime()),detalleTrasladoSalida);
				this.addItemToMapa(item);
				item = new TravelSummaryItem(TravelSummaryItemType.TRANSPORTE_FIN, new DateTime(traslado.getDepartureDateTime()), detalleTrasladoLlegada);
				this.addItemToMapa(item);
			}
			/* ALOJAMIENTOS */
			List<AccomodationTicket> estadias = destino.getAccomodationTickets();
			if(estadias.size() > 0) for (AccomodationTicket estadia : estadias) {
				if(estadia instanceof AccomodationTicketNull) continue;
				String detalleCheckIn = "Registrarse en: "+estadia.getDescription();
				item = new TravelSummaryItem(TravelSummaryItemType.ALOJAMIENTO_INICIO, new DateTime(destino.getTransportTicket().getDepartureDateTime()), detalleCheckIn );
				this.addItemToMapa(item);
			}
			
			destinoAnterior = destino;
		}
		
	}
	
	private void addItemToMapa(TravelSummaryItem itemResumen) {
		DateMidnight fecha = itemResumen.getFecha().toDateMidnight();
		if (!this.mapa.keySet().contains(fecha))
			this.mapa.put(fecha, new ArrayList<TravelSummaryItem>());
		((List<TravelSummaryItem>) this.mapa.get(fecha)).add(itemResumen);
	}
	
	public HashMap<DateMidnight, List<TravelSummaryItem>> getItems() {
		return this.mapa;
	}
	
	public List<DateMidnight> getDays() {
		return new ArrayList<DateMidnight>(this.mapa.keySet());
	}
	/*@In Viaje viaje;
	public List<String> getResumenViaje() {
		List<String> pasos = new ArrayList<String>();
		return null;
	}
	*/
}
