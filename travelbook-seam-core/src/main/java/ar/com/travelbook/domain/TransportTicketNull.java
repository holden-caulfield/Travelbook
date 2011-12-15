package ar.com.travelbook.domain;

import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;
import org.jboss.seam.annotations.Name;


/**
 * Transport Ticket Null Objects
 * 
 * @author cruz
 *
 */
@Entity
@Name("transportNull")
@AccessType("field")
public class TransportTicketNull extends TransportTicket {
	private static final long serialVersionUID = 1L;

	protected TransportTicketNull(){
		
	}
	
	public TransportTicketNull(TransportCategory category, Date departureDateTime, Date arrivalDateTime, float price, String description,Place departurePlace, Place arrivalPlace) {
		super(category,departureDateTime,arrivalDateTime,price,description,departurePlace,arrivalPlace);
	}
}
