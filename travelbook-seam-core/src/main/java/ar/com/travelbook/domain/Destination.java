package ar.com.travelbook.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;
import org.jboss.seam.annotations.Name;

/**
 * Destination from a Travel
 * 
 * @author cruz
 *
 */
@Entity
@Name("destination")
@AccessType("field")
public class Destination extends DomainEntity {
	private static final long serialVersionUID = 1L;


	@ManyToOne
	@JoinColumn(name = "travel_id", insertable = false, updatable = false)
	private Travel travel;

	
    @ManyToOne
    @JoinColumn(name="place_id")
	private Place place;

    @ManyToOne
    @JoinColumn(name="travelticket_id")
	private TransportTicket transportTicket;
    
    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
        )
        @JoinTable(
            name="Destination_AccomodationTickets",
            joinColumns=@JoinColumn(name="destination_id"),
            inverseJoinColumns=@JoinColumn(name="accomodationticket_id")
        )
	private List<AccomodationTicket> accomodationTickets;


    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
        )
        @JoinTable(
            name="Destination_Activities",
            joinColumns=@JoinColumn(name="destination_id"),
            inverseJoinColumns=@JoinColumn(name="activity_id")
        )
	private List<Activity> activities;
	
	protected Destination() {
	}

	public Destination(Place place) {
		this.place = place;
		this.accomodationTickets = new ArrayList<AccomodationTicket>();
		this.accomodationTickets.add(new AccomodationTicketNull());
		this.activities = new ArrayList<Activity>();
	}

	public Travel getTravel() {
		return this.travel;
	}

	public void setTravel(Travel travel) {
		this.travel = travel;
	}

	public Place getPlace() {
		return place;
	}
	
	public TransportTicket getTransportTicket(){
		return transportTicket;
	}	

	public void setTransportTicket(TransportTicket transportTicket) {
		this.transportTicket=transportTicket;
	}

	public List<AccomodationTicket> getAccomodationTickets() {
		return accomodationTickets;
	}

	public void setAccomodationTickets(List<AccomodationTicket> alojamientos) {
		this.accomodationTickets = alojamientos;
	}

	public List<Activity> getActivities() {
		return this.activities;
	}

	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	public Date getDepartureDateTime() {
		return this.transportTicket.getDepartureDateTime();
	}

	public String toString() {
		return this.getPlace().getName();
	}

	/**
	 * If this destination contains a Rankable entity as a place, activity, accomodation or transport
	 * 
	 * @param rankable Rankable entity we want to know it contains
	 * 
	 * @return boolean
	 */
	public boolean contains(Rankable rankable) {
		if(Place.class.isInstance(rankable)){
			return place.equals(rankable);
		}else if(Provider.class.isInstance(rankable)){
			for (Activity activity : activities) {
				if(ActivityPublic.class.isInstance(activity)){
					ActivityPublic publicActivity = (ActivityPublic) activity;
					if(publicActivity.getProvider().equals(rankable))
						return true;
				}
			}
		}else if(Accomodation.class.isInstance(rankable)){
			for (AccomodationTicket accomodationTicket : accomodationTickets) {
				if(AccomodationTicketPublic.class.isInstance(accomodationTicket)){
					AccomodationTicketPublic publicAccomodationTicket = (AccomodationTicketPublic) accomodationTicket;
					if(publicAccomodationTicket.getAccomodation().equals(rankable))
						return true;
				}
			}
		}else if(Transport.class.isInstance(rankable)){
			if(transportTicket.equals(rankable))
				return true;
		}
		return false;
	}
}
