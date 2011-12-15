package ar.com.travelbook.domain;

import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;
import org.jboss.seam.annotations.Name;

/**
 * Transport Ticket not hired
 * 
 * @author cruz
 *
 */
@Entity
@Name("transportTicketPrivate")
@AccessType("field")
public class TransportTicketPrivate extends TransportTicket {
	private static final long serialVersionUID = 1L;
	private String otherCategory;
	
	public TransportTicketPrivate(){
		
	}
	
	public TransportTicketPrivate(TransportCategory category, Date departureDateTime, Date arrivalDateTime, float price, String description,Place departurePlace, Place arrivalPlace) {
		super(category,departureDateTime,arrivalDateTime,price,description,departurePlace,arrivalPlace);
	}

	public TransportTicketPrivate(String otherCategory, Date departureDateTime, Date arrivalDateTime, float price, String description,Place departurePlace, Place arrivaPlace) {
		super(TransportCategory.Other,departureDateTime,arrivalDateTime,price,description,departurePlace,arrivaPlace);
		this.otherCategory = otherCategory;
	}

	public String getOtherCategory() {
		return otherCategory;
	}


	public void setOtherCategory(String otherCategory) {
		this.otherCategory = otherCategory;
	}
}
