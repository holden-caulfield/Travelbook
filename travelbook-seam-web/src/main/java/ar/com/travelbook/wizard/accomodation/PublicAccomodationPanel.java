package ar.com.travelbook.wizard.accomodation;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.RaiseEvent;

import ar.com.travelbook.RankableDetailPage;
import ar.com.travelbook.common.SeamCompoundPropertyModel;
import ar.com.travelbook.common.SeamReadOnlyModel;
import ar.com.travelbook.domain.AccomodationCategory;
import ar.com.travelbook.domain.AccomodationTicket;
import ar.com.travelbook.domain.AccomodationTicketPublic;
import ar.com.travelbook.domain.Destination;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.WizardPage;

public class PublicAccomodationPanel extends Panel  {
	private static final long serialVersionUID = 1L;
	
	@In private Integer selectedItem;
	public PublicAccomodationPanel(String id){
		super(id);
		ListView repeating;
		
		Form searchForm = new Form("searchForm", new SeamCompoundPropertyModel("accomodationFilter")) {
			private static final long serialVersionUID = 1L;

			@Override
			@RaiseEvent("searchAccomodation")
			protected void onSubmit() {
				super.onSubmit();
			}
		};
		add(searchForm);
		
		List<AccomodationCategory> categories = Arrays.asList(AccomodationCategory.values());
		DropDownChoice accomodationCategory = new DropDownChoice("accomodationCategory", categories) {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean localizeDisplayValues() {
				return true;
			}
		};
		accomodationCategory.getLocalizer();
		accomodationCategory.setRequired(false);
		accomodationCategory.setNullValid(true);
		searchForm.add(accomodationCategory);

		searchForm.add(new TextField("description"));
		searchForm.add(new TextField("address"));
		
		TextField minPrice = new TextField("minPrice");
		TextField maxPrice = new TextField("maxPrice");
		
		searchForm.add(minPrice);
		searchForm.add(maxPrice);

		
		repeating = new ListView("repeating",new SeamReadOnlyModel("publicAccomodations",true)) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(final ListItem item) {
				AccomodationTicketPublic accomodationTicketPublic = (AccomodationTicketPublic) item.getModelObject();
				AjaxButton link=new AjaxButton("link"){
					private static final long serialVersionUID = 1L;
					@In
					private Travel travel;
					
					@In
					private List<AccomodationTicket> publicAccomodations;
					
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						Destination destination = travel.getDestinations().get(selectedItem);
						AccomodationTicket accomodationTicket = publicAccomodations.get(item.getIndex());
						destination.getAccomodationTickets().set(0, accomodationTicket);
						WizardPage wizard = (WizardPage) this.findPage();
						wizard.updateAll(target);
					}
				}; 
				item.add(link);
				
				AjaxButton detailLink = new AjaxButton("linkDetalle") {
					private static final long serialVersionUID = 1L;

					@In
					private List<AccomodationTicketPublic> publicAccomodations;
					
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						AccomodationTicketPublic accomodationTicketPublic = publicAccomodations.get(item.getIndex());
						Page detailPage = new RankableDetailPage(accomodationTicketPublic.getAccomodation(), findPage());
						setResponsePage(detailPage);
					}
				};
				item.add(detailLink);
				
				AccomodationCategory accomodationSelected = accomodationTicketPublic.getCategory();
				item.add(new Label("category", accomodationSelected.name()));
				String price = ((Float)accomodationTicketPublic.getPrice()).toString();
				item.add(new Label("price", price));
				link.add(new Label("name", accomodationTicketPublic.getDescription()+ accomodationTicketPublic.getAccomodation().getName()));
				item.add(new Label("address", accomodationTicketPublic.getAddress()));
			}
		};
		searchForm.add(repeating);

	}
	

	
}
