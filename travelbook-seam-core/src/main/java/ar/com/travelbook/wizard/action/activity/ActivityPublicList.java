package ar.com.travelbook.wizard.action.activity;

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

import ar.com.travelbook.domain.ActivityPublic;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.helpers.AbstractList;
import ar.com.travelbook.helpers.CustomRestrictions;

/**
 * The list of Activities that are public
 * 
 * @author cruz
 *
 */
@Name("activityListFactory")
@Scope(ScopeType.CONVERSATION)
public class ActivityPublicList extends AbstractList<ActivityPublic, ActivityPublicFilter> {
	private static final long serialVersionUID = 1L;

	@In(create=true)
	private ActivityPublicFilter activityFilter;
	
	@Override
	protected Class<ActivityPublic> getClazz() {
		return ActivityPublic.class;
	}

	@In Travel travel;
	@In Integer selectedItem;
	
	/**
	 * Transforms the filter to a Criteria
	 */
	@Override
	protected void processFilters(Criteria criteria) {
		criteria.add(Restrictions.eq("place", travel.getDestinations().get(selectedItem).getPlace()));

		if(activityFilter.getName()!=null) {
			criteria.add(Restrictions.ilike("name", "%" + activityFilter.getName() + "%"));
		}
		if(activityFilter.getMaxPrice()!=0)
			criteria.add(Restrictions.le("price", activityFilter.getMaxPrice()));
		if(activityFilter.getMinPrice()!=0)
			criteria.add(Restrictions.ge("price", activityFilter.getMinPrice()));
		if(activityFilter.getStartDate()!=null)
			criteria.add(CustomRestrictions.sameDay("startDateTime", activityFilter.getStartDate()));
		if(activityFilter.getEndDate()!=null)
			criteria.add(CustomRestrictions.sameDay("endDateTime", activityFilter.getEndDate()));
		
		criteria.createAlias("provider", "rankable");

	}
	
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private List<ActivityPublic> actividadesContratadas;

	@Observer("searchActividad")
	@Factory
	public void getActividadesContratadas() {
		actividadesContratadas = super.getEntities();
	}

	@Override
	protected ActivityPublicFilter getFilter() {
		return this.activityFilter;
	}
	
}
