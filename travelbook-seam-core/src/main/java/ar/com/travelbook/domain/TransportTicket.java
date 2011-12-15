package ar.com.travelbook.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;
import org.hibernate.validator.NotNull;

/**
 * Abstract Transport Ticket
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract public class TransportTicket extends DomainEntity {
	private static final long serialVersionUID = 1L;

	@Column(nullable = true)
	private TransportCategory category;
	@NotNull
	private Date departureDateTime;
	@NotNull
	private Date arrivalDateTime;
	private float price;
	private String description;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "departure_place_id")
	private Place departurePlace;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "arrival_place_id")
	private Place arrivalPlace;

	public void setCategory(TransportCategory category) {
		this.category = category;
	}

	public void setDepartureDateTime(Date departure) {
		this.departureDateTime = departure;
	}

	public void setArrivalDateTime(Date arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	public void setPrice(float costo) {
		this.price = costo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDeparturePlace(Place departurePlace) {
		this.departurePlace = departurePlace;
	}

	public void setArrivalPlace(Place arrivalPlace) {
		this.arrivalPlace = arrivalPlace;
	}

	protected TransportTicket() {

	}

	public TransportCategory getType() {
		return category;
	}

	public Date getDepartureDateTime() {
		return departureDateTime;
	}

	public Date getArrivalDateTime() {
		return arrivalDateTime;
	}

	public float getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public Place getDeparturaPlace() {
		return departurePlace;
	}

	public Place getArrivalPlace() {
		return arrivalPlace;
	}

	public TransportTicket(TransportCategory type, Date departureDateTime,
			Date arrivalDateTime, float price, String description,
			Place departurePlace, Place arrivalPlace) {
		this.category = type;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
		this.price = price;
		this.description = description;
		this.departurePlace = departurePlace;
		this.arrivalPlace = arrivalPlace;
	}

}
