package ar.com.travelbook.wizard.action.itinerary;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import ar.com.travelbook.helpers.AbstractFilter;

/**
 * Filter for list of places
 * 
 * @author cruz
 *
 */
@Name("placeFilter")
@Scope(ScopeType.CONVERSATION)
public class PlaceFilter extends AbstractFilter implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public PlaceFilter() {
		super(false, "votesRatio");
	}


	private String search;
	
	
	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

	public void clear() {
		this.search = null;
	}
	
}
