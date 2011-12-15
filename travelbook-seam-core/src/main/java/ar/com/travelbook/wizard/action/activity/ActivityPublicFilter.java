package ar.com.travelbook.wizard.action.activity;

import java.io.Serializable;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import ar.com.travelbook.helpers.RankableFilter;

/**
 * Filters the ActivitiesPublic
 * 
 * @author cruz
 *
 */
@Name("activityFilter")
@Scope(ScopeType.CONVERSATION)
public class ActivityPublicFilter extends RankableFilter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private float minPrice;
	private float maxPrice;
	private Date startDate;
	private Date endDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
