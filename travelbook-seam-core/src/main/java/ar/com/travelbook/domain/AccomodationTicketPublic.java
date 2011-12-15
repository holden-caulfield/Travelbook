package ar.com.travelbook.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;

/**
 * Accomodation Ticket Public. Used for assigning an Accomodation (rankable) to a Travel
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class AccomodationTicketPublic extends AccomodationTicket {
	private static final long serialVersionUID = 1L;

	protected AccomodationTicketPublic(){
		
	}
	
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="accomodation_id")
	private Accomodation accomodation;

    /**
     * AccomodationTicketPublic
     * 
     * @param category Category
     * @param address Address
     * @param price Price
     * @param description Description
     * @param place Place
     * @param accomodation Accomodation to link
     */
	public AccomodationTicketPublic(AccomodationCategory category, String address, float price, String description, Place place, Accomodation accomodation) {
		super(category, address,price,description,place);
		this.accomodation = accomodation;
	}

	public Accomodation getAccomodation() {
		return accomodation;
	}
	
	
}
