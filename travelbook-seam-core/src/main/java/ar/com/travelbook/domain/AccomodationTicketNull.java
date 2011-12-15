package ar.com.travelbook.domain;

import javax.persistence.Entity;
import org.hibernate.annotations.AccessType;

/**
 * Null accomodationTicket. Used for symbolizing a null accommodation
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class AccomodationTicketNull extends AccomodationTicket {
	private static final long serialVersionUID = 1L;

	protected AccomodationTicketNull(){
		
	}
	
}
