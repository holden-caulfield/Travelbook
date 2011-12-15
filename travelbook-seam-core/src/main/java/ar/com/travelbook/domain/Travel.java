package ar.com.travelbook.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.AccessType;
import org.jboss.seam.annotations.Name;

/**
 * Travel. Has all data of destinations, transports, activities and accomodations
 * @author cruz
 *
 */
@Entity
@Name("travel")
@AccessType("field")
public class Travel extends DomainEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="travel_id")
	private List<Destination> destinations;

	public Travel() {
		destinations = new ArrayList<Destination>();
	}

	public List<Destination> getDestinations() {
		return this.destinations;
	}
	
	public void addDestination(Place place){
		this.destinations.add(new Destination(place));
	}
	
	public boolean canChooseTransport(){
		return destinations.size() > 1;
	}
	
	/**
	 * Tells whether the accommodations can be chosen 
	 * 
	 * @return
	 */
	public boolean canChooseAccomodation(){
		if(destinations.size()>1){
			for (Destination destination : destinations.subList(1, destinations.size())) {
				if(destination.getTransportTicket()==null)
					return false;
			}
			return true;
		}
		else
			return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Checks if a Rankable was ranked on this Travel
	 * 
	 * @param rankable
	 * 
	 * @return
	 */
	public boolean contains(Rankable rankable) {
		for (Destination destination : destinations) {
			if(destination.contains(rankable))
				return true;
		}
		return false;
	}
	
}
