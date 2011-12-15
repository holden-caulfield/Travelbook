package ar.com.travelbook.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;

/**
 * A provider of an activity
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class Provider extends Rankable {
	private static final long serialVersionUID = 1L;

}
