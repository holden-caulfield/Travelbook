package ar.com.travelbook.wizard.action.accomodation;

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

import ar.com.travelbook.domain.AccomodationTicketPublic;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.helpers.AbstractList;
import ar.com.travelbook.helpers.CustomRestrictions;

/**
 * List of accomodationsPublic
 * 
 * @author cruz
 *
 */
@Name("accomodationListFactory")
@Scope(ScopeType.CONVERSATION)
public class AccomodationPublicList extends AbstractList<AccomodationTicketPublic, AccomodationPublicFilter> {
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private AccomodationPublicFilter accomodationFilter;

	@In
	private Integer selectedItem;
	
	@In private Travel travel;
	
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private List<AccomodationTicketPublic> publicAccomodations;

	@Observer("searchAccomodations")
	@Factory
	public void getPublicAccomodations() {
		publicAccomodations = super.getEntities();
	}

	@Override
	protected Class<AccomodationTicketPublic> getClazz() {
		return AccomodationTicketPublic.class;
	}

	/**
	 * Maps the filter to a criteria
	 * 
	 */
	@Override
	protected void processFilters(Criteria criteria) {
		if (accomodationFilter.getMaxPrice() != 0)
			criteria.add(Restrictions.le("price", accomodationFilter.getMaxPrice()));
		if (accomodationFilter.getMinPrice() != 0)
			criteria.add(Restrictions.ge("price", accomodationFilter.getMinPrice()));
		if (accomodationFilter.getStartDate() != null)
			criteria.add(CustomRestrictions.sameDay("departureDateTime", accomodationFilter.getStartDate()));
		if (accomodationFilter.getEndDate() != null)
			criteria.add(CustomRestrictions.sameDay("arrivalDateTime", accomodationFilter.getEndDate()));
		if (accomodationFilter.getAddress() != null) 
			criteria.add(Restrictions.ilike("address", "%"+accomodationFilter.getAddress()+"%"));
		if(accomodationFilter.getDescription()!=null)
			criteria.add(Restrictions.ilike("description", "%"+accomodationFilter.getDescription()+"%"));
		criteria.add(Restrictions.eq("place", travel.getDestinations().get(selectedItem).getPlace()));
		
		criteria.createAlias("accomodation", "rankable");

	}

	@Override
	protected AccomodationPublicFilter getFilter() {
		return accomodationFilter;
	}

}
