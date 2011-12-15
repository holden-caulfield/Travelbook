package ar.com.travelbook.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;

/**
 * A rankable Accomodation. 
 * It's used only in the AccomodationTicketPublic for association with a Travel
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class Accomodation extends Rankable {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs object
	 */
	public Accomodation() {
		
	}
}
