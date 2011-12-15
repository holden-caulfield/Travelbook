package ar.com.travelbook.wizard.action.itinerary;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import ar.com.travelbook.domain.Place;
import ar.com.travelbook.helpers.AbstractList;

/**
 * List of places factory
 * 
 * @author cruz
 *
 */
@Name("placeListFactory")
@Scope(ScopeType.CONVERSATION)
public class PlaceList extends AbstractList<Place, PlaceFilter> {
	private static final long serialVersionUID = 1L;

	@In
	private PlaceFilter placeFilter;
	
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private List<Place> places;
		
	@Observer("searchPlace")
	@Factory
	public void getPlaces() {
		this.places = super.getEntities();
	};
	
	@Override
	protected void processFilters(Criteria criteria) {
		if(placeFilter.getSearch()!=null) {
			criteria.add(Restrictions.ilike("name", "%" + placeFilter.getSearch() + "%"));
		}
	}

	@Override
	protected Class<Place> getClazz() {
		return Place.class;
	}

	@Override
	protected PlaceFilter getFilter() {
		return this.placeFilter;
	}

	
}
