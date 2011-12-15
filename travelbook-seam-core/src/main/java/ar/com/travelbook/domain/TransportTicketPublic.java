package ar.com.travelbook.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;
import org.jboss.seam.annotations.Name;

/**
 * Transport Public Ticket, can be hired
 * 
 * @author cruz
 *
 */
@Entity
@Name("transportTicketPublic")
@AccessType("field")
public class TransportTicketPublic extends TransportTicket {
	private static final long serialVersionUID = 1L;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="transport_id")
    private Transport transport;
	
	protected TransportTicketPublic(){
		
	}
	
	public TransportTicketPublic(TransportCategory category, Date departureDateTime, Date arrivalDateTime, float price, String description,Place departurePlace, Place arrivalPlace, Transport transport) {
		super(category,departureDateTime,arrivalDateTime,price,description,departurePlace,arrivalPlace);
		this.transport = transport;
	}

	public Transport getTransport() {
		return transport;
	}
	
}
