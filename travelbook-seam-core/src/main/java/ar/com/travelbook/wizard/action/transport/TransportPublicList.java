package ar.com.travelbook.wizard.action.transport;

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

import ar.com.travelbook.domain.TransportTicketPublic;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.helpers.AbstractList;
import ar.com.travelbook.helpers.CustomRestrictions;

/**
 * List of Transports factory
 * 
 * @author cruz
 *
 */
@Name("transporteListFactory")
@Scope(ScopeType.CONVERSATION)
public class TransportPublicList extends AbstractList<TransportTicketPublic, TransportPublicFilter> {
	private static final long serialVersionUID = 1L;

	@In(create=true)
	private TransportPublicFilter transporteFilter;
	
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private List<TransportTicketPublic> transportesContratados;
		
	@Observer("searchTransporte")
	@Factory
	public void getTransportesContratados() {
		this.transportesContratados = super.getEntities();
	};
	
	@In Travel travel;
	@In Integer selectedItem;
	
	/**
	 * Maps Filter to Hibernate filter
	 */
	@Override
	protected void processFilters(Criteria criteria) {
		if(transporteFilter.getEmpresa()!=null) {
			criteria.add(Restrictions.ilike("empresa", "%" + transporteFilter.getEmpresa() + "%"));
		}
		if(transporteFilter.getMedioTransporte()!=null)
			criteria.add(Restrictions.eq("category", transporteFilter.getMedioTransporte()));
		if(transporteFilter.getMaximoPrecio()!=0)
			criteria.add(Restrictions.le("price", transporteFilter.getMaximoPrecio()));
		if(transporteFilter.getMinimoPrecio()!=0)
			criteria.add(Restrictions.ge("price", transporteFilter.getMinimoPrecio()));
		if(transporteFilter.getFechaLlegada()!=null)
			criteria.add(CustomRestrictions.sameDay("departureDateTime", transporteFilter.getFechaLlegada()));
		if(transporteFilter.getFechaSalida()!=null)
			criteria.add(CustomRestrictions.sameDay("arrivalDateTime", transporteFilter.getFechaSalida()));
		
		criteria.add(Restrictions.eq("departurePlace", travel.getDestinations().get(selectedItem-1).getPlace()));
		criteria.add(Restrictions.eq("arrivalPlace", travel.getDestinations().get(selectedItem).getPlace()));
		
		criteria.createAlias("transport", "rankable");
		
	}

	@Override
	protected Class<TransportTicketPublic> getClazz() {
		return TransportTicketPublic.class;
	}

	@Override
	protected TransportPublicFilter getFilter() {
		return this.transporteFilter;
	}

}
