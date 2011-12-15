package ar.com.travelbook.wizard.action.itinerary;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import ar.com.travelbook.domain.Place;
import ar.com.travelbook.domain.Rankable;
import ar.com.travelbook.domain.Vote;
import ar.com.travelbook.helpers.AbstractList;

/**
 * List of places factory
 * 
 * @author cruz
 *
 */
@Name("TopPlacesListFactory")
@Scope(ScopeType.CONVERSATION)
public class TopPlacesList extends AbstractList<Place, TopPlacesFilter> {
	private static final long serialVersionUID = 1L;

	@In
	private EntityManager entityManager; 
	
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private List<Place> topPlaces;
		
	@Observer("searchTopPlaces")
	@Factory 
	public void getTopPlaces() {
		this.topPlaces = super.getEntities();
	};

	@Override
	protected void processFilters(Criteria criteria) {
		List r = entityManager.createQuery("select r.id " + 
					" from Place r join r.votes as rvotes group by r.id order by count(rvotes)").
					setMaxResults(20).
					getResultList();
		
		criteria.add(Restrictions.in("id", r));
	}

	@Override
	protected Class<Place> getClazz() {
		return Place.class;
	}

	@Override
	protected TopPlacesFilter getFilter() {
		return new TopPlacesFilter();
	}

	
}
