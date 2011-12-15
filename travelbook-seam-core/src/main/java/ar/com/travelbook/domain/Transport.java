package ar.com.travelbook.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;

/**
 * Rankable Transport
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class Transport extends Rankable {
	private static final long serialVersionUID = 1L;

	
	public Transport() {
		
	}
}
