package ar.com.travelbook.wizard.action.accomodation;

import java.io.Serializable;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import ar.com.travelbook.domain.AccomodationCategory;
import ar.com.travelbook.domain.Place;
import ar.com.travelbook.helpers.RankableFilter;

/**
 * Filters the AccomodationPublic list
 * 
 * @author cruz
 *
 */
@Name("accomodationFilter")
@Scope(ScopeType.CONVERSATION)
public class AccomodationPublicFilter extends RankableFilter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Place place;
	private String description;
	private String address;
	private Date startDate;
	private Date endDate;
	private float minPrice;
	private float maxPrice;
	private AccomodationCategory accomodationCategory;
	
	public String getDescription() {
		return description;
	}

	public void setName(String description) {
		this.description = description;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	public void setAccomodationCategory(AccomodationCategory accomodationCategory) {
		this.accomodationCategory = accomodationCategory;
	}
	
	public AccomodationCategory getAccomodationCategory() {
		return accomodationCategory;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Place getPlace() {
		return place;
	}

}
