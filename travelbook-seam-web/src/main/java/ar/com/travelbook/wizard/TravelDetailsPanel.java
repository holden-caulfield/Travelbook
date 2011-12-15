package ar.com.travelbook.wizard;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;

import ar.com.travelbook.common.ContextSeamPropertyModel;
import ar.com.travelbook.domain.AccomodationTicket;
import ar.com.travelbook.domain.AccomodationTicketPublic;
import ar.com.travelbook.domain.AccomodationTicketNull;
import ar.com.travelbook.domain.AccomodationTicketPrivate;
import ar.com.travelbook.domain.AccomodationCategory;
import ar.com.travelbook.domain.Destination;
import ar.com.travelbook.domain.TransportCategory;
import ar.com.travelbook.domain.TransportTicket;
import ar.com.travelbook.domain.TransportTicketPublic;
import ar.com.travelbook.domain.TransportTicketPrivate;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.helpers.WizardConversationManager;

public class TravelDetailsPanel extends Panel {
	private final class ItineraryList extends ListView {
		@In(create = true)
		WizardConversationManager wizardConversationManager;
		private static final long serialVersionUID = 1L;

		private ItineraryList(String id, IModel model) {
			super(id, model);
		}

		@In(scope = ScopeType.CONVERSATION)
		@Out(scope = ScopeType.CONVERSATION)
		private Integer selectedItem;

		@Override
		protected void populateItem(final ListItem item) {
			final int itemPosition = item.getIndex();
			Destination destination = (Destination) item.getModelObject();
			Label name = new Label("name", destination.getPlace().getName());
			item.add(name);
			if (selectedItem == item.getIndex()) {
				name
						.add(new SimpleAttributeModifier("class",
								"selectedItem"));
			}

			if ((page.getCurrentStep() == 1) && (itemPosition == 0)) {
				// Si es transporte (1), al primer item no se le puede asignar
				// transporte para llegar
				return;
			}
			name.add(new AjaxEventBehavior("onclick") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target) {
					wizardConversationManager.switchConversation(itemPosition);
					target.addComponent(TravelDetailsPanel.this);
					page.updateMainPanel(target);
				}
			});
		}
	}

	private static final long serialVersionUID = 1L;

	private WizardPage page;
	@In private Travel travel;
	@In(required=false) private Integer selectedItem;

	public TravelDetailsPanel(String id, WizardPage page) {
		super(id);
		Context context = Contexts.getConversationContext();
		if (!context.isSet("selectedItem"))
			context.set("selectedItem", new Integer(0));
		this.page = page;
		setOutputMarkupId(true);
		ListView destinationsList = new ItineraryList("destinations",
				new ContextSeamPropertyModel("travel", "destinations"));
		add(destinationsList);
		Label transportLabel = new Label("transport", new PropertyModel(this,"transport")){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(travel.getDestinations().size()!=0)
					return travel.getDestinations().get(selectedItem).getTransportTicket() != null;
				else
					return false;
			}
		};
		add(transportLabel);
		Label accomodationLabel = new Label("accomodation", new PropertyModel(this, "accomodation")) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isVisible() {
				if(travel.getDestinations().size()!=0)
					return travel.getDestinations().get(selectedItem).getAccomodationTickets() != null;
				else
					return false;
			}
		};
		add(accomodationLabel);
	}
	
	//@TODO: Meter el string de transporte en un string resources
	public String getTransport(){
		String transportStr = "Transporte: ";
		TransportTicket transport = travel.getDestinations().get(selectedItem).getTransportTicket();
		if(transport!=null){
			TransportCategory type = transport.getType();
			if (transport instanceof TransportTicketPrivate) {
				TransportTicketPrivate privateTransport = (TransportTicketPrivate) transport;
				if (type == TransportCategory.Other) {
					if (privateTransport.getOtherCategory() != null)
						transportStr += " - " + privateTransport.getOtherCategory();
				} else if (type != null)
					transportStr+= " - " + type.name();
			} else if (transport instanceof TransportTicketPublic) {
				TransportTicketPublic publicTransport = (TransportTicketPublic) transport;
				if (type != null)
					transportStr+= " - " + type.name();
				if (publicTransport.getTransport().getName() != null)
					transportStr+= " - " + publicTransport.getTransport().getName();
			}
		}else
			transportStr = "";
		return transportStr;
	}
	
	//@TODO: Meter el string de alojamiento en un string resources
	public String getAccomodation() {
		String accomodationStr = "Alojamiento: ";
		if(travel.getDestinations().get(selectedItem).getAccomodationTickets().size()>0) {
			AccomodationTicket accomodation = travel.getDestinations().get(selectedItem).getAccomodationTickets().get(0);
			if(!(accomodation instanceof AccomodationTicketNull )) {
				AccomodationCategory category = accomodation.getCategory();
				if (accomodation instanceof AccomodationTicketPrivate) {
					AccomodationTicketPrivate publicAccomodation = (AccomodationTicketPrivate) accomodation;
					if (category == AccomodationCategory.Other) {
						if(publicAccomodation.getOtherCategory() != null) 
							accomodationStr += " - " + publicAccomodation.getOtherCategory();
					} else if (category != null)
							accomodationStr += " - " + category.name();
				} else if (accomodation instanceof AccomodationTicketPublic) {
					AccomodationTicketPublic ePublica = (AccomodationTicketPublic) accomodation;
					if (category != null)
						accomodationStr += " - " + category.name();
					if (ePublica.getAccomodation().getName() != null)
						accomodationStr += " - " + ePublica.getAccomodation().getName();
				}
			} else
				accomodationStr = "";
		}
		return accomodationStr;
	}
}
