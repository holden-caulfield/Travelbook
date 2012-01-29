package ar.com.travelbook.wizard.accomodation;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.jboss.seam.annotations.In;

import ar.com.travelbook.domain.AccomodationTicket;
import ar.com.travelbook.domain.AccomodationTicketNull;
import ar.com.travelbook.domain.AccomodationTicketPrivate;
import ar.com.travelbook.domain.AccomodationCategory;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.WizardPage;

public class PrivateAccomodationPanel extends Panel {
	private static final long serialVersionUID = 941943527294025868L;
	@In
	private Travel travel;
	
	private AccomodationTicketPrivate accomodationTicketPrivate;
	
	@In private Integer selectedItem;
	
	public PrivateAccomodationPanel(String id){
		super(id);
		accomodationTicketPrivate = new AccomodationTicketPrivate();
		AccomodationTicket accomodationTicket = getAccomodationTicketPrivate();
		Form privateForm = new Form("privateForm",new CompoundPropertyModel(accomodationTicketPrivate) 
		{
			private static final long serialVersionUID = 1L;
			@Override
			public Object getObject() {
				return getAccomodationTicketPrivate();
			}
		});

		this.add(privateForm);
		List<AccomodationCategory> categories = Arrays.asList(AccomodationCategory.values());
		DropDownChoice accomodationCategory = new DropDownChoice("category", categories){			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean localizeDisplayValues() {
				return true;
			}
		};
		privateForm.add(accomodationCategory);
		final TextField otherCategoryTextField = new TextField("otherCategory");
		otherCategoryTextField.setOutputMarkupId(true);
		otherCategoryTextField.setOutputMarkupPlaceholderTag(true);
		if(accomodationTicket.getCategory() != AccomodationCategory.Other)
			otherCategoryTextField.setVisible(false);
		privateForm.add(otherCategoryTextField);
		accomodationCategory.add(new AjaxFormComponentUpdatingBehavior("onchange"){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				AccomodationTicket accomodationTicket = getAccomodationTicketPrivate();
				if(accomodationTicket.getCategory() == AccomodationCategory.Other)
					otherCategoryTextField.setVisible(true);
				else
					otherCategoryTextField.setVisible(false);
				target.addComponent(otherCategoryTextField);
			}
		});
		privateForm.add(new TextField("description"));
		privateForm.add(new TextField("address"));

		privateForm.add(new AjaxButton("pSubmit"){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				travel.getDestinations().get(selectedItem).getAccomodationTickets().set(0, getAccomodationTicketPrivate());
				WizardPage wizard = (WizardPage) this.findPage();
				wizard.updateAll(target);
			}
		});

	}

	/**
	 * Devuelve un alojamiento personal, pero no lo pone en el viaje
	 * @return
	 */
	private AccomodationTicket getAccomodationTicketPrivate() {
		AccomodationTicket accomodationTicket = travel.getDestinations().get(selectedItem).getAccomodationTickets().get(0);
		if(((accomodationTicket instanceof AccomodationTicketNull))||(!(accomodationTicket instanceof AccomodationTicketPrivate)))
			accomodationTicket = accomodationTicketPrivate; 
		return accomodationTicket;		
				
	}

}
