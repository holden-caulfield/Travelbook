package ar.com.travelbook.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;

/**
 * Accomodation Ticket Private (specified by the User).
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class AccomodationTicketPrivate extends AccomodationTicket {
	private static final long serialVersionUID = 1L;
	private String otherCategory;	

	public AccomodationTicketPrivate(){
	}
	
	public void setOtherCategory(String otherCategory) {
		this.otherCategory = otherCategory;
	}

	public AccomodationTicketPrivate(AccomodationCategory category,  String address, float price, String description, Place place) {
		super(category, address,price,description,place);
	}
	
	public AccomodationTicketPrivate(String otherCategory, String address, float price, String description, Place place) {
		super(AccomodationCategory.Other, address,price,description,place);
		this.otherCategory = otherCategory;
	}
	
	public String getOtherCategory() {
		return otherCategory;
	}

	
	
}
