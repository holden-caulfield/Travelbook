package ar.com.travelbook.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;

/**
 * An accomodation for a Travel
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class AccomodationTicket extends DomainEntity {
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = true)
	private AccomodationCategory category;
	private String address;
	private float price;
	private String description;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="place_id")
	private Place place;

    /**
     * Constructor for hibernate
     */
	public AccomodationTicket() {
		
	}
	
	public AccomodationCategory getCategory() {
		return category;
	}

	public String getAddress() {
		return address;
	}

	public float getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}


	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	
	public void setCategory(AccomodationCategory category) {
		this.category = category;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Constructor of AccomodationTicket for the application
	 * 
	 * @param category Category 
	 * @param address Address
	 * @param price Price
	 * @param description Description
	 * @param place Place
	 */
	public AccomodationTicket(AccomodationCategory category, String address, float price, String description, Place place) {
		this.category = category;
		this.address = address;
		this.price = price;
		this.description = description;
		this.place = place;
	}
	
}
