package ar.com.travelbook.helpers;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.jboss.seam.annotations.In;

import ar.com.travelbook.domain.DomainEntity;

/**
 * Abstract list
 * 
 * @author cruz
 *
 * @param <ENTITY> Domain Entity
 * @param <FILTER> The filter
 * 
 * It is using the Session because the Criteria API doesn't exist in JPA
 */
public abstract class AbstractList<ENTITY extends DomainEntity, FILTER extends AbstractFilter> implements Serializable{
	private static final long serialVersionUID = 1L;

	@In
	private Session session;

	/**
	 * Gets class of entity
	 * 
	 * @return Class
	 */
	protected abstract Class<ENTITY> getClazz();
	
	/**
	 * Get Filter object
	 * 
	 * @return FILTER
	 */
	protected abstract FILTER getFilter();
	
	/**
	 * Gets the list of entities filtered
	 * 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	protected List<ENTITY> getEntities() {

		Criteria criteria = session.createCriteria(getClazz());
		
		processFilters(criteria);
		addOrders(criteria);
		
		return criteria.list();
	}


	/**
	 * Transforms the filters to a criteria
	 * 
	 * @param criteria
	 */
	protected abstract void processFilters(Criteria criteria);

	/**
	 * Add order to the listing
	 * 
	 * @param criteria
	 */
	protected void addOrders(Criteria criteria) {
		if(getFilter().getAscending()){
			criteria.addOrder(Order.asc(getFilter().getSortProperty()));
		}else{
			criteria.addOrder(Order.desc(getFilter().getSortProperty()));
		}
	
	}
}
