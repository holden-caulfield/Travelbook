package ar.com.travelbook.wizard.accomodation;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;

import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.common.SeamModel;
import ar.com.travelbook.domain.Destination;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.ChoicePanel;
import ar.com.travelbook.wizard.WizardPage;
import ar.com.travelbook.wizard.activity.ActivityPage;

public class AccomodationPage extends WizardPage {

	@In
	private Travel travel;
	
	@In
	private Integer selectedItem;
	private FeedbackPanel feedbackPanel;
	private Form accomodationTicketTypeForm;

	private Label placeName;
	
	public AccomodationPage() {
		super(2);
		
		feedbackPanel = new FeedbackPanel("feedbackPanel");
		add(feedbackPanel);
		feedbackPanel.setOutputMarkupId(true);
		accomodationTicketTypeForm = new Form("accomodationTicketTypeForm");
		placeName = new Label("placeName", new PropertyModel(this,"placeName"));
		add(placeName);
		placeName.setOutputMarkupId(true);
		
		accomodationTicketTypeForm.add(new ChoicePanel("choicePanel", 
				new SeamModel("accomodationType",ScopeType.CONVERSATION),
				new PublicAccomodationPanel("publicDiv"),
				new PrivateAccomodationPanel("privateDiv")));
		add(accomodationTicketTypeForm);
		
		accomodationTicketTypeForm.setOutputMarkupId(true);
	}

	public String getPlaceName(){
		Destination destination = travel.getDestinations().get(selectedItem);
		return destination.getPlace().getName();
	}
	
	@Override
	public void updateMainPanel(AjaxRequestTarget target) {
		super.updateMainPanel(target);
		target.addComponent(this.accomodationTicketTypeForm);
		target.addComponent(this.placeName);
	}
	
}
