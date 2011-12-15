package ar.com.travelbook.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;

/**
 * Activity Base Class
 * 
 * @author gabriel
 *
 */
@Entity
@AccessType("field")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Activity extends DomainEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	private Date startDateTime;
	private Date endDateTime;
	private float price;
	
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="place_id")
	private Place place;
	
	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date start) {
		this.startDateTime = start;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date end) {
		this.endDateTime = end;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name; 
	}
	
	public void setPlace(Place place) {
		this.place = place;
	}

	public Place getPlace() {
		return place;
	}

	protected Activity() {
	
	}
	
	/**
	 * Constructor Activiy 
	 * 
	 * @param name Name
	 * @param start Start date time
	 * @param end End date time
	 * @param place Place
	 */
	public Activity(String name,Date start, Date end, Place place) {
		this.name = name;
		this.endDateTime = end;
		this.startDateTime = start;
		this.place = place;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getPrice() {
		return price;
	}
}
